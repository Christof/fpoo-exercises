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
