(ns remind-warrior.core
  (:gen-class)
  (:require [clojure.java.shell :as shell]
            [clj-time.format :as tf]
            [clojure.pprint :as pp]
            [clojure.data.json :as json]))

(defn parse-time
  ([time-str] (parse-time time-str "MMMM d yyyy"))
  ([time-str form]
   (tf/unparse
     (tf/formatter form)
     (tf/parse (tf/formatters :basic-date-time-no-ms) time-str))))

(defn task->rem [task]
  ;(pp/pprint task)
  (let [todo [(str "REM " (parse-time (:entry task))
                   " *1 MSG " (:description task))]]
    (if-let [due (:due task)]
      (conj todo (str "REM " (parse-time due)
                      " AT " (parse-time due "HH:mm")
                      " MSG Due: " (:description task)))
      todo)))

(defn -main
  [& args]
  (let [tasks (->> (json/read-str (:out (shell/sh "task" "export"))
                                   :key-fn keyword)
                    (filter #(= (:status %) "pending"))
                    (sort-by :urgency)
                    reverse)
        rems (mapcat task->rem tasks)]
    (doseq [line rems]
      (println line))))
