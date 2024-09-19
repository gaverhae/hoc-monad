(ns t.core
  (:gen-class))

;; helper macros

(defmacro match
  [expr & cases]
  (let [e (gensym)]
    `(let [~e ~expr]
       (case (first ~e)
         ~@(->> (partition 2 cases)
                (mapcat (fn [[pat body]]
                          [(first pat)
                           `(let [~(vec (cons '_ (rest pat))) ~e]
                              ~body)])))))))

(defmacro mo
  [bindings]
  (if (#{0 1} (count bindings))
    (throw
      (RuntimeException. "invalid number of elements in mo bindings"))
    (let [[n v & r] bindings]
      (if (empty? r)
        v
        [:bind v `(fn [~n] (mo ~r))]))))

;; simple, no-op monad to get us started

(def no-op-example
  (mo [a [:return 5]
       b [:return 7]
       _ [:return (+ a b)]]))

(defn no-op-run
  [ma]
  (match ma
    [:return a] :todo
    [:bind ma f] :todo))

;; short-circuiting monad

(defn sc-add
  [a b]
  [:return (+ a b)])

(defn sc-div
  [n q]
  (if (zero? q)
    [:stop {:error :div-by-zero}]
    [:return (/ n 1.0 q)]))

(defn sc-sqrt
  [a]
  (if (neg? a)
    [:stop {:error :neg-sqrt}]
    [:return (Math/sqrt a)]))

(defn sc-sqr-inv
  [a]
  (mo [sqrt (sc-sqrt a)
       inv (sc-div 1 sqrt)]))

(defn sc-example
  [x]
  (mo [a [:return x]
       b (sc-add a 7)
       c [:return (- b 3)]
       d (sc-sqr-inv c)
       _ [:return (* d 6)]]))

(defn sc-run
  [ma]
  (match ma
    [:return a] :todo
    [:bind ma f] :todo
    [:stop info] :todo))

;; cooperative multitasking

(def co-1
  (mo [a [:return 1]
       b [:return 2 + a]
       _ (:output b)
       c [:return (* a b)]
       _ (:output c)]))

(def co-2
  (mo [a [:return 10]
       b [:return 20 + a]
       _ (:output b)
       c [:return (* a b)]
       _ (:output c)]))

(defn co-run
  [& mas]
  :todo)

(defn -main
  [& args]
  (println "Hello, World!"))
