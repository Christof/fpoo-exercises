(ns sequence-m)

(use 'clojure.algo.monads)

(def maybe-sequence-monad-monadifier list)

(def maybe-sequence-monad-decider
  (fn [step-value monadic-continuation]
    (let [continuation (fn [binding-value]
                         (if (nil? binding-value)
                           (maybe-sequence-monad-monadifier binding-value)
                           (monadic-continuation binding-value)))]
      (mapcat continuation step-value))))


(def maybe-sequence-monad
     (monad [m-result maybe-sequence-monad-monadifier
             m-bind maybe-sequence-monad-decider]))

(prn
 (with-monad maybe-sequence-monad
   (domonad [a [1 2 3]
             b [-1, 1]]
            (* a b)))) ; (-1 1 -2 2 -3 3)

(prn 
 (with-monad maybe-sequence-monad
   (domonad [a [1 nil 3]
             b [-1 1]]
            (* a b)))) ; (-1 1 nil -3 3)
