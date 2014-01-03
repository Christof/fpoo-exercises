; Exercise 1
(defn factorial [n]
  (if (= n 1)
    1
    (* n (factorial (dec n)))))

(factorial 4)
