(ns lab1.euler13-test
  (:require [clojure.test :refer :all]
            [lab1.euler13 :refer :all]))

;; Фиксированные тестовые данные для воспроизводимости
(def light-test-numbers-13
  ["1231231231" "4564564564" "7897897897"]) ; Сумма = 13693693692 -> 1369369369

(def hard-test-numbers-13 (euler13/create-random-number-str))


(deftest rows-to-columns-test
  (testing "Транспонирование"
    (let [result (rows-to-columns light-test-numbers-13)]
      (is (= [["1" "4" "7"] ["2" "5" "8"] ["3" "6" "9"] ["1" "4" "7"] ["2" "5" "8"] ["3" "6" "9"] ["1" "4" "7"]] result)))))

(deftest sum-digits-tail-rec-test
  (testing "Хвостовая рекурсия на простых числах"
    (let [result (sum-digits-tail-rec light-test-numbers-13)]
      (is (= "1369369369" result)))))

(deftest sum-digits-rec-test
  (testing "Обычная рекурсия на простых числах"
    (let [result (sum-digits-rec light-test-numbers-13)]
      (is (= "1369369369" result)))))

(deftest sum-digits-modular-test
  (testing "Модульная реализация на простых числах"
    (let [result (sum-digits-modular light-test-numbers-13)]
      (is (= "1369369369" result)))))

(deftest sum-digits-with-map-test
  (testing "Реализация с map на простых числах"
    (let [result (sum-digits-with-map light-test-numbers-13)]
      (is (= "1369369369" result)))))


(deftest consistency-test
  (testing "Все реализации дают одинаковый результат (большие числа)"
    (let [implementations [sum-digits-tail-rec sum-digits-rec
                           sum-digits-modular sum-digits-with-map]
          results (map #(% hard-test-numbers-13) implementations)]
      (is (apply = results)))))