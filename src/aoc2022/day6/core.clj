(ns aoc2022.day6.core)

(def input (slurp "src/aoc2022/day6/input.txt"))

(defn start-of-message [marker-n]
  (first
   (filter
    #(apply
      distinct?
      (subvec (vec input) (- % marker-n) %))
    (range marker-n (count input)))))

(def part1 (start-of-message 4))
(def part2 (start-of-message 14))
