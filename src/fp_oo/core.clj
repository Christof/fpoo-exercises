(ns fp-oo.core)

; Exercise 1
(defn second [list]
  (first (rest list)))

(second [1 2 3 4])

; Exercise 2
(defn third [list]
  (nth list 2))

(third [1 2 3 4])

(defn third2 [list]
  (first (rest (rest list))))

(third2 [1 2 3 4])

(defn third3 [list]
  (second (rest list)))

(third3 [1 2 3 4])

; Exercise 3
(defn add-squares [& list]
  (apply + (map * list list)))

(add-squares 1 2 5)

; Exercise 4
(defn fact [n]
  (apply * (range 1 (inc n))))

(fact 5)

; Exercise 5
; Take First 3 even numbers
(take 3 (filter even? '(1 2 3 4 5 6 7 8)))

; Distinct
(distinct '(1 1 2 3 4 4 5))

; Concat
(concat '(A B C) '(D E F))

; Repeat
(take 3 (repeat 10))

; Interleave: Separate a bunch of numbers with _
(defn interleave-example [list]
  (interleave list (take (count list) (repeat '_))))
(interleave-example [1 2 3 4])

; Drop and Drop-last: Return 2 middle-most elements of even numbered list
(defn middle [list]
  (let [elements (count list)
        to-drop (dec (/ elements 2))]
    (drop-last to-drop (drop to-drop list))))
(middle '(1 2 3 4 5 6 7 8))

; Flatten: Add elements of a seq without using cons
(eval (flatten [+ [1 2 3 4]]))

; Partition: Convert (1 2 3 4) into (3 4 1 2)
(defn conv [list]
  (flatten (reverse (partition 2 list))))
(conv '(1 2 3 4))

; Every?: Is ( () () () ) a list of empty lists
(every? empty? '( () () () ))

; Remove: remove all nil values from a sequence
(remove nil? '(1 2 nil 3 A B))

