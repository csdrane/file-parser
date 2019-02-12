(ns file-parser.date
  (:require [clj-time.core :as t]
            [clj-time.format :as f]))

(def date-format (f/formatter t/utc "YYYY-MM-dd" "YYYY/MM/dd" "MM/dd/YYYY" "MM-dd-YYYY"))

(defn date-parser [word]
  (f/parse date-format word))
