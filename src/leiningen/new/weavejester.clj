(ns leiningen.new.weavejester
  (:require [leiningen.new.templates :as tmpl]
            [leiningen.core.main :as main]))

(def render (tmpl/renderer "weavejester"))

(defn template-data [name]
  (let [top-ns  (tmpl/sanitize-ns name)
        main-ns (tmpl/multi-segment top-ns)]
    {:raw-name      name
     :name          (tmpl/project-name name)
     :top-namespace top-ns
     :namespace     main-ns
     :top-dir       (tmpl/name-to-path top-ns)
     :nested-dirs   (tmpl/name-to-path main-ns)
     :year          (tmpl/year)
     :date          (tmpl/date)}))

(defn clj-project [name]
  (let [data (template-data name)]
    (main/info "Generating new Clojure library project:" name)
    (tmpl/->files
     data
     ["project.clj" (render "project-clj.clj" data)]
     ["README.md"   (render "README.md" data)]
     [".gitignore"  (render "gitignore" data)]
     ["src/{{nested-dirs}}.clj"       (render "core.clj" data)]
     ["test/{{nested-dirs}}_test.clj" (render "test.clj" data)]
     "resources")))

(defn cljs-project [name]
  (let [data (template-data name)]
    (main/info "Generating new ClojureScript library project:" name)
    (tmpl/->files
     data
     ["project.clj" (render "project-cljs.clj" data)]
     ["README.md"   (render "README.md" data)]
     [".gitignore"  (render "gitignore" data)]
     ["src/{{nested-dirs}}.cljs"          (render "core.cljs" data)]
     ["test/{{nested-dirs}}_test.cljs"    (render "test.cljs" data)]
     ["test/{{top-dir}}/test_runner.cljs" (render "test_runner.cljs" data)]
     "resources")))

(defn weavejester
  "Generate a new Clojure or ClojureScript project."
  ([name]
   (weavejester name "clj"))
  ([name type]
   (case type
     "clj"  (clj-project name)
     "cljs" (cljs-project name)
     (main/abort "No such project type:" type))))
  
