(ns weatherbeacon.controllers.weather
  (:use [compojure.core :only (defroutes GET POST)])
  (:require [clojure.string :as str]
            [ring.util.response :as ring]
            [weatherbeacon.views.main :as views]
            [weatherbeacon.helpers.geocode :as geocoder]
            [weatherbeacon.helpers.forcast :as forcast]
            [weatherbeacon.helpers.naturallanguage :as nl]))

(defn index []
  (views/index-view))

(defn get-weather [weather-tokens location]
  (let [coordinates (geocoder/return-coordinates location)
        weather-data (forcast/get-forcast weather-tokens coordinates)]
    weather-data))

(defn weather-query [query-string location]
  (let [weather-input (nl/classify-query query-string)
        forcast-data (get-weather weather-input location)]
    (views/query-view (str forcast-data))))


(defroutes routes
  (GET "/" [] (index))
  (POST "/query" [query location] (weather-query query location)))
