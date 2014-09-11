(ns cljungle.sandbox
  (:require [cljungle.generator :refer :all]
            [cljungle.sequences :refer :all]
            [cljungle.phrases   :refer :all]))

(defn repeat-random [how-much]
  (into [] (take how-much (repeatedly #(random-note)))))

(def sequence-vector
  [sequence1 sequence2 sequence3])

(defn play-sequentially [sequence-vector]
  (reduce into [sequence-vector]))

(play-sequence 100
  (mix-composed-with-random 32
    (play-sequentially sequence1)
    (repeat-random 32)))

(play-sequence 100
  (mix-composed-with-random 32
    (repeat-random 2)
    (repeat-random 2)))
