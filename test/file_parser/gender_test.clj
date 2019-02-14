(ns file-parser.gender-test
  (:require [file-parser.gender :as sut]
            [clojure.test :as t]))

(t/is (= (sut/gender-parser "m") :male))
(t/is (= (sut/gender-parser "male") :male))
(t/is (= (sut/gender-parser "f") :female))
(t/is (= (sut/gender-parser "female") :female))
(t/is (= (sut/gender-parser "nonbinary") :other))
