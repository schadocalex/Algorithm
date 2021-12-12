(ns advent-of-code-2021.day-12
  (:require [clojure.string :as str]))


(def input-str (slurp "src/advent_of_code_2021/input_day_12_dev1.txt"))
(def edges (as-> input-str $
             (str/split $ #"\n")
             (mapv #(str/split % #"-") $)))

(def nodes (into #{} (apply concat edges)))
(def graph (reduce (fn [g [a b]]
                     (-> g
                         (update-in [a] conj b)
                         (update-in [b] conj a)))
                   (zipmap nodes (repeat (count nodes) []))
                   edges))

(def move [])