(ns aoc2022.day6.core)

(def input (slurp "src/aoc2022/day6/input.txt"))

(defn start-of-message [marker-length]
  (+ (first
      (filter
       #(apply
         distinct?
         (subvec (vec input) % (+ marker-length %)))
       (range (count input)))) marker-length))

(def part1 (start-of-message 4))
(def part2 (start-of-message 14))
