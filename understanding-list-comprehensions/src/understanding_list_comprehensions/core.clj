(ns understanding-list-comprehensions.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(for [number [1 2 3] ] (* number 2) )
;; equivalent with
(map #(* % 2) [1 2 3])

;;; Better use case for for-comprehension
(for [number [1 2 3]
      letter [:a :b :c]]
  (str number letter))
;; more difficult to rewrite to map:
(mapcat (fn [number] (map (fn [letter] (str number letter) ) [:a :b :c])
       )
     [1 2 3])

;;; 3 input lists
;; "range" reminder
(range 1 10)
(count
 (for [tumbler-1 (range 10)
      tumbler-2 (range 10)
      tumbler-3 (range 10)
      :when (or (= tumbler-1 4)
                (= tumbler-2 4)
                (= tumbler-3 4))]
  [tumbler-1 tumbler-2 tumbler-3])
 )
;; another one
(count
 (for [tumbler-1 (range 10)
      tumbler-2 (range 10)
      tumbler-3 (range 10)
      :when (or (= tumbler-1 tumbler-2)
                (= tumbler-2 tumbler-3)
                (= tumbler-3 tumbler-1))]
  [tumbler-1 tumbler-2 tumbler-3])
 )

;;; To sum up - for comprehension enables us to generate data using filtering by :when clause
;;; while still being succinct and understandable
;;; Use it anytime you have multiple lists and you want to generate single list, potentialy filtering the data.

;; E.g. generating ticket numbers
(def capital-letters (map char (range (int \A) (int \Z)) ))
(def blacklisted #{\I \O})
(for [letter-1 capital-letters
      letter-2 capital-letters
      :when (and (not (blacklisted letter-1))
                 (not (blacklisted letter-2)))]
  (str letter-1 letter-2))

;; Other clauses
;; :let - if you want to manipulate list that you already defined
;; :while - keep processing until the condition is satisfied
(for [number [1 2 3]
      :let [tripled (* number 3)]
      :while (odd? tripled)]
  tripled)



;;; Let's try to solve problem with for comprehension
;;; Project euler: https://projecteuler.net/archives
;;; - first problem is grest but easy (self exercise)
;;; - 4th problem is shown in video

;; 1st problem: https://projecteuler.net/problem=1
;; Multiplies of 3 and 5
;; "If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3,5,6 and 9. The sum of these multiples is 23. Find the sum of all the multiples of 3 or 5 below 1000."
(apply + (for [number (range 1 1000)
      :when (or (zero? (mod number 3))
                (zero? (mod number 5)))]
  number)
       )

;; 4th problem : https://projecteuler.net/problem=4
;; Largest palindrome product
;; "A palindromic number reads the same both ways.
;; The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 * 99
;; Find the largest palindrome made from the product of two 3-digit numbers.
;; -- My solution:
(defn is-palindrom? [number]
  (= (.toString number)
     (apply str
            (reverse (.toString number)))
     ))
 (apply max (for [number1 (range 100 1000)
                  number2 (range 100 1000)
                  :when (is-palindrom? (* number1 number2))]
              (* number1 number2)))

;; Reference solution
(defn- palindrom? [number]
  (= (str number) (clojure.string/reverse (str number))))
;; some quick manual checks
(palindrom? 100)
(palindrom? 101)
(apply max (for [three-digit-number-1 (range 100 1000)
                 three-digit-number-2 (range 100 1000)
                 :let [product (* three-digit-number-1 three-digit-number-2)]
                 :when (palindrom? product)]
             product))
