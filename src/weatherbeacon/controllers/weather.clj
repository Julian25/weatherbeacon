(ns weatherbeacon.controllers.weather
  (:use [compojure.core :only (defroutes GET POST)])
  (:require [clojure.string :as str]
            [ring.util.response :as ring]
            [weatherbeacon.views.main :as views]
            [weatherbeacon.helpers.geocode :as geocoder]))


(defn index []
  (views/index-view))

(defn get-weather [location]
  (let [coordinates (geocoder/return-coordinates location)]
    (views/location-data-view coordinates)))
   

(defroutes routes
  (GET "/" [] (index))
  (GET "/weather/:location" [location] (get-weather location)))
