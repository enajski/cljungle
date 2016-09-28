(ns cljungle.spec
  (:require [clojure.spec :as s]
            [clojure.spec.gen :as gen]
            [leipzig.live :as live]
            [leipzig.melody :refer :all]
            [leipzig.scale :as scale]
            [overtone.live :as overtone :refer [freesound-path sample sample-player now at apply-at]]
            [leipzig.canon :as canon]
            ))

(s/def ::jungle (s/* ::section))

(s/def ::section (s/keys :req [::break]
                         :opt [::bass ::synth ::samples]))

(s/def ::break (s/* ::drum-hit))
(s/def ::bass (s/* ::drum-hit))
(s/def ::synth (s/* ::drum-hit))
(s/def ::samples (s/* ::drum-hit))

(s/def ::drum-sample #{:pu :ci :ta :ty})
(s/def ::duration #{1/8 1/4 2/4 4/4})
(s/def ::drum-hit (s/cat :sample ::drum-sample
                         :duration ::duration))

(gen/sample (s/gen ::break 2))

(into [] (map :sample (s/conform ::break (gen/generate (s/gen ::break)))))

(gen/generate (s/gen ::duration))
(s/conform ::drum-hit (gen/generate (s/gen ::drum-hit)))
(s/conform ::break (gen/generate (s/gen ::break)))
(gen/generate (s/gen ::jungle))

(defmulti amen (fn [sample] (sample :sample)))

(defmethod amen :pu [params]
  (sample (freesound-path 26885)))
(defmethod amen :ci [params]
  (sample (freesound-path 26879)))
(defmethod amen :ta [params]
  (sample (freesound-path 26900)))
(defmethod amen :ty [params]
  (sample (freesound-path 26896)))

(defn play-sequence [interval [first-sound & others]]
  (at 0 (sample-player first-sound))
  (apply-at (+ interval (now)) play-sequence [interval others]))

(defn play-break [break]
  (when-not (empty? break)
    (println break)
    (->> break
         (map amen)
         (play-sequence 200))))

(defn play-section [section]
  (play-break (::break section)))

(play-break (s/conform ::break (gen/generate (s/gen ::break))))
(play-section (s/conform ::section (gen/generate (s/gen ::section))))
(play-jungle (s/conform ::jungle (gen/generate (s/gen ::jungle))))

(defn play-jungle [jungle]
  (for [section jungle]
    (play-section section)))

(defn play-break [break]
  (when-not (empty? break)
    (println break)
    (->> break
         (map amen)
         (play-sequence 200))))

(defn play-section [section]
  (play-break (::break section)))

(defn play-jungle [jungle]
  (for [section jungle]
    (play-section section)))

(def kit {:pu {:sound (amen {:sample :pu})}
          :ci {:sound (amen {:sample :ci})}
          :ta {:sound (amen {:sample :ta})}
          :ty {:sound (amen {:sample :ty})}})

(defmethod live/play-note :beat [note]
  (when-let [fn (-> (get kit (:drum note)) :sound)]
    (fn :amp (:amp note))))

(defn beat-tap [drum times length & {:keys [amp] :or {amp 1}}]
  (map #(zipmap [:time :duration :drum :amp]
                [%1 (- length %1) drum amp]) times))



(def da-beats
  (->>
   (reduce with
           [
            ;; (beat-tap :pu (range 8) 8)
            ;; (beat-tap :ty (range 8) 8)
            (beat-tap :ta (range 1 8 2) 8)
            ;; (beat-tap :ci (sort (concat [3.75 7.75] (range 1/2 8 1))) 8)])
            ])
   (all :part :beat)))

(println da-beats)

(def beatz (->> da-beats
                (tempo (bpm 147))))

(live/jam (var beatz))
(live/stop)

(->> (phrase [3/3 3/3 2/3 1/3 3/3] [0 0 0 1 2])
     (where :time inc))

(overtone/definst beep [freq 440 dur 1.0]
  (-> freq
      overtone/pulse
      (* (overtone/env-gen (overtone/perc 0.01 dur) :action overtone/FREE))))

(defmethod live/play-note :default [{midi :pitch seconds :duration}]
  (-> midi overtone/midi->hz (beep seconds)))

(def melody
  (phrase [3/3   3/3   2/3   1/3   3/3]
          [  0     0     0     1     2]))

(->>
 melody
 (tempo (bpm 180))
 (where :pitch (comp scale/C scale/major))
 live/play)

(def arpa1 (->> (times 2 melody1)
                (where :pitch (comp scale/C scale/minor))))

(def arpa2 (->> (times 1 melody1)
                (where :pitch (comp scale/D scale/minor))))

(def arpa (->> arpa1
               (then arpa2)))

(def melody1
  (->> (phrase (repeat 1/4) (range 2 12))
       (then (phrase (repeat 1/2) (range 0 2)))
       (canon/canon canon/table)
       (canon/canon canon/mirror)
       (tempo (bpm 120))))

(live/jam (var arpa))
(live/stop)

(->> (phrase [1 2 2 1] [1 1/4 1/4 1])
     (where :pitch (comp scale/C scale/major))
     live/play)
