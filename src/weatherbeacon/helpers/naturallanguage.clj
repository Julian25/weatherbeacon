(ns weatherbeacon.helpers.naturallanguage
  (:require [clj-http.client :as client]))


(def time-set (set ["monday" "tuesday" "wednesday" "thursday" "friday" "saturday" "sunday"
                  "tomorrow" "soon" "today" "next week"]))

(def weather-set (set ["rain" "wind" "temperature" "temp" "snow" "storm" "hot" "cold"]))

(defn date-handler [time-token]
  (def dates ["monday" "tuesday" "wednesday" "thursday" "friday" "saturday" "sunday"])
  (let [raw-date (get (client/get "http://www.timeapi.org/utc/now?format=%25a") :body)]
        (println raw-date)
  raw-date))

(defn classify-query [raw-string]
  (let [stripped-input (clojure.string/lower-case (clojure.string/replace raw-string #"\?" ""))
        tokens (clojure.string/split stripped-input #"\s+")
        time-tokens (clojure.set/intersection (set tokens) time-set)
        weather-categories (clojure.set/intersection (set tokens) weather-set)
        classification {:time-frame (first time-tokens)  :today (date-handler time-tokens) :weather-type (first weather-categories)}]
    classification))
