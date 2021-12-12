(ns advent-of-code-2021.day-3
  (:require [clojure.string :as str]
            [advent-of-code-2021.utils :refer (parse-int)]))

(defn ** [x n] (reduce * (repeat n x)))

(def input-str (slurp "src/advent_of_code_2021/input_day_3.txt"))
(def report (->> (str/split input-str #"\n")
                 (map #(str/split % #""))
                 (map #(map parse-int %))))

(def report-test [[0 0 1 0 0]
                  [1 1 1 1 0]
                  [1 0 1 1 0]
                  [1 0 1 1 1]
                  [1 0 1 0 1]
                  [0 1 1 1 1]
                  [0 0 1 1 1]
                  [1 1 1 0 0]
                  [1 0 0 0 0]
                  [1 1 0 0 1]
                  [0 0 0 1 0]
                  [0 1 0 1 0]])

(defn bits->base10 [bits]
  (->> bits
       (reverse)
       (map-indexed #(* (** 2 %1) %2))
       (apply +)))

(defn get-val [comp-fn arr equally-default]
  (let [freq (frequencies arr)]
    (if (= (get freq 1) (get freq 0))
      equally-default
      (key (apply comp-fn val freq)))))
(def get-max-val (partial get-val max-key))
(def get-min-val (partial get-val min-key))

(defn ex1 [report]
  (let [gamma-rate (->> report
                        (apply map vector)
                        (map #(get-max-val % 1)))
        epsilon-rate (mapv #(- 1 %) gamma-rate)]
    {:gamma-rate (bits->base10 gamma-rate)
     :epsilon-rate (bits->base10 epsilon-rate)}))

(defn ex2* [report get-value-to-keep idx]
  (if (= (count report) 1)
    (first report)
    (recur (let [value-to-keep (get-value-to-keep (map #(nth % idx) report))]
             (filter #(= (nth % idx) value-to-keep) report))
           get-value-to-keep
           (inc idx))))

(defn ex2 [report]
  (let [oxygen (ex2* report #(get-max-val % 1) 0)
        co2 (ex2* report #(get-min-val % 0) 0)]
    {:oxygen (bits->base10 oxygen)
     :co2 (bits->base10 co2)}))