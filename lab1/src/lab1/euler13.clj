(ns lab1.euler13
  (:gen-class))

; ============== Монолитные реализации ==============

; монолитная реализация с использованием хвостовой рекурсии
(defn sum-digits-tail-rec [nums]
  (letfn [(sum-column [cols carry result]
  ; функция принимает массив цифр текущего разряда из всех чисел, перенос и накопленный результат в обратном порядке
            (if (every? empty? cols) ; если цифры кончились
              (if (zero? carry) ; возвращаем либо результат, либо в начало результата кладем перенос
                result
                (cons carry result))
              (let [current-digits (map #(if (empty? %) 0 (Character/digit (first %) 10)) cols)
              ; если цифры не кончились, каждую переводчим в число (если цифры нет - ноль)
                    col-sum (+ carry (reduce + current-digits)) ; все складываем
                    digit (mod col-sum 10) ; берет последнюю цифру суммы (эта цифра останется)
                    new-carry (quot col-sum 10) ; берет все кроме последней цифры (перенос)
                    next-cols (map rest cols)]
                    ; убираем первый элемент во всех массивах(числах)
                (recur next-cols new-carry (cons digit result)))))]

    (let [num-chars (map #(reverse (seq %)) nums)
    ; seq превращает строки в массивы с цифрами в обратном порядке (чтобы складывать с младшего разряда)
          result (sum-column num-chars 0 '())
          str-result (apply str (take 10 result))]
      (if (= (count str-result) 11)
        (apply str (take 10 str-result))
        str-result))))

; монолитная реализация с использованием обычной рекурсии
(defn sum-digits-rec [nums]
  (letfn [(sum-column [cols carry]
            (if (every? empty? cols)
              (if (zero? carry) [] [carry])
              (let [current-digits (map #(if (empty? %) 0 (Character/digit (first %) 10)) cols)
                    col-sum (+ carry (reduce + current-digits))
                    digit (mod col-sum 10)
                    new-carry (quot col-sum 10)
                    next-cols (map rest cols)]
                (cons digit (sum-column next-cols new-carry)))))]

    (let [nums-list (map #(reverse (seq %)) nums)
          result (sum-column nums-list 0)
          str-result (apply str (take 10 (reverse result)))]
      (if (= (count str-result) 11)
        (apply str (take 10 str-result))
        str-result))))
; в отличие от хвостовой рекурсии, в конце нам нужен реверс результата, так как мы накапливали его "слева-направо"

; ============== Модульная реализация ==============

(defn random-digit []
  (str (rand-int 10))) ; генерирует одну цифру - символ

(defn random-number [length]
  (apply str (repeatedly length random-digit))) ; строка из length цифр

(defn create-random-number-str []
  (doall (repeatedly 100 #(random-number 50)))) ; 100 строк по 50 цифр

(defn reverse-number-strs [number-strs]
  (map #(reverse (seq %)) number-strs))

(defn rows-to-columns [number-rows]
  (apply map vector number-rows))

(defn filter-empty-columns [columns]
  (filter (comp not empty?) columns))

(defn char-to-digit-column [column]
  (map #(Character/digit % 10) column))

(defn sum-columns-with-carry [columns]
  (letfn [(column-reducer [carry-result column]
            (let [[current-carry current-result] carry-result
            ; деструктуризация! в carry-result Хранится перенос + '(итоговые цифры)
                  col-sum (+ current-carry (reduce + column))
                  digit (mod col-sum 10)
                  new-carry (quot col-sum 10)]
              [new-carry (cons digit current-result)]))]

    (let [[final-carry final-result] (reduce column-reducer [0 '()] columns)]
      (if (zero? final-carry)
        final-result
        (cons final-carry final-result)))))

(defn digits-to-string [digits]
  (let [str-result (apply str (take 10 digits))]
    (if (= (count str-result) 11)
      (apply str (take 10 str-result))
      str-result)))

(defn take-first-n-digits [n digits]
  (take n digits))

(defn sum-digits-modular [nums]
  (->> nums
       (reverse-number-strs)
       (rows-to-columns)
       (filter-empty-columns)
       (map char-to-digit-column)
       (sum-columns-with-carry)
       (take-first-n-digits 10)
       (digits-to-string)))

; ============== Использование отображения (map) ==============

(defn sum-digits-with-map [nums]
  (let [big-numbers (map biginteger nums)
        total-sum (reduce + big-numbers)
        sum-str (str total-sum)]
    (subs sum-str 0 10)))

; ============== Работа с циклом ==============

(defn sum-digits-in-loop [nums]
  (let [num-seqs (map #(reverse (seq %)) nums)
        max-len (apply max (map count num-seqs))]
    (loop [col-index 0, carry 0, result []]
      (if (and (>= col-index max-len) (zero? carry))
        (apply str (take 10 result))
        (let [col (map #(if (< col-index (count %))
                          (Character/digit (nth % col-index) 10) 0)
                       num-seqs)
              total (+ carry (reduce + col))
              digit (mod total 10)
              new-carry (quot total 10)]
          (recur (inc col-index) new-carry (cons digit result)))))))

; ============== Работа с бесконечными списками ==============

(defn sum-digits-lazy [nums]
  (let [numbers (take 100 nums)
        digit-vecs (mapv (fn [s]
                           (vec (map #(Character/digit % 10) (reverse (seq s)))))
                         numbers)
        max-len 50
        columns (for [i (range max-len)]
                  (mapv #(if (< i (count %)) (% i) 0) digit-vecs))]

    (letfn [(sum-cols [cols carry]
              (lazy-seq
               (if (seq cols)
                 (let [col    (first cols)
                       total  (+ carry (reduce + col))
                       digit  (mod total 10)
                       new-carry (quot total 10)]
                   (cons digit (sum-cols (rest cols) new-carry)))
                 ;; колонки кончились
                 (when (pos? carry)
                   (let [digit (mod carry 10)
                         new-carry (quot carry 10)]
                     (cons digit (sum-cols '() new-carry)))))))]
      (->> (sum-cols columns 0)
           reverse
           (take 10)
           (apply str)))))
