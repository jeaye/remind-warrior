(defproject remind-warrior "0.1.0"
  :description "TaskWarrior -> Remind converter"
  :url "https://github.com/jeaye/remind-warrior"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/data.json "0.2.6"]
                 [clj-time "0.11.0"]]
  :main ^:skip-aot remind-warrior.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
