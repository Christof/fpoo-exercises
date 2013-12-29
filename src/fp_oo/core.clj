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
