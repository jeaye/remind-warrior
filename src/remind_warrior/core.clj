(ns remind-warrior.core
  (:gen-class)
  (:require [clojure.data.json :as json]))

(defn -main
  [& args]
  (let [parsed (json/read-str (first args))]
    (println (json/write-str parsed))))
