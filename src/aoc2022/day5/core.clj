(ns aoc2022.day5.core
  (:require [clojure.string :as str]))

(def stacks-txt (slurp "src/aoc2022/day5/stacks.txt"))
(def moves-txt (slurp "src/aoc2022/day5/moves.txt"))

(defn txt->stacks [txt]
  (->> txt
       str/split-lines
       (map #(str/split % #""))
       (map #(drop 1 %))
       (map #(take-nth 4 %))
       (apply mapv vector)
       (map drop-last)
       (mapv (partial filter #(not (str/blank? %))))))

(defn txt->moves [txt]
  (->> txt
       str/split-lines
       (map #(re-seq #"\d+" %))
       (mapv #(map parse-long %))))

(defn move [lift state [n from to]]
  (let [crates (take n (get state (dec from)))]
    (vec (map-indexed
          (fn [idx stack]
            (cond
              (= idx (dec from)) (drop n stack)
              (= idx (dec to)) (concat (lift crates) stack)
              :else stack))
          state))))

(defn top-crates [state]
  (apply str (map first state)))

(def stacks (txt->stacks stacks-txt))
(def moves (txt->moves moves-txt))

(def part1
  (->> moves
       (reduce (partial move reverse) stacks)
       top-crates))

(def part2
  (->> moves
       (reduce (partial move identity) stacks)
       top-crates))
