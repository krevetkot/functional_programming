(ns lab1.euler17-test
  (:require
   [clojure.java.shell :as shell]
   [clojure.string :as str]
   [clojure.test :refer [deftest is testing]]
   [lab1.euler17 :refer [count-letters-in-loop count-letters-in-number
                         count-letters-modular count-letters-rec
                         count-letters-tail-rec count-letters-with-map
                         number-to-words]]))

(def fixed-start 1)
(def fixed-end 1000)

(defn run-python-script [script-path & args]
  (let [command (into ["python" script-path] args)
        result (apply shell/sh command)]
    (if (zero? (:exit result))
      (str/trim (:out result))
      (throw (Exception. (str "Python error: " (:err result)))))))

(defn run-euler17-python []
  (run-python-script "./src/lab1/python_euler17.py"))

(def python-result (run-euler17-python))

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

 ; 3+3+5+4+4 = 19

(deftest count-letters-tail-rec-test
  (testing "Общий подсчет для диапазона"
    (is (= 19 (count-letters-tail-rec 1 5)))))

(deftest count-letters-rec-test
  (testing "Общий подсчет для диапазона"
    (is (= 19 (count-letters-rec 1 5)))))

(deftest count-letters-modular-test
  (testing "Общий подсчет для диапазона"
    (is (= 19 (count-letters-modular 1 5)))))

(deftest count-letters-with-map-test
  (testing "Общий подсчет для диапазона"
    (is (= 19 (count-letters-with-map 1 5)))))

(deftest count-letters-in-loop-test
  (testing "Общий подсчет для диапазона"
    (is (= 19 (count-letters-in-loop 1 5)))))

(deftest consistency-test
  (testing "Все реализации дают одинаковый результат"
    (let [implementations [count-letters-tail-rec count-letters-rec
                           count-letters-modular count-letters-with-map count-letters-in-loop]
          results (map #(% fixed-start fixed-end) implementations)]
      (is (cons python-result results)))))