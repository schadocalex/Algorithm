(ns advent-of-code-2021.day-4
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [advent-of-code-2021.utils :refer (parse-int)]))


(def input-str (slurp "src/advent_of_code_2021/input_day_4.txt"))
(def bingo (let [blocs (str/split input-str #"\n\n")]
             {:numbers (mapv parse-int (str/split (first blocs) #","))
              :boards (mapv (fn [txt]
                              (->> (str/split txt #"\n")
                                   (map #(str/split % #" +"))
                                   (map (partial filter seq))
                                   (mapv (partial mapv parse-int))))
                            (rest blocs))}))

(def bingo-test {:numbers [7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1]
                 :boards [[[22 13 17 11 0]
                           [8 2 23 4 24]
                           [21 9 14 16 7]
                           [6 10 3 18 5]
                           [1 12 20 15 19]]
                          [[3 15 0 2 22]
                           [9 18 13 17 5]
                           [19 8 7 25 23]
                           [20 11 10 24 4]
                           [14 21 16 12 6]]
                          [[14 21 17 24 4]
                           [10 16 15 9 19]
                           [18 8 23 26 20]
                           [22 11 13 6 5]
                           [2 0 12 3 7]]]})

(defn convert-boards [board]
  {:set (set (apply concat board))
   :winning-sets (mapv set (concat board (apply map vector board)))})

(defn ex1 [bingo]
  (let [boards (map convert-boards (:boards bingo))]
    (some (fn [numbers]
            (let [numbers-set (set numbers)]
              (some (fn [board]
                      (when (some #(set/subset? % numbers-set) (:winning-sets board))
                        (* (apply + (set/difference (:set board) numbers-set))
                           (last numbers))))
                    boards)))
          (reductions conj [] (:numbers bingo)))))

(defn ex2 [bingo]
  (let [boards (map convert-boards (:boards bingo))

        winning-boards
        (reduce (fn [winning-boards numbers]
                  (let [numbers-set (set numbers)]
                    (reduce (fn [winning-boards board]
                              (if (and (not (contains? winning-boards board))
                                       (some #(set/subset? % numbers-set) (:winning-sets board)))
                                (assoc winning-boards board
                                       {:score (* (apply + (set/difference (:set board) numbers-set))
                                                  (last numbers))
                                        :numbers numbers})
                                winning-boards))
                            winning-boards
                            boards)))
                {}
                (reductions conj [] (:numbers bingo)))]
    (val (apply max-key (comp count :numbers val) winning-boards))))
