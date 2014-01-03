; Exercise 1
(defn factorial [n]
  (if (= n 1)
    1
    (* n (factorial (dec n)))))

(factorial 4)

; Exercise 2
(defn factorial2 [n so-far]
  (if (= n 1)
    so-far
    (factorial2 (dec n) (* so-far n))))
(prn (factorial2 4 1))

; Exercise 3
(defn recursive-function [elements so-far]
  (if (empty? elements)
    so-far
    (recursive-function (rest elements) (+ so-far (first elements)))))
(prn (recursive-function [1 2 3 4] 0))

