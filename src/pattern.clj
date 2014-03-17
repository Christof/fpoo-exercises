(ns pattern)
(use 'patterned.sweet)

(defpatterned count-sequence
  [so-far [           ] ] so-far
  [so-far [head & tail] ] (count-sequence (inc so-far) tail))

; Exercise 1
(defpatterned count-sequence
  [so-far [           ] ] so-far
  [sequence] (count-sequence 0 sequence)
  [so-far [head & tail] ] (count-sequence (inc so-far) tail))

(count-sequence '(:a :b :c))

; Exercise 2
(defpatterned pattern-reduce
  [fun so-far []] so-far
  [fun so-far [head & tail]] (pattern-reduce fun (fun so-far head) tail))

(pattern-reduce (fn [so-far elt] (cons elt so-far))
                []
                [:a :b :c])
