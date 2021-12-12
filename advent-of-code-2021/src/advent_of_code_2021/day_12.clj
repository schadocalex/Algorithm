(ns advent-of-code-2021.day-12
  (:require [clojure.string :as str]))


(def input-str (slurp "src/advent_of_code_2021/input_day_12.txt"))
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

(defn is-lower-case [s]
  (= s (str/lower-case s)))

(defn move [graph path]
  (if (= (peek path) "end")
    1
    (apply +
           (map (fn [node]
                  (if (and (not= node "start")
                           (or (not (is-lower-case node))
                               (not (contains? (set path) node))))
                    (move graph (conj path node))
                    0))
                (graph (peek path))))))

(defn ex1 [graph]
  (move graph ["start"]))

(defn move2 [graph path twice]
  (apply +
         (map (fn [node]
                (if (= node "end")
                  1
                  (let [is-small (is-lower-case node)
                        cave-visited (contains? (set path) node)]
                    (if (and (not= node "start")
                             (or
                              (not is-small)
                              (not cave-visited)
                              (not twice)))
                      (move2 graph
                             (conj path node)
                             (or twice (and is-small cave-visited)))
                      0))))
              (graph (peek path)))))

(defn ex2 [graph]
  (move2 graph ["start"] false))