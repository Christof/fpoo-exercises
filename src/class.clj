(defn method-from-message [class message]
  (message (:__instance_methods__ class)))

(defn class-from-instance [instance]
  (eval (:__class_symbol__ instance)))

(defn apply-message-to [class instance message args]
  (apply (method-from-message class message)instance args))

(def make
     (fn [class & args]
       (let [seeded {:__class_symbol__ (:__own_symbol__ class)}]
         (apply-message-to class seeded :add-instance-values args))))

(def send-to
     (fn [instance message & args]
       (let [class (class-from-instance instance)]
         (apply-message-to class instance message args))))


(def Point
{
  :__own_symbol__ 'Point
  :__instance_methods__
  {
    :add-instance-values (fn [this x y]
                           (assoc this :x x :y y))
    :class-name :__class_symbol__
    :class (fn [this] (class-from-instance this))
    :shift (fn [this xinc yinc]
             (make Point (+ (:x this) xinc)
                         (+ (:y this) yinc)))
    :add (fn [this other]
           (send-to this :shift (:x other)
                                (:y other)))
   }
 })

;; Exercise 1
(def a-point (make Point 1 2))
(prn (apply-message-to Point a-point :shift [1 3]))
(prn (send-to a-point :shift 1 3))

;; Exercise 2
(prn (send-to a-point :class-name))
(prn (send-to a-point :class))


;; For exercise 4
(def Holder  
{
  :__own_symbol__ 'Holder
  :__instance_methods__
  {
    :add-instance-values (fn [this held]
                           (assoc this :held held))
  }
})

