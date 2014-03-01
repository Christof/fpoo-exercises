(ns build-zippers)
; Exercise 1
(defn seq-zip [tree] tree)

(defn zdown [zip]
  (seq-zip (first zip)))

(defn znode [zip]
  zip)

(-> '(a b c)
    seq-zip
    zdown
    znode) ; a

(-> '((+ 1 2) 3 4)
    seq-zip
    zdown
    znode) ; (+ 1 2)

(-> '((+ 1 2) 3 4)
    seq-zip
    zdown
    zdown
    znode) ; +
