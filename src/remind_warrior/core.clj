(ns remind-warrior.core
  (:gen-class)
  (:require [clojure.java.shell :as shell]
            [clj-time.format :as tf]
            [clojure.data.json :as json]))

(defn parse-time [time-str form] ; TODO: Overload on arity with default form
  (tf/unparse
    (tf/formatter form)
    (tf/parse (tf/formatters :basic-date-time-no-ms) time-str)))

(defn task->rem [task]
  (pprint/pprint task)
  (let [todo [(str "REM " (parse-time (:entry task) "MMMM d yyyy")
                   " *1 MSG " (:description task))]]
    (if-let [due (:due task)]
      (conj todo (str "REM " (parse-time due "MMMM d yyyy")
                      " AT " (parse-time due "HH:mm")
                      " MSG Due: " (:description task)))
      todo)))

(defn -main
  [& args]
  (let [tasks (->> (json/read-str (:out (shell/sh "task" "export"))
                                   :key-fn keyword)
                    (filter #(not= (:status %) "deleted"))
                    (sort-by :urgency)
                    reverse)
        rems (mapcat task->rem tasks)]
    (doseq [line rems]
      (println line))))
