; Exercise 1
(let [a (concat '(a b c) '(d e f))
      b (count a)]
  (odd? b))

(-> '(a b c) '(d e f)
    concat
    count
    odd?
    )

; continuation passing style
(-> (concat '(a b c) '(d e f))
    ((fn [a]
       (-> (count a)
           ((fn [b]
              (odd? b)))))))

; Exercise 2
;
; Exercise 3
(-> 3
    (+ 2)
    inc)

(-> 3
    ((fn [a]
       (-> (+ a 2)
           ((fn [b]
              (inc b)))))))
