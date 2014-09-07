(ns cljungle.sequences
  (:require [cljungle.phrases :refer :all]))

(def sequence1 (reduce into [pucitaci cita cita cita cita tatatata]))
(def sequence2 (reduce into [taa cita taa cita tatatata cita cita]))
(def sequence3 (reduce into [taa cita taa cita cita cita taa cita pucitaci taa pucitaci taa taa cita cita cita]))
