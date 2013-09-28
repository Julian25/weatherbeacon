(ns weatherbeacon.views.main
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css)])
  (:require [weatherbeacon.views.layout :as layout]))

(defn index-view []
  (layout/common "Weatherbeacon"
          [:h2 "TESTING"]))

(defn location-data-view [location]
  (layout/common "Location Data"
                 [:h2 location]))

