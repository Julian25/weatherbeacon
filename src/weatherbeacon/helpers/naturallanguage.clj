(ns weatherbeacon.helpers.naturallanguage)


(def time-set (set ["monday" "tuesday" "wednesday" "thursday" "friday" "saturday" "sunday"
                  "tomorrow" "soon" "today" "next week"]))

(def weather-set (set ["rain" "wind" "temperature" "temp" "snow" "storm" "hot" "cold"]))

(defn classify-query [raw-string]
  (let [stripped-input (clojure.string/lower-case (clojure.string/replace raw-string #"\?" ""))
        tokens (clojure.string/split stripped-input #"\s+")
        time-tokens (clojure.set/intersection (set tokens) time-set)
        weather-categories (clojure.set/intersection (set tokens) weather-set)]
    (println time-tokens)
    (println weather-categories)
    tokens))
