(ns aoc2022.day3.core
  (:require [clojure.string :as str]
            [clojure.set :as s]))

(def input (slurp "src/aoc2022/day3/input.txt"))

(defn split-sack [sack]
  (let [half (/ (count sack) 2)]
    (partition half sack)))

(defn char-to-priority [char]
  (let [p (int char)]
    (- p (if (< p 91) 38 96))))

(def part1
  (->> input
       str/split-lines
       (map split-sack)
       (map #(apply s/intersection (map set %)))
       (map #(char-to-priority (first %)))
       (apply +)))

(def part2
  (->> input
       str/split-lines
       (partition 3)
       (map #(map char-array %))
       (map #(apply s/intersection (map set %)))
       (map #(char-to-priority (first %)))
       (apply +)))
