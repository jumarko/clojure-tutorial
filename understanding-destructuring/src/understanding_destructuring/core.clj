(ns understanding-destructuring.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(def coords [10 20 30])

;; without knowing destructuring...
(let [x (first coords)
      y (second coords)
      z (last coords)]
  (+ x y z))

;;; with destructuring it's much more concise
;;; -> vector destructuring
(let [[x y z] coords]
  (+ x y z))

;; vector destructuring works on any kind of sequential structure
(def my-list '("Bronn", "Hound" "Mountain"))
(let [[name-1 name-2 name-3] my-list]
  (str name-3 " " name-2 " " name-1))

(def my-string "Jon")
(let [[char-1 char-2 char-3] my-string]
  (str char-1 char-3))
;; note that we actually don't care about the second char which can be confusing for reader
(let [[char-1 _ char-3] my-string]
  (str char-1 char-3))

;; what if I want to the whole list?
(let [[_ name-2 name-3 :as sellswords] my-list]
  [name-2 sellswords] )

;; first and more aka "rest of the list"
(let [[name-1 & more] my-list ]
  more)


;;; map destructuring

(def my-map {:x 10 :y 20 :z 30})
(let [{x :x y :y z :z} my-map]
  (+ x y z))
;; or alternatively - grabbing keys out
(let [{:keys [x y z]} my-map]
  (+ x y z))

;; default values
(let [{:keys [x y z] :or {x 100 y 200}} {:z 50}]
  (+ x y z))


;;; Real world - When to do destructuring:
;;; - nested data structures
;;; - you can always use let for the same thing but destructuring is much more concise

(defn add-everything [[{:keys [a c] [b1 b2] :b} d]]
  (+ a b1 b2 c d))
(add-everything [{:a 1 :b [2 3] :c 4} 5])
