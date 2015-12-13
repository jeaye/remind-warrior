(ns remind-warrior.core
  (:gen-class)
  (:require [clojure.java.shell :as shell]
            [clojure.pprint :as pprint]
            [clojure.data.json :as json]))

(defn task->rem [task]
  [(str "REM " (:entry task) " MSG " (:description task))])

(defn -main
  [& args]
  (let [tasks (->> (json/read-str (:out (shell/sh "task" "export"))
                                   :key-fn keyword)
                    (filter #(not= (:status %) "deleted"))
                    (sort-by :urgency)
                    reverse)
        rems (mapcat task->rem tasks)]
    (pprint/pprint (apply list rems))))
