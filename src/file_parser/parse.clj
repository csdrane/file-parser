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

(def expected-fields [(text-field :last-name)
                      (text-field :first-name)
                      (gender-field :gender)
                      (text-field :favorite-color)
                      (date-field :date-of-birth)])

(defn valid-line? [fields words]
  (same-length? fields words))

(defn parse-line
  "Parses a line. Returns nil if invalid input."
  ([re-delimiter fields line {:keys [logging?]}]
   (let [words (str/split line re-delimiter)]
     (if (valid-line? fields words)
       (loop [m {}
              [{:keys [name parser] :as field} & fields'] fields
              [word & words'] words]
         (if (nil? field)
           m
           (recur (assoc m name (parser word)) fields' words')))
       (when logging? (log/warnf "unable to parse line: %s" line)))))
  ([re-delimiter fields line]
   (parse-line re-delimiter fields line {:logging? true})))

(defn parse-file [filename re-delimiter fields]
  (try
    (let [file-contents (slurp filename)
          lines (str/split-lines file-contents)
          line-parser (partial parse-line re-delimiter fields)]
      (->> (map line-parser lines)
           (filter some?)))
    (catch Exception e
      (log/errorf "failed to parse file: %s" filename))))

(defn parse-comma-file [filename]
  (parse-file filename comma-delimiter expected-fields))

(defn parse-space-file [filename]
  (parse-file filename space-delimiter expected-fields))

(defn parse-pipe-file [filename]
  (parse-file filename pipe-delimiter expected-fields))
