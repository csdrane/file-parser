(ns file-parser.integration-test
  (:require  [clojure.core :rename {sorted? sorted?'}]
             [clojure.test :as t]
             [cheshire.core :as cheshire]
             [clj-http.client :as client]
             [file-parser.core :as core]
             [file-parser.gender :as gender]))

(def old-record-count (atom nil))

(defn get-by-name []
  (-> (client/get "http://localhost:3000/records/name")
      :body
      (cheshire/parse-string true)))

(defn get-by-birthdate []
  (-> (client/get "http://localhost:3000/records/birthdate")
      :body
      (cheshire/parse-string true)))

(defn get-by-gender []
  (-> (client/get "http://localhost:3000/records/gender")
      :body
      (cheshire/parse-string true)))

(defn add-record []
  (let [unparsed-line (rand-nth ["foo bar male blue 1/1/2011"
                                 "quux zaaz female brown 2/1/2011"
                                 "flub blah nonbinary teal 1/3/2011"])]
    (-> (client/post "http://localhost:3000/records" {:body unparsed-line}))))

(defn sorted? [xs {:keys [getter order]}]
  (every? #(order % 0) (map #(compare (getter %1) (getter %2)) xs (rest xs))))

(defn gender-ordinal [m]
  (->> (get m :gender)
       keyword
       (get gender/gender->ordinal)))

(t/deftest integration
  (core/-main "-c" "resources/comma.txt" "-s" "resources/space.txt" "-p" "resources/pipe.txt")

  (t/is (sorted? (get-by-name) {:getter :last-name :order >=}))
  (t/is (sorted? (get-by-birthdate) {:getter :date-of-birth :order <=}))
  (t/is (sorted? (get-by-gender) {:getter gender-ordinal :order <=}))

  (reset! old-record-count (count (get-by-name)))
  (add-record)

  (t/is (> (count (get-by-name)) @old-record-count))
  (t/is (sorted? (get-by-name) {:getter :last-name :order >=}))
  (t/is (sorted? (get-by-birthdate) {:getter :date-of-birth :order <=}))
  (t/is (sorted? (get-by-gender) {:getter gender-ordinal :order <=}))

  (core/stop-server))
