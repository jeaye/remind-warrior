(ns remind-warrior.core
  (:gen-class)
  (:require [clojure.java.shell :as shell]
            [clojure.pprint :as pprint]
            [clojure.data.json :as json]))

(defn -main
  [& args]
  (let [parsed (filter #(not= (:status %) "deleted")
                       (json/read-str (:out (shell/sh "task" "export"))
                                      :key-fn keyword))]
    (pprint/pprint (apply list parsed))))
