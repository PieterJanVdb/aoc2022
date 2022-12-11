(ns aoc2022.day8.core
  (:require [clojure.string :as str]))

(def input (slurp "src/aoc2022/day8/input.txt"))

(defn txt->grid [txt]
  (->> txt
       str/split-lines
       (map #(str/split % #""))
       (mapv #(mapv parse-long %))))

(defn directions [x y grid]
  (let [row (get grid y)
        column (mapv #(get % x) grid)
        directions [(reverse (subvec row 0 x))
                    (subvec row (inc x))
                    (reverse (subvec column 0 y))
                    (subvec column (inc y))]]
    directions))

(defn visible-dir? [tree dir]
  (every? true? (map #(> tree %) dir)))

(defn visible? [x y grid]
  (some true? (map (partial visible-dir? (get-in grid [y x]))
                   (directions x y grid))))

(defn n-visible [grid]
  (count (for [x (range (count (first grid)))
               y (range (count grid))
               :when (visible? x y grid)]
           [x y])))

(defn distance [tree dir]
  (->> dir
       (map-indexed vector)
       (some #(and (>= (second %) tree) (first %)))
       ((fn [x] (if (nil? x) (count dir) (inc x))))))

(defn scenic-score [x y grid]
  (apply * (mapv (partial distance (get-in grid [y x]))
                 (directions x y grid))))

(defn biggest-scenic-score [grid]
  (apply max (for [x (range (count (first grid)))
                   y (range (count grid))]
               (scenic-score x y grid))))

(def part1 (n-visible (txt->grid input)))
(def part2 (biggest-scenic-score (txt->grid input)))
