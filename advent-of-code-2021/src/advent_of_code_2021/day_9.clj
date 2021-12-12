(ns advent-of-code-2021.day-9
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [advent-of-code-2021.utils :refer (parse-int)]))

(defn neighbors [grid [x y]]
  (filter #(when (and (>= (first %) 0)
                      (< (first %) (count (grid 0)))
                      (>= (second %) 0)
                      (< (second %) (count grid)))
             %)
          [[(dec x) y]
           [(inc x) y]
           [x (dec y)]
           [x (inc y)]]))

(def input-str (slurp "src/advent_of_code_2021/input_day_9.txt"))
(def tubes (as-> input-str $
             (str/split $ #"\n")
             (map #(str/split % #"") $)
             (mapv #(mapv parse-int %) $)))

(defn height [grid [x y]]
  (get-in grid [y x]))

(defn ex1 [grid]
  (reduce (fn [score [x y]]
            (let [h (height grid [x y])]
              (if (every? #(< h (height grid %))
                          (neighbors grid [x y]))
                (+ score h 1)
                score)))
          0
          (for [x (range (count (grid 0)))
                y (range (count grid))]
            [x y])))


(defn basin [grid basin-set]
  (let [pos-to-add (reduce (fn [pos-to-add pos]
                             (apply conj pos-to-add
                                    (filter #(and (< (height grid %) 9)
                                                  (not (contains? basin-set %)))
                                            (neighbors grid pos))))
                           #{}
                           basin-set)]
    (if (empty? pos-to-add)
      basin-set
      (recur grid (set/union basin-set pos-to-add)))))

(defn ex2 [grid]
  (->> (reduce (fn [minimals [x y]]
                 (let [h (height grid [x y])]
                   (if (every? #(< h (height grid %))
                               (neighbors grid [x y]))
                     (conj minimals [x y])
                     minimals)))
               #{}
               (for [x (range (count (grid 0)))
                     y (range (count grid))]
                 [x y]))
       (map #(basin grid #{%}))
       (map count)
       (sort)
       (take-last 3)
       (apply *)))