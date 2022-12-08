(ns aoc2022.day7.core
  (:require [clojure.string :as str]
            [clojure.zip :as zip]))

(def input (slurp "src/aoc2022/day7/input.txt"))

(defn zipper [root]
  (zip/zipper
   (fn [x] (vector? (second x)))
   (fn [x] (seq (second x)))
   (fn [x c] [(first x) (into [] c)])
   root))

(defn cd? [line]
  (and (= (first line) "$") (= (second line) "cd")))

(defn move-up? [line]
  (and (cd? line) (= (nth line 2) "..")))

(defn file? [line]
  (int? (parse-long (first line))))

(defn line->loc [loc line]
  (let [[a b c] line]
    (cond
      (move-up? line) (zip/up loc)
      (cd? line) (zip/down (zip/insert-child loc [c []]))
      (file? line) (zip/insert-child loc [b (parse-long a)])
      :else loc)))

(defn cli->tree [cli]
  (loop [loc (zipper [:root []])
         [line & lines] cli]
    (if-not line
      (get-in (zip/root loc) [1 0])
      (let [segments (str/split line #" ")
            next-loc (line->loc loc segments)]
        (recur next-loc lines)))))

(defn node->size [[a b]]
  (if (vector? b) (apply + (map node->size b)) b))

(defn tree->sizes [tree]
  (->> tree
       zipper
       (iterate zip/next)
       (take-while (complement zip/end?))
       (map zip/node)
       (filter #(vector? (second %)))
       (mapv node->size)))

(def sizes
  (->> input
       str/split-lines
       cli->tree
       tree->sizes))

(def part1
  (->> sizes
       (filter #(<= % 100000))
       (apply +)))

(def part2
  (let [unused (- 70000000 (first sizes))
        min-target (- 30000000 unused)]
    (first (sort (filter #(>= % min-target) sizes)))))
