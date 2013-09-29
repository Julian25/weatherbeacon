(ns weatherbeacon.helpers.forcast
  (:require [clj-http.client :as client]
            [cheshire.core :refer :all]
            [clojure.core.memoize :as core-memo]))

(defn attempt-request [coordinates]
  (def url "https://api.forecast.io/forecast/5e88fd6436621d9483e048d32862dbe2/")
  (let [pretty-coords (apply str (interpose ", " coordinates))
        return-data (get (client/get (clojure.string/join
                                       [url pretty-coords]) {:accept :json}) :body)]
    return-data))

;; Here we are creating a memoized function that caches the API results.
;; It resets 4 times a day.
(def attempt-request-memo (core-memo/ttl attempt-request :ttl/threshold 21600))

(defn get-data-time-id [tokens]
  (let [token-time (get tokens :time-frame)]
    token-time))

;; This function takes a function and provies the relevant weather category and time offset
(defn day-of-week-check [tokens]


;; Need to include day's of the week here
(defn get-time-category [time-token]
  (cond
    (contains? ["currently" "current" "now" "today"] time-token) "currently"
    (contains? ["later" "soon"] time-token) "hourly"
    (contains? ["tomorrow" "next week" "this week"] time-token) "daily"
    :else "daily")

(defn get-time-data [data time-id]
  (let [time-category (get-time-category (str time-id))



(defn get-forcast [tokens coordinates]
  (let [returned-data (attempt-request-memo coordinates)
        raw-data (parse-string returned-data)
        time-data (get-time-data raw-data (get-data-time-id tokens))]
    tokens))
