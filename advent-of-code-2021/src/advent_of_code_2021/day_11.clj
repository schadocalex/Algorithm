(ns advent-of-code-2021.day-11
  (:require [clojure.string :as str]
            [advent-of-code-2021.utils :refer (parse-int)]))


(def input-str (slurp "src/advent_of_code_2021/input_day_11.txt"))
(def octopuses (as-> input-str $
                 (str/split $ #"\n")
                 (mapv #(mapv parse-int (str/split % #"")) $)))

(defn neighbors [grid [x y]]
  (for [x2 (range (- x 1) (+ x 2))
        :when (and (>= x2 0) (< x2 (count (grid 0))))
        y2 (range (- y 1) (+ y 2))
        :when (and (>= y2 0) (< y2 (count grid)))
        :when (or (not= x x2) (not= y y2))]
    [x2 y2]))

(defn inc-energy [grid [x y]]
  (let [cur (get-in grid [y x])]
    (if (< cur 9)
      (update-in grid [y x] inc)
      (if (= cur 9)
        (reduce inc-energy
                (update-in grid [y x] inc)
                (neighbors grid [x y]))
        grid))))

(def flashes (atom 0))

(defn step [grid]
  (let [new-grid (reduce inc-energy grid
                         (for [x (range (count (grid 0)))
                               y (range (count grid))]
                           [x y]))]
    (mapv (fn [line] (mapv #(if (= % 10) (do (swap! flashes inc) 0) %) line)) new-grid)))

(defn ex1 [octopuses]
  (reset! flashes 0)
  (nth (iterate step octopuses) 100)
  (print @flashes))

(def steps (atom 0))

(defn ex2 [octopuses]
  (reset! steps -1)
  (some (fn [grid]
          (swap! steps inc)
          (every? (fn [line] (every? #(= % 0) line)) grid))
        (iterate step octopuses))
  (print @steps))