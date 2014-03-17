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
