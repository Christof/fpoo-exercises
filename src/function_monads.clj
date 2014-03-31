(ns function-monads)
(use 'clojure.algo.monads)

(def function-monad
     (monad [m-result
             (fn [binding-value]
               (fn [] binding-value))

             m-bind
             (fn [monadic-value monadic-continuation]
               (let [binding-value (monadic-value)]
                 (monadic-continuation binding-value)))]))

(def calculation
     (with-monad function-monad
       (let [frozen-step m-result]
         (domonad [a (frozen-step 8)
                   b (frozen-step (+ a 88))]
              (+ a b)))))


;;;; Charging monad

(def charging-monad
     (monad [m-result 
             (fn [result]
               (fn [charge]
                 {:charge charge, :result result}))

             m-bind
             (fn [monadic-value monadic-continuation]
               (fn [charge]
                 (let [enclosed-map (monadic-value charge)
                       binding-value (:result enclosed-map)]
                   ( (monadic-continuation binding-value)
                     (inc charge)))))]))

(def run-and-charge
     (with-monad charging-monad
       (let [frozen-step m-result]
         (domonad [a (frozen-step 8)
                   b (frozen-step (+ a 88))]
                  (+ a b)))))



(use '[clojure.pprint :only [cl-format]])

(def verbose-charging-monad
     (monad [m-result 
             (fn [result]
               (cl-format true "Freezing ~A.~%" result)
               (fn [charge]
                 (cl-format true "Unfrozen calculation gets charge ~A.~%" charge)
                 (cl-format true "... The frozen calculation result was ~A.~%" result)
                 {:charge charge, :result result}))

             m-bind
             (fn [monadic-value monadic-continuation]
               (cl-format true "Making a decision.~%")
               (fn [charge]
                 (let [enclosed-map (monadic-value charge)
                       binding-value (:result enclosed-map)]
                   (cl-format true "Calling continuation with ~A~%" binding-value)
                   (cl-format true "... The charge to increment is ~A~%", charge)
                   ( (monadic-continuation binding-value)
                     (inc charge)))))]))

(println "==========")
(println "Defining run-and-charge.")
         
(def run-and-charge-and-speak
     (with-monad verbose-charging-monad
       (let [frozen-step m-result]
         (domonad [a (frozen-step 8)
                   b (frozen-step (+ a 88))]
                  (+ a b)))))

(println "-----------")
(println "Running run-and-charge.")

; Exercise 3
(def verbose-charging-monad-alternate-inc
     (monad [m-result 
             (fn [result]
               (cl-format true "Freezing ~A.~%" result)
               (fn [charge]
                 (cl-format true "Unfrozen calculation gets charge ~A.~%" charge)
                 (cl-format true "... The frozen calculation result was ~A.~%" result)
                 {:charge (inc charge), :result result}))       ;; <<== change

             m-bind
             (fn [monadic-value continuation]
               (cl-format true "Making a decision.~%")
               (fn [charge]
                 (let [enclosed-map (monadic-value charge)
                       binding-value (:result enclosed-map)]
                   (cl-format true "Calling continuation with ~A~%" binding-value)
                   (cl-format true "... The charge to increment is ~A~%", charge)
                   ( (continuation binding-value) charge))))]))                        ;; <<== change

(println "==========")
(println "Defining run-and-charge.")


; Exercise 4
(defn transform-state [transformer]
  (fn [state]
    {:state (transformer state) :result state}))

(def transform-state-example
  (domonad [b (transform-state inc)]
           b))

(prn (transform-state-example 1))

; Exercise 5


(defn get-state [variable]
       (fn [state]
         {:state state, :result (variable state)}))

(defn assign-state [variable new-state]
  (fn [state]
    (let [old-value (variable state)]
    {:state (assoc state variable new-state) , :result old-value})))

(defn transform-state [variable transformer]
  (fn [state]
    (let [old-value (variable state)]
    {:state (assoc state variable (transformer old-value)) , :result old-value})))

(def state-monad
     (monad [m-result 
             (fn [result]
               (fn [state]
                 {:state state, :result result}))

             m-bind
             (fn [monadic-value monadic-continuation]
               (fn [state]
                 (let [enclosed-map (monadic-value state)
                       binding-value (:result enclosed-map)
                       new-state (:state enclosed-map)]
                   (  (monadic-continuation binding-value) new-state))))]))

(def map-state-example
     (with-monad state-monad
       (domonad [a (get-state :a)
                 old-b (assign-state :b 3)
                 old-c (transform-state :c inc)]
          [a old-b old-c])))
(prn (map-state-example {:a 1 :b 2 :c 5}))
