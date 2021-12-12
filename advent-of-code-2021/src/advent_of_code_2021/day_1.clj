(ns advent-of-code-2021.day-1
  (:require [clojure.string :as str]
            [advent-of-code-2021.utils :refer (parse-int)]))

(def input-str (slurp "src/advent_of_code_2021/input_day_1_refact.txt"))
(def depths (map parse-int (str/split input-str #"\n")))

(defn ex1 [depths]
  (->> depths
       (partition 2 1)
       (filter (fn [[a b]] (< a b)))
       (count)))

(defn ex2 [depths]
  (->> depths
       (partition 3 1)
       (map (partial apply +))
       (ex1)))
