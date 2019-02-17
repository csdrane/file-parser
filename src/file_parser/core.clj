(ns file-parser.core
  (:gen-class)
  (:require [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]]
            [file-parser.parse :as parse]
            [file-parser.routes :as routes]
            [file-parser.state :as state]
            [file-parser.view :as view]
            [ring.adapter.jetty :refer [run-jetty]]))

(def cli-options
  [["-c" "--comma-file FILE" "Parse comma-seperated file"]
   ["-s" "--space-file FILE" "Parse space-seperated file"]
   ["-p" "--pipe-file FILE" "Parse pipe-seperated file"]
   ["-P" "--port PORT" "Webserver port"
    :default 3000
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
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

(defn print-output [{:keys [comma-file pipe-file space-file]}]
  (let [comma-results (when comma-file (parse/parse-comma-file comma-file))
        pipe-results  (when pipe-file (parse/parse-pipe-file pipe-file))
        space-results  (when space-file (parse/parse-space-file space-file))
        combined-results (concat '() comma-results pipe-results space-results)]
    (view/view combined-results)
    (reset! state/records combined-results)))

(defn start-server [{:keys [port]}]
  (reset! state/server (run-jetty routes/handler {:port port
                                                  :join? false})))

(defn stop-server []
  (.stop @state/server)
  (reset! state/server nil))

(defn -main [& args]
  (let [{:keys [options exit-message ok?]} (validate-args args)]
    (if exit-message
      (exit (if ok? 0 1) exit-message)
      (do (print-output options)
          (start-server options)))))
