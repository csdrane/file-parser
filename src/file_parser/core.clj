(ns file-parser.core
  (:gen-class)
  (:require [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]]
            [file-parser.parse :as parse]
            [file-parser.view :as view]))

(def cli-options
  [["-c" "--comma-file FILE" "Parse comma-seperated file"]
   ["-s" "--space-file FILE" "Parse space-seperated file"]
   ["-p" "--pipe-file FILE" "Parse pipe-seperated file"]
   ["-h" "--help"]])

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn validate-args [args]
  (let [{:keys [options errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options) {:exit-message summary :ok? true}
      errors {:exit-message (error-msg errors)}
      :else {:options options})))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn start [{:keys [comma-file pipe-file space-file]}]
  (let [comma-results (when comma-file (parse/parse-comma-file comma-file))
        pipe-results  (when pipe-file (parse/parse-pipe-file pipe-file))
        space-results  (when space-file (parse/parse-space-file space-file))
        combined-results (concat '() comma-results pipe-results space-results)]
    (view/view combined-results)))

(defn -main [& args]
  (let [{:keys [options exit-message ok?]} (validate-args args)]
    (if exit-message
      (exit (if ok? 0 1) exit-message)
      (start options))))
