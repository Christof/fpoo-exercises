(load-file "src/klass-1.clj")

;; This imports a function from another namespace. (Think package or module.)
(use '[clojure.pprint :only [cl-format]])
;;; The two class/pairs from which everything else can be built

;; Anything
(install (basic-class 'Anything,
                      :left 'MetaAnything,
                      :up nil,
                      {
                       :add-instance-values identity
                       :method-missing
                       (fn [this message args]
                         (throw (Error. (cl-format nil "A ~A does not accept the message ~A."
                                                   (send-to this :class-name)
                                                   message))))
                       :to-string (fn [this] (str this))
                       :class-name :__class_symbol__    
                       :class (fn [this] (class-from-instance this))
                       }))
                            
(install (basic-class 'MetaAnything,
                      :left 'Klass,
                      :up 'Klass,
                      { 
                       }))


;; Klass
(install (basic-class 'Klass,
                      :left 'MetaKlass,
                      :up 'Anything,
                      {
                       :new
                       (fn [class & args]
                         (let [seeded {:__class_symbol__ (:__own_symbol__ class)}]
                           (apply-message-to class seeded :add-instance-values args)))
                       :to-string
                       (fn [this] 
                         (cl-format nil "class ~A "
                                    (:__own_symbol__ this)))
                      }))
                            
(install (basic-class 'MetaKlass,
                      :left 'Klass,
                      :up 'Klass,
                      {
                       :new
                       (fn [this
                            new-class-symbol superclass-symbol
                            instance-methods class-methods]
                         ;; Metaclass
                         (install
                          (basic-class (metasymbol new-class-symbol)
                                       :left 'Klass
                                       :up 'Klass
                                       class-methods))
                         ;; Class
                         (install
                          (basic-class new-class-symbol
                                       :left (metasymbol new-class-symbol)
                                       :up superclass-symbol
                                       instance-methods)))
                       }))

;; An example class:

(send-to Klass :new
         'Point 'Anything
         {
          :x :x
          :y :y 

          :add-instance-values
          (fn [this x y]
            (assoc this :x x :y y))
          
          :to-string
          (fn [this]
            (cl-format nil "A ~A like this: [~A, ~A]"
                       (send-to this :class-name)
                       (send-to this :x)
                       (send-to this :y)))

          :shift
          (fn [this xinc yinc]
            (let [my-class (send-to this :class)]
              (send-to my-class :new
                                (+ (:x this) xinc)
                                (+ (:y this) yinc))))
          :add
          (fn [this other]
            (send-to this :shift (:x other)
                                 (:y other)))
         } 
         
         {
          :origin (fn [class] (send-to class :new 0 0))
         })


(prn (send-to Point :to-string))
(prn (send-to Klass :to-string))
(prn (send-to (send-to Anything :new) :to-string))
(prn (send-to (send-to Point :new 1 2) :to-string))

(send-to Klass :new
         'ColoredPoint 'Point
         {
          :color "black"
          }
         {
          })

(def Klass
  (assoc-in Klass
            [:__instance_methods__ :ancestors]
            (fn [class]
              ;(remove invisible?
                      (reverse (lineage (:__own_symbol__ class))))))
(send-to Point :ancestors) ;(Point Anything)
(send-to ColoredPoint :ancestors) ;(ColoredPoint Point Anything)
(send-to Klass :ancestors) ;(Klass Anything)

(def Anything
  (assoc-in Anything [:__instance_methods__ :class-name]
            (fn [this]
              (first (send-to (eval (:__class_symbol__ this)) :ancestors )))))

(send-to Point :class-name) ;Klass




