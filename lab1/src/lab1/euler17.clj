(ns lab1.euler17
  (:gen-class)
  (:require [clojure.string :as str]))

(def number-words
  {0 "" 1 "one" 2 "two" 3 "three" 4 "four" 5 "five"
   6 "six" 7 "seven" 8 "eight" 9 "nine" 10 "ten"
   11 "eleven" 12 "twelve" 13 "thirteen" 14 "fourteen" 15 "fifteen"
   16 "sixteen" 17 "seventeen" 18 "eighteen" 19 "nineteen"
   20 "twenty" 30 "thirty" 40 "forty" 50 "fifty"
   60 "sixty" 70 "seventy" 80 "eighty" 90 "ninety"})

; ============== Монолитные реализации ==============

; монолитная реализация с использованием хвостовой рекурсии
(defn count-letters-tail-rec [start end]
  (letfn [(number-to-words [n]
            (cond
              (= n 1000) "one thousand"
              (>= n 100) (let [hundreds (quot n 100)
                               remainder (mod n 100)
                               hundred-str (str (number-words hundreds) " hundred")]
                           (if (zero? remainder)
                             hundred-str
                             (str hundred-str " and " (number-to-words remainder))))
              (>= n 20) (let [tens (* (quot n 10) 10)
                              units (mod n 10)
                              tens-str (number-words tens)]
                          (if (zero? units)
                            tens-str
                            (str tens-str " " (number-words units))))
              :else (number-words n)))
          (count-iter [n total]
            (if (> n end)
              total
              (let [words (number-to-words n) ; каждому числу сосставляем слова
                    clean-words (clojure.string/replace words #"[ -]" "") ; удаляем пробелы 
                    letter-count (count clean-words)] ; считаем буквы
                (recur (inc n) (+ total letter-count)))))]
    (count-iter start 0)))


; монолитная реализация с использованием обычной рекурсии
(defn count-letters-rec [n end]
  (letfn [(number-to-words [n]
                           (cond
                             (= n 1000) "one thousand"
                             (>= n 100) (let [hundreds (quot n 100)
                                              remainder (mod n 100)
                                              hundred-str (str (number-words hundreds) " hundred")]
                                          (if (zero? remainder)
                                            hundred-str
                                            (str hundred-str " and " (number-to-words remainder))))
                             (>= n 20) (let [tens (* (quot n 10) 10)
                                             units (mod n 10)
                                             tens-str (number-words tens)]
                                         (if (zero? units)
                                           tens-str
                                           (str tens-str " " (number-words units))))
                             :else (number-words n)))]
  (if (> n end)
    0
    (let [words (number-to-words n)
          clean-words (clojure.string/replace words #"[ ]" "")
          letter-count (count clean-words)]
      (+ letter-count (count-letters-rec (inc n) end))))))



; ============== Модульная реализация ==============

(defn number-to-words [n]
  (cond
    (= n 1000) "one thousand"
    (>= n 100) (let [hundreds (quot n 100)
                     remainder (mod n 100)
                     hundred-str (str (number-words hundreds) " hundred")]
                 (if (zero? remainder)
                   hundred-str
                   (str hundred-str " and " (number-to-words remainder))))
    (>= n 20) (let [tens (* (quot n 10) 10)
                    units (mod n 10)
                    tens-str (number-words tens)]
                (if (zero? units)
                  tens-str
                  (str tens-str " " (number-words units))))
    :else (number-words n)))

(defn clean-words [words]
  (str/replace words #"[ ]" ""))

(defn count-letters-in-number [n]
  (-> n
      number-to-words
      clean-words
      count))

(defn count-letters-modular [start end]
  (->> (range start (inc end))       
       (map count-letters-in-number) 
       (reduce +)))                 


(defn count-letters-with-map [start end]
  (apply + (map (comp count
                      #(clojure.string/replace % #"[ ]" "") number-to-words)
                (range start (inc end)))))
; пошагово: number-to-words к элементу, заменяем пробелы, считаем символы
; все это собирается в композицию и применяется к каждому элементу. потом складывается