(ns weatherbeacon.helpers.geocode
  (:require [clj-http.client :as client]
            [cheshire.core :refer :all]))


(defn return-coordinates [location-name]
  (def url "https://maps.google.com/maps/api/geocode/json?")
  (let [return-data (client/get url {:query-params {"address" location-name, "sensor" "false"}})
        coordinate-data (second (first (rest (second (second (rest (nth (first (rest (first (parse-string (get return-data :body))))) 1)))))))]
    coordinate-data))
