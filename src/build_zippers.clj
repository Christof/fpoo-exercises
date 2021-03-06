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

; Exercise 4
(defn zreplace [zip subtree]
  (assoc zip
         :here subtree))

(-> (seq-zip '(a b c)) zdown zright (zreplace 3) znode) ; 3
(-> (seq-zip '(a b c)) zdown zright (zreplace 3) zright zleft znode) ; 3
(-> (seq-zip '(a b c)) zdown zright (zreplace 3) zleft zright zright znode) ; c

; Exercise 5
(defn zreplace [zip subtree]
  (assoc zip
         :here subtree
         :changed true))

(defn zup [zip]
  (let [unmodified (first (:parents zip))]
    (cond
      (nil? unmodified)
      nil

      (:changed zip)
      (assoc unmodified
             :here (concat (:lefts zip) (list (:here zip)) (:rights zip))
             :changed true)
      :else
      unmodified)))

(-> (seq-zip '(a b c)) zdown zright (zreplace 3) zup znode) ; (a 3 c)
(-> (seq-zip '(a b c)) zdown zright (zreplace 3)
    zright (zreplace 4) zup znode) ; (a 3 4)
(-> (seq-zip '(a)) zdown (zreplace 3) zup zup) ; nil
(-> (seq-zip '(a (b) c)) zdown zright zdown (zreplace 3) zroot) ; (a (3) c)
(-> (seq-zip '(a (b) c)) zdown zright zdown (zreplace 3) 
    zup zright (zreplace 4) zroot) ; (a (3) 4)

; Exercise 6
; Step 1
(defn znext [zip]
  (zright zip)
  )
(-> (seq-zip '( a b)) zdown znext znode) ; b
(-> (seq-zip '(a ((b)))) zdown znext znode) ; ((b))

; Step 2
(def zbranch? (comp seq? znode))
(defn znext [zip]
  (cond (and (zbranch? zip) (not (nil? (zdown zip))))
        (zdown zip)
        :else
        (zright zip)
        ))
(-> (seq-zip '(a b)) znext znode) ; a
(-> (seq-zip '(() b)) znext znext znode) ; b
(defn znext [zip]
  (or (and (zbranch? zip) (zdown zip))
      (zright zip)))

; Step 3
(defn znext [zip]
  (letfn [(search-up [zip]
            (or (-> zip zup zright)
                (-> zip zup search-up)))]
  (or (and (zbranch? zip) (zdown zip))
      (zright zip)
      (search-up zip)
      )))

(-> (seq-zip '((a) b)) zdown zdown znext znode) ; b
(-> (seq-zip '(((a)) b)) zdown zdown zdown znext znode) ; b

; Step 4
(def zend? :end?)
(defn znext [zip]
  (letfn [(search-up [zip]
            (if (nil? (zup zip))
              (assoc zip
                     :end? true)
            (or (-> zip zup zright)
                (-> zip zup search-up))))]
  (or (and (zbranch? zip) (zdown zip))
      (zright zip)
      (search-up zip)
      )))
(-> (seq-zip '()) zend?) ; false (returns actually nil)
(-> (seq-zip '()) znext zend?) ; true
(def zipper (-> (seq-zip '(a)) znext (zreplace 5) znext))
(zend? zipper) ; true
(zroot zipper) ; (5)
