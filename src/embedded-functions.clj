(def make
     (fn [type & args]
       (apply type args)))

(def send-to
     (fn [object message & args]
       (apply (message (:__methods__ object)) object args)))

(def Point
     (fn [x y]
       {:x x,
        :y y
        :__class_symbol__ 'Point
        :__methods__ {
           :class :__class_symbol__
           :x :x
           :y :y
           :add (fn [this other]
                    (send-to this :shift (send-to other :x) (send-to other :y)))
           :shift (fn [this xinc yinc]
                    (make Point (+ (send-to this :x) xinc)
                                (+ (send-to this :y) yinc)))}}))

; Exercise 4.1: x and y getters, use them in shift, implement add
(def p1 (make Point 1 2))
(def p2 (make Point 3 4))
(def sum (send-to p1 :add p2))
(prn (send-to sum :x))
(prn (send-to sum :y))
