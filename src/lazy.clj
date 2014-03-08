(ns lazy)
(def rrange
     (fn [first past-end]
       (new clojure.lang.LazySeq
            (fn []
              (if (= first past-end)
                nil
                (cons first
                      (rrange (inc first) past-end)))))))

; Exercise 1
(defn mapr [f s]
  (new clojure.lang.LazySeq
       (fn []
         (if (empty? s)
           nil
           (cons (f (first s)) (mapr f (rest s)))))))

(mapr inc [1 2 3 4])

; Exercise 2
(defn filterr [pred s]
  (new clojure.lang.LazySeq
       (fn []
         (cond (empty? s)
               nil

               (pred (first s))
               (cons (first s) (filterr pred (rest s)))

               :else
               (filterr pred (rest s))))))

(filterr odd? [-1 2 -3 5])
(filter odd? [-1 2 -3 5])

;;; For exercise 3.

;;; This throws an exception if the last element of a sequence is
;;; evaluated when only the `first` is asked for. It uses a large
;;; source sequence because many builtin sequence functions precalculate
;;; more than one element when asked for the first. Therefore, if 
;;; the source sequence were something like [1 2], `filter` would count
;;; as eager. The number of elements precalculated is subject to change, but
;;; it's awfully unlikely to be 10,000.

(def eager?
     (fn [filter-function]
       (try
         (not (first (filter-function (fn [elt]
                                        (if (= elt 9999)
                                          (throw (Error.)))
                                        true)
                                      (range 10000))))
         (catch Error e
           true))))
(eager? filter)
(eager? filterr)
(eager? mapr)


(defn filtere [pred s]
         (cond (empty? s)
               nil

               (pred (first s))
               (cons (first s) (filtere pred (rest s)))

               :else
               (filtere pred (rest s))))
(filtere odd? [-1 2 -3 5])
(eager? filtere)
