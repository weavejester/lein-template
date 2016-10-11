(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.228"]]
  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-doo "0.1.6"]]
  :doo {:paths {:rhino "lein run -m org.mozilla.javascript.tools.shell.Main"}}
  :aliases {"test-cljs" ["with-profile" "test" "doo" "rhino" "test" "once"]
            "test-all"  ["do" ["test"] ["test-cljs"]]}
  :profiles
  {:test {:dependencies [[org.mozilla/rhino "1.7.7"]]
          :cljsbuild
          {:builds
           {:test
            {:source-paths ["src" "test"]
             :compiler {:output-to "target/main.js"
                        :output-dir "target"
                        :main {{top-namespace}}.test-runner
                        :optimizations :simple}}}}}})
