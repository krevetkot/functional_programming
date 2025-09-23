(ns lab1.euler17-test
  (:require [clojure.test :refer [deftest is testing]]
            [lab1.euler17 :refer [count-letters-in-loop count-letters-in-number count-letters-modular count-letters-rec count-letters-tail-rec count-letters-with-map number-to-words]]))

(def fixed-range
  [1 1000])

(deftest number-to-words-test
  (testing "Преобразование чисел в слова"
    (is (= "one" (number-to-words 1)))
    (is (= "twenty" (number-to-words 20)))
    (is (= "twenty one" (number-to-words 21)))
    (is (= "three hundred and forty two" (number-to-words 342)))
    (is (= "one thousand" (number-to-words 1000)))))

(deftest count-letters-in-number-test
  (testing "Подсчет букв в числительных"
    (is (= 3 (count-letters-in-number 1)))   ; "one" -> 3
    (is (= 3 (count-letters-in-number 2)))   ; "two" -> 3
    (is (= 5 (count-letters-in-number 3)))   ; "three" -> 5
    (is (= 23 (count-letters-in-number 342))) ; "threehundredandfortytwo" -> 23
    (is (= 11 (count-letters-in-number 1000))))) ; "onethousand" -> 11

(deftest count-letters-modular-test
  (testing "Общий подсчет для диапазона"
    (is (= 19 (count-letters-modular 1 5))) ; 3+3+5+4+4 = 19
    (is (= 21124 (count-letters-modular 1 1000)))))

(deftest consistency-test
  (testing "Все реализации дают одинаковый результат"
    (let [implementations [count-letters-tail-rec count-letters-rec
                           count-letters-modular count-letters-with-map count-letters-in-loop]
          results (map #(% fixed-range) implementations)]
      (is (apply = results)))))