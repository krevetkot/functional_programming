(ns lab1.core
  (:gen-class)
  (:require
   [clojure.java.shell :as shell]
   [clojure.string :as str]
   [lab1.euler13 :as euler13]
   [lab1.euler17 :as euler17]))

(def number-str (euler13/create-random-number-str))

(defn run-python-script [script-path & args]
  (let [command (into ["python" script-path] args)
        result (apply shell/sh command)]
    (if (= 0 (:exit result))
      (str/trim (:out result))
      (throw (Exception. (str "Python error: " (:err result)))))))

(defn run-euler13-python [numbers]
  (let [numbers-str (str/join " " numbers)]
    (run-python-script "./src/lab1/python_euler13.py" numbers-str)))

(defn run-euler17-python []
  (run-python-script "./src/lab1/python_euler17.py"))

(defn -main []  
  
  
  (println "PYTHON\n")
  (println (run-euler13-python number-str))
  (println (run-euler17-python))

  (println "\nCLOJURE")
  (println "\nEuler 13")
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
  (println "count-letters-lazy:" (euler17/count-letters-lazy 1 1000))

  (System/exit 0))