(ns file-parser.parse-test
  (:require [file-parser.parse :as sut]
            [clojure.test :as t]
            [clj-time.core :as time]
            [file-parser.date :as d]))

(t/deftest parse
  (t/is (sut/same-length? '(a a) '(b b)))
  (t/is (not (sut/same-length? '(a a) '(b))))

  (t/is (= (sut/valid-line? [:a :b] '(a)) false))

  (let [spec [(sut/text-field :last-name)
              (sut/text-field :first-name)
              (sut/gender-field :gender)
              (sut/text-field :favorite-color)
              (sut/date-field :date-of-birth)]]

    (t/is (= (sut/parse-line sut/pipe-delimiter sut/expected-fields "Drane | Chris | Male | Blue | 8/18/1985")
             {:last-name "Drane"
              :first-name "Chris"
              :gender :male
              :favorite-color "Blue"
              :date-of-birth (d/date-parser "8/18/1985")}))

    (t/is (= (sut/parse-line sut/comma-delimiter sut/expected-fields "Drane, Chris, Male, Blue, 8/18/1985")
             {:last-name "Drane"
              :first-name "Chris"
              :gender :male
              :favorite-color "Blue"
              :date-of-birth (d/date-parser "8/18/1985")}))

    (t/is (= (sut/parse-line sut/space-delimiter sut/expected-fields "Drane Chris Male Blue 8/18/1985")
             {:last-name "Drane"
              :first-name "Chris"
              :gender :male
              :favorite-color "Blue"
              :date-of-birth (d/date-parser "8/18/1985")}))

    (t/is (= (sut/parse-file "resources/pipe.txt" sut/pipe-delimiter sut/expected-fields)
             (list
              {:last-name "Durant"
               :first-name "Andy"
               :gender :male
               :favorite-color "blue"
               :date-of-birth (d/date-parser "4/24/1990")}
              {:last-name "Durant"
               :first-name "Nick"
               :gender :male
               :favorite-color "green"
               :date-of-birth (d/date-parser "1/28/1988")})))

    (t/is (= (sut/parse-file "resources/comma.txt" sut/comma-delimiter sut/expected-fields)
             (list
              {:last-name "Drane"
               :first-name "Chris"
               :gender :male
               :favorite-color "blue"
               :date-of-birth (d/date-parser "8/18/1985")}
              {:last-name "Bokoff"
               :first-name "Jen"
               :gender :female
               :favorite-color "purple"
               :date-of-birth (d/date-parser "11/30/1986")}
              {:last-name "Sparse"
               :first-name "Entry"
               :gender nil
               :favorite-color nil
               :date-of-birth nil}
              {:last-name "Zweilter",
               :first-name "Jerry",
               :gender :male,
               :favorite-color "red",
               :date-of-birth (d/date-parser "2/2/1922")})))

    (t/is (= (sut/parse-file "resources/space.txt" sut/space-delimiter sut/expected-fields)
             (list
              {:last-name "Jones",
               :first-name "Michael",
               :gender :male,
               :favorite-color "orange",
               :date-of-birth (d/date-parser "9/1/1967")}
              {:last-name "Lawrence",
               :first-name "Jen",
               :gender :female,
               :favorite-color "teal",
               :date-of-birth (d/date-parser "11/30/1986")}
              {:last-name "Lawrence",
               :first-name "Jan",
               :gender :female,
               :favorite-color "purple",
               :date-of-birth  (d/date-parser "11/30/1986")}
              {:last-name "Lawrence",
               :first-name "Jim",
               :gender :male,
               :favorite-color "purple",
               :date-of-birth  (d/date-parser "11/30/1986")})))))
