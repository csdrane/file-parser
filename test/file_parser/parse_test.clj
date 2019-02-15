(ns file-parser.parse-test
  (:require [file-parser.parse :as sut]
            [clojure.test :as t]
            [clj-time.core :as time]
            [file-parser.date :as d]))


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
            {:last-name "Drane"
             :first-name "Chris"
             :gender :male
             :favorite-color "Blue"
             :date-of-birth (d/date-parser "8/18/1985")}
            {:last-name "Bokoff"
             :first-name "Jen"
             :gender :female
             :favorite-color "Purple"
             :date-of-birth (d/date-parser "11/30/1986")}
            {:last-name "Sparse"
             :first-name "Entry"
             :gender :other
             :favorite-color nil
             :date-of-birth nil})))

  (t/is (= (sut/parse-file "resources/comma.txt" sut/comma-delimiter sut/expected-fields)
           (list
            {:last-name "Drane"
             :first-name "Chris"
             :gender :male
             :favorite-color "Blue"
             :date-of-birth (d/date-parser "8/18/1985")}
            {:last-name "Bokoff"
             :first-name "Jen"
             :gender :female
             :favorite-color "Purple"
             :date-of-birth (d/date-parser "11/30/1986")}
            {:last-name "Sparse"
             :first-name "Entry"
             :gender :other
             :favorite-color nil
             :date-of-birth nil})))

  (t/is (= (sut/parse-file "resources/space.txt" sut/space-delimiter sut/expected-fields)
           (list
            {:last-name "Drane"
             :first-name "Chris"
             :gender :male
             :favorite-color "Blue"
             :date-of-birth (d/date-parser "8/18/1985")}
            {:last-name "Bokoff"
             :first-name "Jen"
             :gender :female
             :favorite-color "Purple"
             :date-of-birth (d/date-parser "11/30/1986")}))))
