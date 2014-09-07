(ns cljungle.sandbox
  (:require [cljungle.generator :refer :all]
            [cljungle.sequences :refer :all]))

(play-sequence 100 (into [] (take 64 (repeatedly #(random-note)))))

(play-sequence 80 (reduce into [sequence1 sequence2 sequence3]))
