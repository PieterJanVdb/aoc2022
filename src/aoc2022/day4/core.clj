(ns aoc2022.day4.core
  (:require [clojure.string :as str]
            [clojure.set :as s]
            [clojure.edn :as edn]))

(def input (slurp "src/aoc2022/day4/input.txt"))

(defn range-pairs [pair]
  (map #(let [[a b] (map parse-long (str/split % #"-"))]
          (set (range a (inc b))))
       (str/split pair #",")))

(defn fully-contained? [[a b]]
  (or (s/subset? a b) (s/subset? b a)))

(defn partly-contained? [[a b]]
  (not (empty? (s/intersection a b))))

(defn contain-count [contain-fn]
  (->> input
       str/split-lines
       (map range-pairs)
       (filter contain-fn)
       count))

(def part-1 (contain-count fully-contained?))
(def part-2 (contain-count partly-contained?))
