(ns file-parser.routes
  (:require [cheshire.core :as cheshire]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [file-parser.state :as state]
            [file-parser.sort :as sort]
            [file-parser.parse :as parse]
            [clojure.tools.logging :as log])
  (:import org.joda.time.DateTime))

(defn *200 [resp]
  {:status 200
   :body resp
   :headers {"Content-Type" "application/json"}})

(defn *201 []
  {:status 200
   :body "Your response has been processed successfully."
   :headers {"Content-Type" "application/json"}})

(defn *400 []
  {:status 400
   :body "Unable to process your request."
   :headers {"Content-Type" "application/json"}})

(defn records-by-gender []
  (*200 (-> @state/records
            sort/output-1
            cheshire/generate-string)))

(defn records-by-birthdate []
  (*200 (-> @state/records
            sort/output-2
            cheshire/generate-string)))

(defn records-by-name []
  (*200 (-> @state/records
            sort/output-3
            cheshire/generate-string)))

(defn parse [line]
  (loop [[parser & parsers] [#(parse/parse-line parse/pipe-delimiter parse/expected-fields % {:logging? false})
                             #(parse/parse-line parse/space-delimiter parse/expected-fields % {:logging? false})
                             #(parse/parse-line parse/comma-delimiter parse/expected-fields % {:logging? false})]]
    (let [parsed (try (parser line) (catch Exception _ nil))]
      (cond
        (nil? parser) nil
        parsed parsed
        :else (recur parsers)))))

(defn add-record [req]
  (let [record (some-> req :body slurp parse)]
    (if record
      (do
        (swap! state/records #(conj % record))
        (*201))
      (do
        (log/warnf "invalid request: %s" req)
        (*400)))))

(defroutes handler
  (POST "/records" req (add-record req))
  (GET "/records/gender" [] (records-by-gender))
  (GET "/records/birthdate" [] (records-by-birthdate))
  (GET "/records/name" [] (records-by-name))
  (route/not-found "Unknown route"))

(extend-protocol cheshire.generate/JSONable
  org.joda.time.DateTime
  (to-json [dt gen]
    (cheshire.generate/write-string gen (str dt))))
