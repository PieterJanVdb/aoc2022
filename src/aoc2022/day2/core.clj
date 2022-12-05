(ns aoc2022.day2.core
  (:require [clojure.string :as str]))

(def input (slurp "src/aoc2022/day2/input.txt"))

(def shape-scores {"A" 1, "B" 2, "C" 3, "X" 1, "Y" 2, "Z" 3})
(def shape-order ["C" "A" "B" "C" "A"])

(defn duel-1 [round]
  (let [x (get shape-scores (first round))
        y (get shape-scores (second round))
        d (- y x)]
    (cond
      (or (= d -2) (= d 1)) (+ y 6)
      (= d 0) (+ y 3)
      :else y)))

(defn duel-2 [round]
  (let [shape (first round)
        x (get shape-scores shape)
        y (second round)]
    (cond
      (= y "X") (get shape-scores (get shape-order (dec x)))
      (= y "Y") (+ (get shape-scores shape) 3)
      :else (+ (get shape-scores (get shape-order (inc x))) 6))))

(defn calculate-total [duel-fn]
  (->> input
       str/split-lines
       (map #(duel-fn (str/split % #" ")))
       (apply +)))

(def part-1 (calculate-total duel-1))
(def part-2 (calculate-total duel-2))
