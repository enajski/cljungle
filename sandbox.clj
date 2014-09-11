(ns cljungle.sandbox
  (:require [cljungle.generator :refer :all]
            [cljungle.sequences :refer :all]))

(defn play-randomly [how-much]
  (into [] (take how-much (repeatedly #(random-note)))))

(defn play-sequentially []
  (reduce into [sequence1 sequence2 sequence3]))

(play-sequence 100
  (mix-composed-with-random (* 2 2 128)
    (play-sequentially)
    (play-randomly 1)))
