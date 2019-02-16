;; Clojure's sort is stable, so to promote readability, we add a few additional sorts to promote readability,
;; such that doing so doesn't violate the invariants expected on our outputs.
(ns file-parser.sort
  (:require [file-parser.gender :as gender]))

(def ascending compare)

(def descending (comp - compare))

(defn gender [m] (-> (:gender m) gender/gender->ordinal))

(defn output-1 [coll]
  (->> coll
       (sort-by :first-name ascending)
       (sort-by :last-name ascending)
       (sort-by gender ascending)))

(defn output-2 [coll]
  (->> coll
       (sort-by :first-name ascending)
       (sort-by :last-name ascending)
       (sort-by :date-of-birth ascending)))

(defn output-3 [coll]
  (->> coll
       (sort-by :first-name descending)
       (sort-by :last-name descending)))
