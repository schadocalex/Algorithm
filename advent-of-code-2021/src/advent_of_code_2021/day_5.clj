(ns advent-of-code-2021.day-5
  (:require [clojure.string :as str]
            [advent-of-code-2021.utils :refer (parse-int)]))


(def input-str (slurp "src/advent_of_code_2021/input_day_5.txt"))
(def vents (->> (str/split input-str #"\n")
                (mapv (fn [line]
                        (->> (str/split line #" -> ")
                             (mapv #(mapv parse-int (str/split % #","))))))))

(def vents-test [[[0,9] [5,9]]
                 [[8,0] [0,8]]
                 [[9,4] [3,4]]
                 [[2,2] [2,1]]
                 [[7,0] [7,4]]
                 [[6,4] [2,0]]
                 [[0,9] [2,9]]
                 [[3,4] [1,4]]
                 [[0,0] [8,8]]
                 [[5,5] [8,2]]])


(defn generic-range [x1 x2]
  (let [step (if (<= x1 x2) 1 -1)]
    (range x1 ((if (= step 1) inc dec) x2) step)))

(defn ex1 [vents]
  (let [positions (for [[[x1 y1] [x2 y2]] vents
                        :when (or (= x1 x2) (= y1 y2))
                        x (generic-range x1 x2)
                        y (generic-range y1 y2)]
                    [x y])
        grid (reduce (fn [grid pos]
                       (assoc grid pos (inc (get grid pos 0))))
                     {}
                     positions)]
    (count (filter #(>= (val %) 2) grid))))

(defn ex2 [vents]
  (let [positions-hv (for [[[x1 y1] [x2 y2]] vents
                           :when (or (= x1 x2) (= y1 y2))
                           x (generic-range x1 x2)
                           y (generic-range y1 y2)]
                       [x y])
        positions-diag (for [[[x1 y1] [x2 y2]] vents
                             :when (not (or (= x1 x2) (= y1 y2)))
                             :let [x (generic-range x1 x2)
                                   y (generic-range y1 y2)]
                             pos (map vector x y)]
                         pos)
        grid (reduce (fn [grid pos]
                       (assoc grid pos (inc (get grid pos 0))))
                     {}
                     (concat positions-hv positions-diag))]
    (count (filter #(>= (val %) 2) grid))))