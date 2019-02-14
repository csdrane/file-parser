(ns file-parser.date-test
  (:require [file-parser.date :as sut]
            [clojure.test :as t]
            [clj-time.core :as time]))

(t/is (= (sut/date-parser "8/18/1985") (time/date-time 1985 8 18)))
(t/is (= (sut/date-parser "08/18/1985") (time/date-time 1985 8 18)))
(t/is (= (sut/date-parser "8-18-1985") (time/date-time 1985 8 18)))
(t/is (= (sut/date-parser "08-18-1985") (time/date-time 1985 8 18)))
(t/is (= (sut/date-parser "1985/8/18") (time/date-time 1985 8 18)))
(t/is (= (sut/date-parser "1985/08/18") (time/date-time 1985 8 18)))
(t/is (= (sut/date-parser "1985-8-18") (time/date-time 1985 8 18)))
(t/is (= (sut/date-parser "1985-08-18") (time/date-time 1985 8 18)))
