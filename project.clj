(defproject file-parser "0.1.0-SNAPSHOT"
  :description "file parser"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.4.1"]
                 [org.clojure/tools.logging "0.4.1"]
                 [clj-time "0.15.0"]]
  :plugins [[quickie "0.4.2"]]
  :main ^:skip-aot file-parser.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
