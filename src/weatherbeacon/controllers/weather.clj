(ns weatherbeacon.controllers.weather
  (:use [compojure.core :only (defroutes GET POST)])
  (:require [clojure.string :as str]
            [ring.util.response :as ring]
            [weatherbeacon.views.main :as views]))


(defn index []
  (views/index-view))

(defn get-weather [location]
  (views/location-data-view location))
   

(defroutes routes
  (GET "/" [] (index))
  (GET "/weather/:location" [location] (get-weather location)))
