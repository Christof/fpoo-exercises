; Exercise 1
(map (fn [n] (+ 2 n)) [1 2 3])

(map (comp inc inc) [1 2 3])
(map (partial + 2) [1 2 3])

; Exercise 2

((juxt empty? reverse count) [:a :b :c])

(def separate (juxt filter remove))

; Exercise 3
(def myfun
  (let [x 3]
    (fn [] x)))
x ; should produce an error, but nothing happens
(myfun) ; 3

; Exercise 4
(def myfun2
  ((fn [x]
     (fn [] x)
     ) 3))
(myfun2)

; Exercise 5
(def my-atom (atom 0))
(deref my-atom)
(swap! my-atom (fn [_] 33))
(deref my-atom)

; Exercise 6
(defn always [value]
  (fn [& args] value))
((always 8) 1 'a :foo)
(swap! my-atom (always 34))
(deref my-atom)

; Exercise 7
(defn check-sum [list]
    (apply + (map * (range 1 (inc (count list))) list)))
(check-sum [4 8 9 3 2])

; Exercise 8
(def reversed-digits
     (fn [string]
       (map (fn [digit-char]
              (-> digit-char str Integer.))
            (reverse string))))

(defn isbn? [string]
  (let [sum (check-sum (reversed-digits string))]
    (zero? (rem sum 11))))
(isbn? "0131774115")
(isbn? "0977716614")
(isbn? "1934356190")

; Exercise 9
(defn check-sum [list]
  (apply + (map * list (cycle '(1 3)))))
(check-sum [4 8 9 3 2])

(defn rem-of [num]
  (fn [n] (rem n num))
  )
(defn upc? [string]
  (-> string
      reversed-digits
      check-sum
      (#(rem % 10))
      zero?
      )
  )

((rem-of 10) 11)

(upc? "074182265830")
(upc? "731124100023")
(upc? "722252601404") ;; This one is incorrect.

; Exercise 10
(defn check-sum [multipliers seq]
  (apply + (map * seq (multipliers seq)))
  )

(defn number-checker [divisor multipliers]
  (fn [string]
    (-> string
       reversed-digits
       (check-sum multipliers)
       (#(rem % divisor))
       zero?)))
(def upc? (number-checker 10 (fn [_] (cycle '(1 3)))))
(def isbn? (number-checker 11 #(range 1 (inc (count %)))))
