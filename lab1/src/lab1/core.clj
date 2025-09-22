(ns lab1.core
  (:gen-class)
  (:require [lab1.euler13 :as euler13]
            [lab1.euler17 :as euler17]))



(defn random-digit []
  (str (rand-int 10))) ; генерирует одну цифру - символ

(defn random-number [length]
  (apply str (repeatedly length random-digit))) ; строка из length цифр

(def numbers-str
  (repeatedly 100 #(random-number 50))) ; 100 строк по 50 цифр


(defn -main []
  (println "Euler 13")
  ;; (println "sum-digits-tail-rec:" (euler13/sum-digits-tail-rec numbers-str))
  ;; (println "sum-digits-with-map:" (euler13/sum-digits-with-map numbers-str))

  (println "\nEuler 17")
  (println "count-letters-tail-rec:" (euler17/count-letters-tail-rec 1 1000))
  (println "count-letters-rec:" (euler17/count-letters-rec 1 1000))
  (println "count-letters-with-map:" (euler17/count-letters-with-map 1 1000)))