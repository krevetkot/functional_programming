(ns lab1.euler13-test
  (:require
   [clojure.java.shell :as shell]
   [clojure.string :as str]
   [clojure.test :refer [deftest is testing]]
   [lab1.euler13 :refer [create-random-number-str rows-to-columns
                         sum-digits-modular sum-digits-rec sum-digits-tail-rec
                         sum-digits-with-map]]))

;; Фиксированные тестовые данные для воспроизводимости
(def light-test-numbers-13
  ["1231231231" "4564564564" "7897897897"]) ; Сумма = 13693693692 -> 1369369369

(def hard-test-numbers-13 (create-random-number-str))

(defn run-python-script [script-path & args]
  (let [command (into ["python" script-path] args)
        result (apply shell/sh command)]
    (if (zero? (:exit result))
      (str/trim (:out result))
      (throw (Exception. (str "Python error: " (:err result)))))))

(defn run-euler13-python [numbers]
  (let [numbers-str (str/join " " numbers)]
    (run-python-script "./src/lab1/python_euler13.py" numbers-str)))

(def python-result (run-euler13-python hard-test-numbers-13))

(deftest rows-to-columns-test
  (testing "Транспонирование"
    (let [result (rows-to-columns light-test-numbers-13)]
      (is (= [[\1 \4 \7] [\2 \5 \8] [\3 \6 \9] [\1 \4 \7]
              [\2 \5 \8] [\3 \6 \9] [\1 \4 \7] [\2 \5 \8]
              [\3 \6 \9] [\1 \4 \7]] result)))))

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
      (is (apply = (cons python-result results))))))