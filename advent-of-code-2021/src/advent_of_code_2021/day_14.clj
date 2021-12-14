(ns advent-of-code-2021.day-14
  (:require [clojure.string :as str]))

(def input-str (slurp "src/advent_of_code_2021/input_day_14.txt"))
(let [[template-str rules-str] (str/split input-str #"\n\n")]
  (def template template-str)
  (def rules (->> rules-str
                  (str/split-lines)
                  (map #(str/split % #"\ \-\>\ "))
                  (apply map vector)
                  (apply zipmap))))

(defn step [rules template]
  (let [final-str (str (reduce (fn [next-template letters]
                                 (if-let [inter-value (get rules (str/join letters))]
                                   (str next-template (first letters) inter-value)
                                   (apply str next-template (first letters))))
                               ""
                               (partition 2 1 template)) (last template))]
    final-str))

(defn ex1 [template rules]
  (let [final-template (nth (iterate (partial step rules) template) 10)
        values (vals (frequencies final-template))]
    (println (frequencies final-template))
    (- (apply max values) (apply min values))))

(defn update-with-default [map key fn default]
  (assoc map key (fn (get map key default))))

(defn step2 [rules couples]
  (reduce-kv (fn [next-couples letters nb]
               (if-let [inter-value (get rules letters)]
                 (-> next-couples
                     (update-with-default (str (first letters) inter-value) #(+ nb %) 0)
                     (update-with-default (str inter-value (second letters)) #(+ nb %) 0))
                 (update-with-default next-couples letters inc 0)))
             {}
             couples))

(defn ex2 [template rules]
  (let [initial-couples (frequencies (map (partial apply str) (partition 2 1 template)))
        final-couples (nth (iterate (partial step2 rules) initial-couples) 40)
        occurences (apply concat [[(first template) 1] [(last template) 1]]
                          (map (fn [[key nb]] [[(first key) nb] [(second key) nb]]) final-couples))
        freqs (apply merge-with + (map #(apply hash-map %) occurences))
        values (vals freqs)]
    (println freqs)
    (/ (- (apply max values) (apply min values)) 2)))
