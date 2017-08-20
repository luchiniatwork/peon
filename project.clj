(defproject peon "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.854" :scope "provided"]

                 [org.omcljs/om "1.0.0-beta1" :scope "test"]
                 [cljsjs/react "15.5.4-0" :scope "test"]
                 [cljsjs/react-dom "15.5.4-0" :scope "test"]]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-doo "0.1.7"]]

  :min-lein-version "2.6.1"

  :source-paths ["src/clj" "src/cljs" "src/cljc"]

  :test-paths ["test/clj" "test/cljc"]

  :main ^:skip-aot peon.core

  :target-path "target/%s"

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src/cljs" "src/cljc"]
                :compiler {:optimizations :whitespace}}

               {:id "test"
                :source-paths ["src/cljs" "src/cljc" "test"]
                :compiler {:output-to "target/cljs-tests.js"
                           :output-dir "target"
                           :main peon.test-runner
                           :optimizations :none
                           :target :nodejs}}
               
               {:id "min"
                :source-paths ["src/cljs" "src/cljc"]
                :jar true
                :compiler {:source-map-timestamp true
                           :optimizations :advanced
                           :pretty-print false}}]}

  :doo {:build "test"
        :debug true}
  
  :profiles {:uberjar {:aot :all}})
