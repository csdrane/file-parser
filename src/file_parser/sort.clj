(ns file-parser.sort
  (:require [file-parser.gender :as gender]))



(def ascending compare)

(def descending (comp - compare))

(defn gender [m] (-> (:gender m) gender/gender->ordinal))

(defn output-1 [coll]
  (->> coll
       (sort-by :last-name ascending)
       (sort-by gender ascending)))

(defn output-2 [coll]
  (sort-by :birth-date ascending coll))

(defn output-3 [coll]
  (sort-by :last-name descending coll))
