(ns weatherbeacon.views.main
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css)]
        [hiccup.form :only (form-to label text-area submit-button text-field)])
  (:require [weatherbeacon.views.layout :as layout]))

(defn location-data-view [weather-data]
  (layout/common "Location Data"
                 [:p weather-data]))

(defn query-view [query]
  (layout/common "Query"
                  [:p query]))

(defn query-form []
  [:div {:class "jumbotron"}
   [:h1 {:class "header"} "Welcome to Weatherbeacon"]
   [:p {:class "lead aligned"} "A 'natural' way to check the weather"]
   (form-to [:post "/query"]
            [:div {:class "form-group main-form"}
            (label "query" "What would you like to know?")
            (text-area {:class "form-control" :placeholder "Will it rain tomorrow?"} "query")
            (label "location" "Where are you searching")
            (text-field {:class "form-control" :placeholder "Washington, DC"} "location")
            (submit-button {:class "btn btn-lg btn-success query-submit"} "Submit")])])

(defn index-view []
  (layout/common "Weatherbeacon"
                 (query-form)))


