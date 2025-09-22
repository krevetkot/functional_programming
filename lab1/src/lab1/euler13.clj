(ns lab1.euler13
  (:gen-class))

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


; использование отображения (map)
(defn sum-digits-with-map [nums]
  (let [big-numbers (map biginteger nums)
        total-sum (reduce + big-numbers)
        sum-str (str total-sum)]
    (subs sum-str 0 11)))
