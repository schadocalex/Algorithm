(ns advent-of-code-2021.day-2
  (:require [clojure.string :as str]
            [advent-of-code-2021.utils :refer (parse-int)]))

(def input-str (slurp "src/advent_of_code_2021/input_day_2.txt"))
(def commands (->> (str/split input-str #"\n")
                   (map #(str/split % #" "))
                   (map (fn [[dir x]] [dir (parse-int x)]))))
(def commands-test [["forward" 5]
                    ["down" 5]
                    ["forward" 8]
                    ["up" 3]
                    ["down" 8]
                    ["forward" 2]])

(def move {"forward" (fn [x [f d]] [(+ f x) d])
           "down" (fn [x [f d]] [f (+ d x)])
           "up" (fn [x [f d]] [f (- d x)])})

(defn ex1 [commands]
  (->> commands
       (reduce
        (fn [pos [dir x]]
          ((move dir) x pos))
        [0 0])))

(def move2 {"forward" (fn [x [horizontal depth aim]] [(+ horizontal x) (+ depth (* aim x)) aim])
            "down" (fn [x [horizontal depth aim]] [horizontal depth (+ aim x)])
            "up" (fn [x [horizontal depth aim]] [horizontal depth (- aim x)])})

(defn ex2 [commands]
  (->> commands
       (reduce
        (fn [pos [dir x]]
          ((move2 dir) x pos))
        [0 0 0])))