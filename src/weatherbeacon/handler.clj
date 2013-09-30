(ns weatherbeacon.handler
  (:use compojure.core
        [hiccup.page :only (html5)] 
        [hiccup.middleware :only (wrap-base-url)]
        [ring.adapter.jetty :only [run-jetty]]
        [ring.middleware.params :only [wrap-params]])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.response :as response]
            [weatherbeacon.controllers.weather :as weather]))


(defroutes main-routes
  weather/routes
  (route/resources "/")
  (route/not-found "Page not found"))

;(def app
  ;(-> (handler/site main-routes)
      ;(wrap-base-url)))

;(def app (wrap-params handler/site main-routes))

(def application (handler/site main-routes))

(defn start [port]
  (run-jetty #'application {:port port :join? false}))

(defn -main []
  (let [port (Integer/parseInt
                                 (or (System/getenv "PORT") "8080"))]
    (start port)))
