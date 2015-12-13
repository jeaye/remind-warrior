(ns remind-warrior.core
  (:gen-class)
  (:require [clojure.java.shell :as shell]
            [clojure.pprint :as pprint]
            [clj-time.format :as tf]
            [clojure.data.json :as json]))

(defn parse-time [time-str]
  (tf/unparse
    (tf/formatter "MMMM d yyyy")
    (tf/parse (tf/formatters :basic-date-time-no-ms) time-str)))

(defn task->rem [task]
  [(str "REM " (parse-time (:entry task)) " MSG " (:description task))])

(defn -main
  [& args]
  (let [tasks (->> (json/read-str (:out (shell/sh "task" "export"))
                                   :key-fn keyword)
                    (filter #(not= (:status %) "deleted"))
                    (sort-by :urgency)
                    reverse)
        rems (mapcat task->rem tasks)]
    (pprint/pprint (apply list rems))))
