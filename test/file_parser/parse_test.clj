(ns file-parser.parse-test
  (:require [file-parser.parse :as sut]
            [clojure.test :as t]
            [clj-time.core :as time]))

;(println (sut/parse-line #"\|" ["first-name" "last-name"] "chris|drane"))

(t/is (sut/same-length? '(a a) '(b b)))
(t/is (not (sut/same-length? '(a a) '(b))))

(t/is (thrown? Exception (sut/validate-line [:a :b] '(a))))

(t/is (= (sut/parse-line #"\|" [(sut/text-field :a) (sut/text-field :b) (sut/date-field :c)] "a|b|2019-1-1") {:a "a" :b "b" :c (time/date-time 2019 1 1)}))
