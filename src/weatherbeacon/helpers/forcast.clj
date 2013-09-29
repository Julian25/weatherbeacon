(ns weatherbeacon.helpers.forcast
  (:require [clj-http.client :as client]
            [cheshire.core :refer :all]
            [clojure.core.memoize :as core-memo]
            [forecast-clojure.core :as forecast]))

;(defn attempt-request [coordinates]
  ;(def url "https://api.forecast.io/forecast/5e88fd6436621d9483e048d32862dbe2/")
  ;(let [pretty-coords (apply str (interpose ", " coordinates))
        ;return-data (get (client/get (clojure.string/join
                                       ;[url pretty-coords]) {:accept :json}) :body)]
    ;return-data))

(defn attempt-request [coordinates]
  (forecast/forecast (str (first coordinates)) (str (second coordinates))))

;; Here we are creating a memoized function that caches the API results.
;; It resets 4 times a day.
(def attempt-request-memo (core-memo/ttl attempt-request :ttl/threshold 21600))

(defn get-data-time-id [tokens]
  (let [token-time (get tokens :time-frame)]
    (str token-time)))

(defn get-today [tokens]
  (str (get tokens :today)))

;; This function takes a function and provies the relevant weather category and time offset
(defn day-of-week-check [request-day-token today-token]
  (let [day-vector ["monday" "tuesday" "wednesday" "thursday" "friday" "saturday" "sunday"]
        request-day-index (.indexOf day-vector request-day-token)
        today (.indexOf day-vector today-token)
        time-difference ((fn [request-day today]
                          (cond 
                            (> request-day today) (- request-day today)
                            :else (+ 7 (- request-day today)))) request-day-index today)]
        {:type "daily" :offset time-difference}))


;; Need to include day's of the week here
(defn get-time-category [time-token today-token]
  (cond
    (contains? #{"monday" "tuesday" "wednesday" "thursday" "friday" "saturday" "sunday"} time-token)
        (day-of-week-check time-token today-token)
    (contains? #{"currently" "current" "now" "today"} time-token) {:type "currently" :offset 0}
    (contains? #{"later" "soon"} time-token) {:type "hourly" :offset 2}
    (contains? #{"tomorrow"} time-token) {:type "daily" :offset 1}
    (contains? #{"week"} time-token) {:type "daily" :offset 4}
    :else {:type "daily" :offset 1}))


;; This is named master-data-function because it is the main functional that pulls the raw
;; JSON data apart and returns the relevant data
(defn master-data-function [data time-category weather-type]
  (cond 
    (= (get time-category :type) "currently") {:data (get data :currently) :offset (get time-category :offset) :weather weather-type}
    (= (get time-category :type) "hourly")  {:data (get data :hourly) :offset (get time-category :offset) :weather weather-type}
    :else "No data found"))

(defn get-time-data [data weather-type time-id today]
  (let [time-category (get-time-category (str time-id) (str today))
        time-data (master-data-function data time-category weather-type)]
    time-data))

;; This function takes the time data and spits back the relevant weather types
(defn get-relevant-weather [time-data weather-type]
  (let [data (get time-data :data)
        offset (get time-data :offset)
        summary (get-in data [:summary])
        weather-data (nth (get data :data) offset)]
    (cond
      (contains? #{"rain" "snow" "storm"} (str weather-type)) {:summary summary
                                                             :intensity (get weather-data :precipIntensity)
                                                             :probability (get weather-data :precipProbability)
                                                             :precip-type (get weather-data :precipType)}
      (contains? #{"temp" "temperature"} (str weather-type)) {:summary summary
                                                              :temperature (get weather-data :temperature)
                                                              :temperatureMax (get weather-data :temperatureMax)
                                                              :temperatureMaxTime (get weather-data :temperatureMaxTime)}
      (contains? #{"wind"} (str weather-type)) {:summary summary
                                                :windSpeed (get weather-data :windSpeed)}
      (contains? #{"humidity" "pressure" "ozone"} (str weather-type)) {:summary summary
                                                                       :humidity (get weather-data :humidity)
                                                                       :pressure (get weather-data :pressure)
                                                                       :ozone (get weather-data :ozone)
                                                                       :visibility (get weather-data :visibility)}
      :else "No data found")))

(defn get-forcast [tokens coordinates]
  (println tokens)
  (let [returned-data (attempt-request-memo coordinates)
        time-data (get-time-data returned-data (get tokens :weather)
                                 (get-data-time-id tokens) (get-today tokens))
        relevant-data (get-relevant-weather time-data (get tokens :weather-type))]
    (println relevant-data)
    relevant-data))

