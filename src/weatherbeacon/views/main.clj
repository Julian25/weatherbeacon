(ns weatherbeacon.views.main
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css)]
        [hiccup.form :only (form-to label text-area submit-button)])
  (:require [weatherbeacon.views.layout :as layout]))

(defn location-data-view [weather-data]
  (layout/common "Location Data"
                 [:p weather-data]))

(defn query-view [query]
  (layout/common "Query"
                  [:p query]))

(defn query-form []
  (form-to [:post "/query"]
           (label "query" "What would you like to know?")
           (text-area "query")
           (label "location" "Where are you searching")
           (text-area "location")
           (submit-button "Submit")))

(defn index-view []
  (layout/common "Weatherbeacon"
                 [:h2 "Enter Weather Query"]
                 (query-form)))


