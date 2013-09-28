(ns weatherbeacon.handler
  (:use compojure.core
        [hiccup.page :only (html5)] 
        [hiccup.middleware :only (wrap-base-url)])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.response :as response]
            [weatherbeacon.controllers.weather :as weather]))


(defroutes main-routes
  weather/routes
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site main-routes)
      (wrap-base-url)))
