(ns cljungle.syllables
  (:require [overtone.live :refer :all]))

(def pu
  "kick"
    (sample (freesound-path 26885)))

(def ci
  "hi-hat"
    (sample (freesound-path 26879)))

(def ta
  "snare"
    (sample (freesound-path 26900)))

(def ty
  "semi-snare"
    (sample (freesound-path 26896)))
