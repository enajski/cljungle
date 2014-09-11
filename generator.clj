(ns cljungle.generator
  (:require [overtone.live :refer :all]
            [cljungle.syllables :refer :all]))

(defn return-random [list]
    (nth list (rand-int (count list))))

(defn play-sequence [interval [first-sound & others]]
    (at 0 (first-sound))
    (apply-at (+ interval (now)) play-sequence [interval others]))

(def notes [pu ci ta ty])

(defn random-note [] (return-random notes))

(defn mix-composed-with-random [random-notes composed random]
  (into []
        (conj
          (into [] (take (* 2 random-notes) (shuffle composed)))
          (into [] (take random-notes random)))))
