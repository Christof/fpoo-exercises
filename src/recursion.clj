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

; Exercise 4
(defn rec-function [combiner elements so-far]
  (if (empty? elements)
    so-far
    (rec-function combiner (rest elements) (combiner (first elements) so-far))))

; Exercise 3
(defn recursive-function [elements so-far]
  (rec-function + elements so-far))
(prn (recursive-function [1 2 3 4] 0))

; Exercise 4
(prn (rec-function * [1 2 3 4] 1))

; Exercise 5
(defn f [elt so-far]
  (assoc so-far elt 0))
(rec-function f [:a :b :c] {})

(rec-function #(assoc %2 %1 (count %2)) [:a :b :c] {})
