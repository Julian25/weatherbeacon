(ns weatherbeacon.helpers.forcast
  (:require [clj-http.client :as client]
            [cheshire.core :refer :all]
            [clojure.core.memoize :as core-memo]))


(defn attempt-request [coordinates]
  (def url "https://api.forecast.io/forecast/5e88fd6436621d9483e048d32862dbe2/")
  (let [pretty-coords (apply str (interpose ", " coordinates))
        return-data (get (client/get (clojure.string/join
                                       [url pretty-coords]) {:accept :json}) :body)]
    return-data))

;; Here we are creating a memoized function that caches the API results.
;; It resets 4 times a day.
(def attempt-request-memo (core-memo/ttl attempt-request :ttl/threshold 21600))


(defn get-forcast [tokens coordinates]
  (let [returned-data (attempt-request-memo coordinates)
        raw-data (parse-string returned-data)]
    (println raw-data)
    tokens))
