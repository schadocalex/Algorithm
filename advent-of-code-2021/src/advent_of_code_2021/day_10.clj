(ns advent-of-code-2021.day-10
  (:require [clojure.string :as str]))

(def input-str (slurp "src/advent_of_code_2021/input_day_10.txt"))
(def lines (as-> input-str $
             (str/split $ #"\n")))

(def opens #{\( \[ \{ \<})
(def close->open {\) \(,  \] \[, \} \{, \> \<})
(def char-score {\) 3, \] 57, \} 1197, \> 25137
                 \( 1, \[ 2, \{ 3, \< 4})

(defn line-score
  ([line] (line-score line []))
  ([line pending]
   (if (empty? line)
     0
     (let [char (first line)]
       (cond
         (contains? opens char)
         (line-score (rest line) (conj pending char))

         (= (peek pending) (close->open char))
         (line-score (rest line) (pop pending))

         :else
         (char-score char))))))

(defn ex1 [lines]
  (apply + (map line-score lines)))

(defn line-score2
  ([line] (line-score2 line []))
  ([line pending]
   (if (empty? line)
     (reduce (fn [score char]
               (+ (* score 5) (char-score char)))
             0
             (reverse pending))
     (let [char (first line)]
       (cond
         (contains? opens char)
         (line-score2 (rest line) (conj pending char))

         (= (peek pending) (close->open char))
         (line-score2 (rest line) (pop pending))

         :else
         nil)))))

(defn ex2 [lines]
  (->> lines
       (map line-score2)
       (filter some?)
       (sort)
       (vec)
       (#(nth % (/ (dec (count %)) 2)))))