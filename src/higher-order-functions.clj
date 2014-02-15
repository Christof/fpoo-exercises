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
