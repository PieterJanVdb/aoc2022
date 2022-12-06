(ns aoc2022.day4.core
  (:require [clojure.string :as str]
            [clojure.set :as s]
            [clojure.edn :as edn]))

(def input (slurp "src/aoc2022/day4/input.txt"))

(defn ranges [pair]
  (map (fn [x]
         (let [str-bounds (str/split x #"-")
               [a b] (map parse-long str-bounds)]
           (set (range a (inc b)))))
       pair))

(defn fully-contained? [pair]
  (let [[a b] (ranges pair)]
    (or (s/subset? a b) (s/subset? b a))))

(defn partly-contained? [pair]
  (not (empty? (apply s/intersection (ranges pair)))))

(defn contain-count [contain-fn]
  (->> input
       str/split-lines
       (map #(str/split % #","))
       (map contain-fn)
       (filter true?)
       count))

(def part-1 (contain-count fully-contained?))
(def part-2 (contain-count partly-contained?))
