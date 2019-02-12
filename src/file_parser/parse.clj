(ns file-parser.parse
  (:require [file-parser.date :refer [date-parser]]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(defn same-length? [xs ys]
  (= (count xs) (count ys)))

(defn text-field [name]
  {:name name :parser identity})

(defn date-field [name]
  {:name name :parser date-parser})

(defn validate-line [fields words]
  (when-not (same-length? fields words)
    (throw (IllegalArgumentException. "Input data in unexpected format"))))

; TODO should wrap in a try - parse might fail
(defn parse-line [re-delimiter fields line]
  (let [words (str/split line re-delimiter)]
    (validate-line fields words)
    (loop [m {}
           [{:keys [name parser] :as field} & fields'] fields
           [word & words'] words]
      (if (nil? field)
        m
        (recur (assoc m name (parser word)) fields' words')))))

(defn parse-file [filename re-delimiter fields]
  (with-open [r (io/reader filename)]
    (let [line-parser (partial parse-line re-delimiter fields)
          lines (line-seq r)]
      (map line-parser lines))))

