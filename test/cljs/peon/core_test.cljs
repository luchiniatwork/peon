(ns peon.core-test
  (:require [cljs.test]
            [peon.core :as peon]
            [om.next :as om :refer [defui]]
            [cljsjs.react])
  (:require-macros [cljs.test :refer [is deftest testing]]))

#_(deftest a-test
    (testing "FIXME, I fail."
      (is (= 0 1))))

#_(deftest example-passing-test-cljc
    (is (= 1 1)))

(defui Child
  Object
  (render [this]
          (println "Chhild renderer")))

(def child (om/factory Child))

(defui Parent
  peon/IDispatcher
  (dispatcher [this] {:f #(println "Dispatched :f with" %)})
  Object
  (render [this]
          (child)))



(deftest simple-test
  
  (let [p (Parent. {})
        c (Child. {})]
    (.render p)
    (.render c)
    (peon/dispatch p :f "asdasd")
    (peon/dispatch c :f "asdasd")
    ))
