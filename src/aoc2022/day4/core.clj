(ns aoc2022.day4.core
  (:require [clojure.string :as str]
            [clojure.set :as s]
            [clojure.edn :as edn]))

(def input (slurp "src/aoc2022/day4/input.txt"))

(defn bounds [pair]
  (map edn/read-string
       (mapcat #(str/split % #"-") pair)))

(defn fully-contained? [pair]
  (let [[a b c d] (bounds pair)]
     (or (and (>= a c) (<= b d))
         (and (>= c a) (<= d b)))))

(defn partly-contained? [pair]
  (let [[a b c d] (bounds pair)]
     (->> [(range a (+ b 1)) (range c (+ d 1))]
          (map set)
          #_(apply s/intersection)
          #_(count))))

(defn contain-count [contain-fn]
  (->> input
       str/split-lines
       (map #(str/split % #","))
       (map contain-fn)
       (filter true?)
       count))

(def part-1 (contain-count fully-contained?))
(def part-2 (contain-count partly-contained?))
