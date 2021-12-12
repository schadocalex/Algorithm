(ns advent-of-code-2021.day-8
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def input-str (slurp "src/advent_of_code_2021/input_day_8.txt"))
(def signal (as-> input-str $
              (str/split $ #"\n")
              (map #(str/split % #"(\ \|\ )") $)
              (mapv (fn [bloc]
                      (mapv #(str/split % #" ") bloc)) $)))

(defn ex1 [signal]
  (count
   (filter #(contains? #{2 3 4 7} (count %))
           (apply concat (map second signal)))))

(defn decode [signal]
  (let [signal (set (map (comp set seq) signal))
        s1 (some #(when (= (count %) 2) %) signal)
        signal (disj signal s1)
        s4 (some #(when (= (count %) 4) %) signal)
        signal (disj signal s4)
        s7 (some #(when (= (count %) 3) %) signal)
        signal (disj signal s7)
        s8 (some #(when (= (count %) 7) %) signal)
        signal (disj signal s8)
        s9 (some #(when (and (= (count %) 6) (set/subset? s4 %)) %) signal)
        signal (disj signal s9)
        s0 (some #(when (and (= (count %) 6) (set/subset? s1 %)) %) signal)
        signal (disj signal s0)
        s6 (some #(when (= (count %) 6) %) signal)
        signal (disj signal s6)
        s3 (some #(when (set/subset? s1 %) %) signal)
        signal (disj signal s3)
        s5 (set/difference s9 (set/difference s8 s6))
        signal (disj signal s5)
        s2 (first signal)]
    {s1 1, s4 4, s7 7, s8 8, s9 9, s0 0, s6 6, s3 3, s5 5, s2 2}))

(defn convert [decode-map codes]
  (let [codes (mapv (comp set seq) codes)]
    (+ (* 1000 (decode-map (codes 0)))
       (* 100 (decode-map (codes 1)))
       (* 10 (decode-map (codes 2)))
       (* 1 (decode-map (codes 3))))))

(defn ex2 [signals]
  (apply + (map #(convert (decode (first %)) (second %)) signals)))