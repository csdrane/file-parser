(defproject file-parser "0.1.0-SNAPSHOT"
  :description "file parser"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [;; core dependencies
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.4.1"]
                 [org.clojure/tools.logging "0.4.1"]
                 [cheshire "5.8.1"]
                 [clj-time "0.15.0"]
                 [compojure "1.6.1"]
                 [ring "1.7.1"]

                 ;; test dependencies
                 [clj-http "3.9.1"]]
  :plugins [[quickie "0.4.2"]]
  :main ^:skip-aot file-parser.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
