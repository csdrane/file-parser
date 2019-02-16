(ns file-parser.date
  (:require [clj-time.core :as t]
            [clj-time.format :as f]))

(def date-parse-format (f/formatter t/utc "YYYY-MM-dd" "YYYY/MM/dd" "MM/dd/YYYY" "MM-dd-YYYY"))
(def date-print-format (f/formatter "M/d/YYYY"))

(defn date-parser [word]
  "Parse a date. If unsuccessful, return nil."
  (try
    (f/parse date-parse-format word)
    (catch Exception _
        nil)))

(defn date-unparser [date]
  (when date (f/unparse date-print-format date)))
