(ns lab1.core
  (:gen-class))


(def numbers-str ["37107287533902102798797998220837590246510135740250"])

; хвостовая рекурсия
; в данном случае рекурсивно вызывается функция sum-column
(defn sum-digits-tail-rec [nums]
  (letfn [(sum-column [cols carry result] ; функция принимает массив цифр текущего разряда из всех чисел, перенос и накопленный результат в обратном порядке
            (if (every? empty? cols) ; если цифры кончились
              
              (if (zero? carry) ; возвращаем либо результат, либо в начало результата кладем перенос
                result
                (cons carry result))

              (let [current-digits (map #(if (empty? %) 0 (Character/digit (first %) 10)) cols) ; если цифры не кончились, каждую переводчим в число (если цифры нет - ноль)
                    col-sum (+ carry (reduce + current-digits)) ; все складываем
                    digit (mod col-sum 10) ; берет последнюю цифру суммы (эта цифра останется)
                    new-carry (quot col-sum 10) ; берет все кроме последней цифры (перенос)
                    next-cols (map rest cols)] ; убираем первый элемент во всех массивах(числах) 
                (recur next-cols new-carry (cons digit result)))))]
    
    (let [num-chars (map #(reverse (seq %)) nums) ; seq превращает строки в массивы с цифрами в обратном порыдке (чтобы складывать с младшего разряда)
          result (sum-column num-chars 0 '())] 
      (apply str (take 10 result)))))



(defn -main [& args]
  (println (sum-digits-tail-rec numbers-str)))