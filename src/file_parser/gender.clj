(ns file-parser.gender
  (:require [clojure.string :as str]))

(def gender-words-map
  {"m" :male
   "male" :male
   "f" :female
   "female" :female})

(def gender->ordinal {:female 0
                      :male 1
                      :other 2})

(defn enum-parser
  "Parse an by supplying a predefined set of possible words and the value they
   map to, plus a default in case a value isn't in our words-map."
  [word words-map else]
  (when-not (empty? word)
    (-> word
        str/lower-case
        (#(get words-map % else)))))

(defn gender-parser [word]
  (enum-parser word gender-words-map :other))
