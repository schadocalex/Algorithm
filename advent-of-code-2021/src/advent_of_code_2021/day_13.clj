(ns advent-of-code-2021.day-13
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [advent-of-code-2021.utils :refer (parse-int)]))

(def input-str (slurp "src/advent_of_code_2021/input_day_13.txt"))
(let [[coords-str folds-str] (str/split input-str #"\n\n")]
  (def coords (->> coords-str
                   (str/split-lines)
                   (map #(str/split % #","))
                   (mapv (partial mapv parse-int))
                   (set)))
  (def folds (mapv (fn [line]
                     (let [[var x] (str/split (subs line 11) #"\=")]
                       [var (parse-int x)]))
                   (str/split-lines folds-str))))

(defn fold-x [coords fold-x]
  (let [to-fold (set/select #(> (first %) fold-x) coords)
        remaining-set (set/select #(< (first %) fold-x) coords)]

    (reduce (fn [folded-set [x y]]
              (conj folded-set [(- (* 2 fold-x) x) y]))
            remaining-set
            to-fold)))

(defn fold-y [coords fold-y]
  (let [to-fold (set/select #(> (second %) fold-y) coords)
        remaining-set (set/select #(< (second %) fold-y) coords)]

    (reduce (fn [folded-set [x y]]
              (conj folded-set [x (- (* 2 fold-y) y)]))
            remaining-set
            to-fold)))

(defn fold [coords [dir z]]
  (case dir
    "x" (fold-x coords z)
    "y" (fold-y coords z)))

(defn ex1 [coords folds]
  (count (fold coords (first folds))))

(defn ex2 [coords folds]
  (let [final-coords (reduce fold coords folds)
        max-x (first (apply max-key first final-coords))
        max-y (second (apply max-key second final-coords))]
    (doseq [y (range (inc max-y))]
      (let [x-values (map first (set/select #(= (second %) y) final-coords))
            line (reduce #(assoc %1 %2 "#") (vec (repeat max-x " ")) x-values)]
        (println (str/join line))))))