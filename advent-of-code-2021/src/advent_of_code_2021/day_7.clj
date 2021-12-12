(ns advent-of-code-2021.day-7
  (:require [clojure.string :as str]
            [advent-of-code-2021.utils :refer (parse-int)]))


(def input-str (slurp "src/advent_of_code_2021/input_day_7.txt"))
(def crabs (->> (str/split input-str #",")
                (mapv parse-int)))

(def crabs-test [16,1,2,0,4,2,7,1,2,14])

(defn abs [x] (max x (- x)))

(defn fuel [crabs goal]
  (apply + (map #(abs (- % goal)) crabs)))

(defn ex1 [crabs]
  (fuel crabs
        (apply min-key
               (partial fuel crabs)
               (range (apply min crabs) (inc (apply max crabs))))))

(defn fuel2 [crabs goal]
  (apply + (map (fn [crab]
                  (let [dist (abs (- crab goal))]
                    (/ (* dist (inc dist)) 2))) crabs)))

(defn ex2 [crabs]
  (fuel2 crabs
         (apply min-key
                (partial fuel2 crabs)
                (range (apply min crabs) (inc (apply max crabs))))))