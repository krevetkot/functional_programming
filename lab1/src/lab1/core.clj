(ns lab1.core
  (:gen-class)
  (:require [lab1.euler13 :as euler13]
            [lab1.euler17 :as euler17]))

(def number-str (euler13/create-random-number-str))

(defn -main []
  (println "Euler 13")
  (println "sum-digits-tail-rec:" (euler13/sum-digits-tail-rec number-str))
  (println "sum-digits-rec:" (euler13/sum-digits-rec number-str))
  (println "sum-digits-modular:" (euler13/sum-digits-modular number-str))
  (println "sum-digits-with-map:" (euler13/sum-digits-with-map number-str))
  (println "sum-digits-in-loop:" (euler13/sum-digits-in-loop number-str))
  (println "sum-digits-lazy:" (euler13/sum-digits-lazy number-str))

  (println "\nEuler 17")
  (println "count-letters-tail-rec:" (euler17/count-letters-tail-rec 1 1000))
  (println "count-letters-rec:" (euler17/count-letters-rec 1 1000))
  (println "count-letters-modular:" (euler17/count-letters-modular 1 1000))
  (println "count-letters-with-map:" (euler17/count-letters-with-map 1 1000))
  (println "count-letters-in-loop:" (euler17/count-letters-in-loop 1 1000))
  (println "count-letters-lazy:" (euler17/count-letters-lazy 1 1000)))