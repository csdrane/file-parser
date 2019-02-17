(ns file-parser.sort-test
  (:require [file-parser.date :as d]
            [file-parser.sort :as sut]
            [clojure.test :as t]))

(t/deftest sort
  (def input [{:gender :male :last-name "zz" :date-of-birth (d/date-parser "01/01/1980")}
              {:gender :male :last-name "aa" :date-of-birth (d/date-parser "2-1-1980")}
              {:gender :female :last-name "zed" :date-of-birth (d/date-parser "1-2-1980")}
              {:gender :female :last-name "foo" :date-of-birth (d/date-parser "1-1-1981")}
              {:gender :other :last-name "bar" :date-of-birth (d/date-parser "11-1-1980")}])

  (def expected-1 [{:gender :female :last-name "foo" :date-of-birth (d/date-parser "1-1-1981")}
                   {:gender :female :last-name "zed" :date-of-birth (d/date-parser "1-2-1980")}
                   {:gender :male :last-name "aa" :date-of-birth (d/date-parser "2-1-1980")}
                   {:gender :male :last-name "zz" :date-of-birth (d/date-parser "1-1-1980")}
                   {:gender :other :last-name "bar" :date-of-birth (d/date-parser "11-1-1980")}])

  (def expected-2 [{:gender :male :last-name "zz" :date-of-birth (d/date-parser "1-1-1980")}
                   {:gender :female :last-name "zed" :date-of-birth (d/date-parser "1-2-1980")}
                   {:gender :male :last-name "aa" :date-of-birth (d/date-parser "2-1-1980")}
                   {:gender :other :last-name "bar" :date-of-birth (d/date-parser "11-1-1980")}
                   {:gender :female :last-name "foo" :date-of-birth (d/date-parser "1-1-1981")}])

  (def expected-3 [{:gender :male :last-name "zz" :date-of-birth (d/date-parser "1-1-1980")}
                   {:gender :female :last-name "zed" :date-of-birth (d/date-parser "1-2-1980")}
                   {:gender :female :last-name "foo" :date-of-birth (d/date-parser "1-1-1981")}
                   {:gender :other :last-name "bar" :date-of-birth (d/date-parser "11-1-1980")}
                   {:gender :male :last-name "aa" :date-of-birth (d/date-parser "2-1-1980")}])

  (t/is (= (sut/output-1 input) expected-1))
  (t/is (= (sut/output-2 input) expected-2))
  (t/is (= (sut/output-3 input) expected-3)))
