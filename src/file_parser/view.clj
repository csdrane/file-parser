(ns file-parser.view
  (:require [clojure.core :rename {sort sort'}]
            [file-parser.date :as date]
            [file-parser.sort :as sort]))

(def output-1-description "Output 1 – sorted by gender (females before males) then by last name ascending")
(def output-2-description "Output 2 – sorted by birth date, ascending")
(def output-3-description "Output 3 – sorted by last name, descending")

(defn view-gender [gender]
  (if gender (name gender) "-"))

(defn view-date [date]
  (if date (date/date-unparser date) "-"))

(defn view-word [word]
  (if word word "-"))

(defn view-entry [{:keys [first-name last-name gender favorite-color date-of-birth]}]
  (printf "%s %s %s %s %s\n"
          (view-word last-name)
          (view-word first-name)
          (view-gender gender)
          (view-word favorite-color)
          (view-date date-of-birth)))

(defn view* [description entries]
  (println description)
  (doseq [entry entries]
    (view-entry entry)))

(defn view [seq]
  (view* output-1-description (sort/output-1 seq))
  (view* output-2-description (sort/output-2 seq))
  (view* output-3-description (sort/output-3 seq)))
