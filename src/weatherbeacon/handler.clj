(ns weatherbeacon.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [weatherbeacon.controllers.weather :as weather]))

(defroutes app-routes
  (GET "/" [] "Welcome to Weatherbeacon")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
