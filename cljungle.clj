(use '[overtone.live])

(def AMENZ
   {:Snare4             26903
    :Snare3             26902
    :Snare2             26901
    :Snare1             26900
    :SemiSnare4         26899
    :SemiSnare3         26898
    :SemiSnare2         26897
    :SemiSnare1         26896
    :SemiKick22         26895
    :SemiKick21         26894
    :ReverseSnare3      26893
    :ReverseRoll4       26892
    :ReverseKick4       26891
    :ReverseClHiHat2    26890
    :Openhihat          26889
    :Kick4              26888
    :Kick3              26887
    :Kick2              26886
    :Kick1              26885
    :Crash              26884
    :Crashsnare         26883
    :ClosedHiHat4       26882
    :ClosedHiHat3       26881
    :ClosedHiHat2       26880
    :ClosedHiHat1       26879
    :Bassstab           26878})

(def amen-samples
    (doall (map (fn [id] (sample (freesound-path id))) (vals AMENZ))))

(defn return-random [list]
    (nth list (rand-int (count list))))

(defn random-amen []
    (return-random amen-samples))

(def amenkick
    (sample (freesound-path 26885)))

(def closedhihat
    (sample (freesound-path 26879)))

(def snare1
    (sample (freesound-path 26900)))

(defn pucita [time interval]
    (at (+ 0 time) ((random-amen)))
    (at (+ interval time) (closedhihat))
    (at (+ (* 2 interval) time) ((random-amen)))
    (apply-at (+ (* 3 interval) time) pucita (+ (* 3 interval) time) [interval]))

; (pucita (now) 100)

; (stop)

