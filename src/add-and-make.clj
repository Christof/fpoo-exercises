(def point {:x 1, :y 2, :__class_symbol__ 'Point})

(def Point
     (fn [x y]
       {:x x,
        :y y
        :__class_symbol__ 'Point}))

(def x :x)
(def y :y)
(def class-of :__class_symbol__)

(def shift
     (fn [this xinc yinc]
       (Point (+ (x this) xinc)
              (+ (y this) yinc))))

(def Triangle
     (fn [point1 point2 point3]
       {:point1 point1, :point2 point2, :point3 point3
        :__class_symbol__ 'Triangle}))


(def right-triangle (Triangle (Point 0 0)
                              (Point 0 1)
                              (Point 1 0)))

(def equal-right-triangle (Triangle (Point 0 0)
                                    (Point 0 1)
                                    (Point 1 0)))

(def different-triangle (Triangle (Point 0 0)
                                  (Point 0 10)
                                  (Point 10 0)))

; Exercise 1: add
(defn add [point1 point2]
  (Point (+ (x point1) (x point2)) (+ (y point1) (y point2))))
(add (Point 1 2) (Point 3 4))

(defn add2 [point1 point2]
  (shift point1 (x point2) (y point2)))
(add2 (Point 1 2) (Point 3 4))

; Exercise 2: constructor
(defn make [type-constructor & arguments]
  (apply type-constructor arguments))
(make Point 1 2)
(make Triangle (make Point 1 2)
      (make Point 1 3)
      (make Point 3 1))

