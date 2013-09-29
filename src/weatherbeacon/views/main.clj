(ns weatherbeacon.views.main
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css)]
        [hiccup.form :only (form-to label text-area submit-button text-field)])
  (:require [weatherbeacon.views.layout :as layout]
            [clj-time.core]
            [clj-time.format]
            [clj-time.coerce]))

(defn location-data-view [weather-data]
  (layout/common "Location Data"
                 [:p weather-data]))

(defn natural-weather-string [weather-data]
  (let [time-frame (get weather-data :time)
        weather-type (get weather-data :weather-type)
        data (get weather-data :data)
        base-string (cond
                      (contains? #{"now" "later" "soon" "current"} (str time-frame)) 
                          (str "The forcast is " (get data :summary))
                      (contains? #{"tomorrow" "monday" "tuesday" "wednesday" "thursday" "friday" "saturday" "sunday"} (str time-frame)) 
                          (str "The forcast listed for " time-frame " is " (get data :summary))
                      (contains? #{"week"} (str time-frame)) (str "The forcase for this week is " (get data :summary))
                      :else "The forcast is ")
        weather-string (if
                         (contains? #{"rain" "snow" "temperature" "wind"} (str weather-type))
                                    (str "You asked about " (clojure.string/upper-case weather-type) ", here you go:")
                         (str "You asked about " weather-type " here is some information you may find useful"))
        weather-information-string (cond
                                     (= "rain" (str weather-type)) (cond
                                                                    (= 0 (get data :intensity)) (str "Things are looking good. The probability of rain is " (get data :probability))
                                                                    (> (get data :intensity) 0) (str "Looks like it might rain. If it is not already, the probability of it raining is: " (get data :probability)))
                                     (= "temperature" (str weather-type)) (if (not= nil (get data :temperature))
                                                                          (str "The temperature " time-frame " will be " (get data :temperature) ". The high will be " (get data :temperatureMax) " at around " (clj-time.format/unparse (clj-time.format/formatters :hour-minute) (clj-time.coerce/from-long (get data :temperatureMaxTime))))
                                                                            (str "Could not retrieve temperature data."))
                                     (= "wind" (str weather-type)) (str "The wind speed is " (get data :windSpeed))
                                     :else (str "Humidity: " (get data :humidity) ", Pressure: " (get data :pressure) ", Ozone: " (get data :ozone) ", Visibility " (get data :visibility)))]
    {:summary-string base-string :weather weather-string :forcast-data weather-information-string}))




(defn query-view [data]
  (let [forcast-string (natural-weather-string data)
        summary (get forcast-string :summary-string)
        weather (get forcast-string :weather)
        weather-data (get forcast-string :forcast-data)]
    (layout/common "Forcast"
                   [:div {:class "jumbotron"}
                    [:p summary]
                    [:p weather]
                    [:p weather-data]])))



(defn query-form []
  [:div {:class "jumbotron"}
   [:h1 {:class "header"} "Welcome to Weatherbeacon"]
   [:p {:class "lead aligned"} "A 'natural' way to check the weather"]
   [:a {:class "btn btn-danger vote" :href "http://clojurecup.com/app.html?app=weatherbeacon"} "Vote For My App on Clojure Cup"]
   (form-to [:post "/query"]
            [:div {:class "form-group main-form"}
            (label "query" "What would you like to know?")
            (text-area {:class "form-control" :placeholder "Will it rain tomorrow?"} "query")
            (label "location" "Where are you searching?")
            (text-field {:class "form-control" :placeholder "Washington"} "location")
            (submit-button {:class "btn btn-lg btn-success query-submit"} "Submit")])])

(defn index-view []
  (layout/common "Weatherbeacon"
                 (query-form)))


