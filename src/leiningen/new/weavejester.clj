(ns leiningen.new.weavejester
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "weavejester"))

(defn template-data [name]
  {:name name
   :sanitized (name-to-path name)})

(defn clojure [name]
  (let [data (template-data name)]
    (main/info "Generating new Clojure library project:" name)
    (->files data
             ["src/{{sanitized}}/foo.clj" (render "foo.clj" data)])))

(defn clojurescript [name]
  (let [data (template-data name)]
    (main/info "Generating new ClojureScript library project:" name)
    (->files data
             ["src/{{sanitized}}/foo.clj" (render "foo.clj" data)])))

(defn weavejester
  "Generate a new Clojure or ClojureScript project."
  ([name]
   (weavejester name "clojure"))
  ([name type]
   (case type
     "clojure"       (clojure name)
     "clojurescript" (clojurescript name)
     (main/abort "No such project type:" type))))
