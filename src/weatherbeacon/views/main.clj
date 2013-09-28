(ns weatherbeacon.views.main
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css)])
  (:require [weatherbeacon.views.layout :as layout]))

(defn index-view []
  (layout/common "Weatherbeacon"
          [:h2 "Welcome to WeatherBeacon"]))

(defn location-data-view [weather-data]
  (layout/common "Location Data"
                 [:p weather-data]))

