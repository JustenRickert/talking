(ns talking.model
  (:require [clojure.string :as str]
            [postagga.en-fn-v-model :refer [en-model]]
            [postagga.tagger :refer [viterbi]]))

(defn- word-pos-pair [ws]
  (apply map vector [ws (viterbi en-model ws)]))

(defn tag-sentence-string [s]
  (->> s
       (#(str/split % #" "))
       word-pos-pair
       (mapv #(str/join "_" %))
       (str/join " ")))
