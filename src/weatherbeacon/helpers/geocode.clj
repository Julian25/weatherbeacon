(ns weatherbeacon.helpers.geocode
  (:require [clj-http.client :as client]
            [cheshire.core :refer :all]))


(defn hunt-for-coordinates [raw-data]
  (rest (second (second (second (rest (first (first (rest (first raw-data))))))))))

(defn return-coordinates [location-name]
  (def url "https://maps.google.com/maps/api/geocode/json?")
  (let [return-data (client/get url {:query-params {"address" location-name, "sensor" "false"}})
        coordinate-data (hunt-for-coordinates (parse-string (get return-data :body)))
        temp-coords (first coordinate-data)
        coordinates [(second (first temp-coords)) (second (second temp-coords))]]
    coordinates))


