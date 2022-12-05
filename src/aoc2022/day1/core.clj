(ns aoc2022.day1.core
  (:require [clojure.string :as str]
            [clojure.edn :as edn]))

(def input (slurp "src/aoc2022/day1/input.txt"))

(def cals-per-elf
  (map #(->> %
             str/split-lines
             (map edn/read-string)
             (apply +))
       (str/split input #"\n\n")))

(def part1 (apply max cals-per-elf))

(def part2
  (->> cals-per-elf
       (sort >)
       (take 3)
       (apply +)))
