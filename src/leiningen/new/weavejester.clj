(ns leiningen.new.weavejester
  (:require [leiningen.new.templates :as tmpl]
            [leiningen.core.main :as main]))

(def render (tmpl/renderer "weavejester"))

(defn template-data [name]
  (let [main-ns (tmpl/multi-segment (tmpl/sanitize-ns name))]
    {:raw-name    name
     :name        (tmpl/project-name name)
     :namespace   main-ns
     :nested-dirs (tmpl/name-to-path main-ns)
     :year        (tmpl/year)
     :date        (tmpl/date)}))

(defn clojure [name]
  (let [data (template-data name)]
    (main/info "Generating new Clojure library project:" name)
    (tmpl/->files data
                  ["project.clj" (render "project-clj.clj" data)]
                  ["README.md"   (render "README.md" data)]
                  [".gitignore"  (render "gitignore" data)]
                  ["src/{{nested-dirs}}.clj"       (render "core.clj" data)]
                  ["test/{{nested-dirs}}_test.clj" (render "test.clj" data)]
                  "resources")))

(defn clojurescript [name]
  (let [data (template-data name)]
    (main/info "Generating new ClojureScript library project:" name)
    (tmpl/->files data
                  ["project.clj" (render "project-cljs.clj" data)]
                  ["README.md"   (render "README.md" data)]
                  [".gitignore"  (render "gitignore" data)]
                  ["src/{{nested-dirs}}.cljs"       (render "core.cljs" data)]
                  ["test/{{nested-dirs}}_test.cljs" (render "test.cljs" data)]
                  "resources")))

(defn weavejester
  "Generate a new Clojure or ClojureScript project."
  ([name]
   (weavejester name "clojure"))
  ([name type]
   (case type
     "clojure"       (clojure name)
     "clojurescript" (clojurescript name)
     (main/abort "No such project type:" type))))
  
