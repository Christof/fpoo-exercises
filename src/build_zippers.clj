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
           :parents (cons zip (:here zip)))))

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
