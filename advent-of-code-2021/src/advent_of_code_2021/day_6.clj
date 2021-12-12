(ns advent-of-code-2021.day-6
  (:require [clojure.string :as str]
            [advent-of-code-2021.utils :refer (parse-int)]))


(def input-str (slurp "src/advent_of_code_2021/input_day_6.txt"))
(def lanternfish (->> (str/split input-str #",")
                      (mapv parse-int)))

(def lanternfish-test [3,4,3,1,2])

(defn ex1 [fishes]
  (count
   (reduce (fn [fishes _]
             (reduce (fn [next-fishes fish]
                       (if (= fish 0)
                         (conj next-fishes 6 8)
                         (conj next-fishes (dec fish))))
                     []
                     fishes))
           fishes
           (range 80))))

(defn ex2 [fishes]
  (apply + (reduce (fn [fishes _]
                     (-> (vec (rest fishes))
                         (conj (first fishes))
                         (update-in [6] + (first fishes))))
                   (reduce-kv assoc
                              (vec (repeat 9 0))
                              (frequencies fishes))
                   (range 256))))
