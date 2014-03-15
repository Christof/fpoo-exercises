(ns lazy-world)

(def prompt-and-read
     (fn []
       (print "> ")
       (.flush *out*)
       (.readLine
        (new java.io.BufferedReader *in*))))

; Exercise 1
(def inputs (repeatedly prompt-and-read))
(defn one-charachter-line? [line] (= (count line) 1))
(def singles (filter one-charachter-line? inputs))
;(first singles)

(def inputs (repeatedly prompt-and-read))
;(first singles)

; Exercise 2
(defn starts-with-y-or-n [string]
  (or (.startsWith string "y")
      (.startsWith string "n")))
(def ys-and-ns (filter starts-with-y-or-n  (repeatedly prompt-and-read)))

;;; For exercise 3

(def counted-sum
     (fn [number-count numbers]
       (apply +
              (take number-count
                    numbers))))

(def number-string?
     (fn [string]
       (try
         (Integer/parseInt string)
         true
       (catch NumberFormatException ignored
           false))))

(def to-integer
     (fn [string]
       (Integer/parseInt string)))


(def numbers (map to-integer 
                  (filter number-string? 
                          (repeatedly prompt-and-read))))
(defn counted-sum-console []
  (let [n numbers]
    (counted-sum (first numbers) (rest numbers))
    )
  )
