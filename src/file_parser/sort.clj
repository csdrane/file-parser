(ns file-parser.sort)

(def gender->ordinal {:female 0
                      :male 1
                      :other 2})

(def ascending compare)

(def descending (comp - compare))

(defn gender [m] (-> (:gender m) gender->ordinal))

(defn output-1 [coll]
  (->> coll
       (sort-by :last-name ascending)
       (sort-by gender ascending)))

(defn output-2 [coll]
  (sort-by :birth-date ascending coll))

(defn output-3 [coll]
  (sort-by :last-name descending coll))
