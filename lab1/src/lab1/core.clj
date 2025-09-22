(ns lab1.core
  (:gen-class))


(def numbers-str ["111111111111111111111111111111111111111111111111111111111111111111"
                  "111111111111111111111111111111111111111111111111111111111111111111"
                  "111111111111111111111111111111111111111111111111111111111111111111"
                  "111111111111111111111111111111111111111111111111111111111199999999"])

(defn sum-digits-tail-rec [nums]
  (letfn [(sum-column [cols carry result] ; функция принимает массив цифр текущего разряда из всех чисел, перенос и накопленный результат в обратном порядке
            (if (every? empty? cols) ; если цифры кончились
              (if (zero? carry)
                result
                (cons carry result))

              (let [current-digits (map #(if (empty? %) 0 (Character/digit (first %) 10)) cols)
                    col-sum (+ carry (reduce + current-digits))
                    digit (mod col-sum 10)
                    new-carry (quot col-sum 10)
                    next-cols (map rest cols)]
                (recur next-cols new-carry (cons digit result)))))]
    (let [num-chars (map seq nums)
          result (sum-column num-chars 0 '())]
      (apply str (take 10 (reverse result))))))


(defn sum-digits-with-map [nums]
  (let [big-numbers (map biginteger nums)
        total-sum (reduce + big-numbers)
        sum-str (str total-sum)]
    (subs sum-str 0 10)))




(defn -main [& args]
  (println (sum-digits-tail-rec numbers-str))
  (println (sum-digits-with-map numbers-str)))