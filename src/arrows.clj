; Exercise 1: [1] -> 1 -> 2 -> (2)
(-> [1]
    first
    inc
    list
    )

; Exercise 2:
(-> 2
    (* 3)
    list)

; Exercise 3:
(defn double [n] (* 2 n))
(-> 3
    double
    inc
    )

; Exercise 4:
(-> (+ 1 2)
    (* 3)
    (+ 4)
    )

