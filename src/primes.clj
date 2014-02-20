; Exercise 1
(range (* 2 2) 101 2)
(defn multiples [number]
  (range (* number 2) 101 number))
(multiples 2)
(multiples 4)

; Exercise 2
(def nonprimes
  (for [a (range 2 11)
      b (multiples a)]
  b))
nonprimes

(use 'clojure.algo.monads)
(def nonprimes-m
  (with-monad sequence-m
    (domonad [a (range 2 11)
              b (multiples a)]
             b)))
nonprimes-m

; Exercise 3
(remove (set nonprimes) (range 1 100))
