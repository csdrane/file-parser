(ns file-parser.parse
  (:require [file-parser.date :refer [date-parser]]
            [file-parser.gender :refer [gender-parser]]
            [clojure.java.io :as io]
            [clojure.tools.logging :as log]
            [clojure.string :as str]))

(def pipe-delimiter #"\s*\|\s*")
(def comma-delimiter #"\s*\,\s*")
(def space-delimiter #"\s+")

(defn same-length? [xs ys]
  (= (count xs) (count ys)))

(defn text-parser [word]
  (when-not (empty? word)
    word))

(defn text-field [name]
  {:name name :parser text-parser})

(defn date-field [name]
  {:name name :parser date-parser})

(defn gender-field [name]
  {:name name :parser gender-parser})

(defn valid-line? [fields words]
  (same-length? fields words))

(defn parse-line [re-delimiter fields line]
  "Parses a line. Returns nil if invalid input."
  (let [words (str/split line re-delimiter)]
    (if (valid-line? fields words)
      (loop [m {}
             [{:keys [name parser] :as field} & fields'] fields
             [word & words'] words]
        (if (nil? field)
          m
          (recur (assoc m name (parser word)) fields' words')))
      (log/warnf "unable to parse line: %s" line))))

(defn parse-file [filename re-delimiter fields]
  (let [file-contents (slurp filename)
        lines (str/split-lines file-contents)
        line-parser (partial parse-line re-delimiter fields)]
    (->> (map line-parser lines)
         (filter some?))))

