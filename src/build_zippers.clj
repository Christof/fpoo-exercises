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

; Exercise 2
(defn seq-zip [tree]
  {:here tree
   :parents '() })

(defn zdown [zip]
  (if (empty? (:here zip))
    nil
    (assoc zip
           :here (first (:here zip))
           :parents (cons zip (:parents zip)))))

(defn znode [zip]
  (:here zip))

(defn zup [zip]
  (if (empty? (:parents zip))
    nil
    (first (:parents zip))))


(-> '(a b c)
    seq-zip
    zdown
    zup
    znode) ; (a b c)

(-> '(a b c) seq-zip zup) ; nil
(-> '() seq-zip zdown) ; nil

(defn zroot [zip]
  (if (empty? (:parents zip))
    (znode zip)
    (zroot (zup zip)))
  )

(-> '(((a)) b c)
    seq-zip
    zdown
    zdown
    zdown
    zroot
    )

; Exercise 3
(defn seq-zip [tree]
  {:here tree
   :parents '()
   :lefts '()
   :rights '() })

(defn zdown [zip]
  (if (empty? (:here zip))
    nil
    (assoc zip
           :here (first (:here zip))
           :lefts '()
           :rights (rest (:here zip))
           :parents (cons zip (:parents zip)))))
(defn zright [zip]
  (if (empty? (:rights zip))
    nil
    (assoc zip
           :here (first (:rights zip))
           :lefts (concat (:lefts zip) (list (:here zip)))
           :rights (rest (:rights zip)))))
(defn zleft [zip]
  (if (empty? (:lefts zip))
    nil
    (assoc zip
           :here (last (:lefts zip))
           :lefts (butlast (:lefts zip))
           :rights (cons (last zip) (:rights zip)))))

(-> (seq-zip '(a b c)) zdown zright znode) ; b
(-> (seq-zip '(a b c)) zdown zright zright zleft znode) ; b
(-> (seq-zip '(a b c)) zdown zleft) ; nil
(-> (seq-zip '(a b c)) zdown zright zright zright) ; nil
(-> (seq-zip '(a b c)) zdown zup znode) ; (a b c)


