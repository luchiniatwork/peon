(ns peon.core-test
  (:require [cljs.test]
            [peon.core :as peon]
            [om.next :as om :refer [defui]]
            [cljsjs.react])
  (:require-macros [cljs.test :refer [is deftest testing]]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))

(deftest example-passing-test-cljc
  (is (= 1 1)))

(defui Parent
  peon/IDispatcher
  (dispatcher [this] {:f #(println "Dispatched :f with" %)}))

(def parent (om/factory Parent))

(deftest simple-test
  
  (deftype T []
    peon/IDispatcher
    (dispatcher [this] {:f #(println "Dispatched :f with" %)}))
  (deftype C []
    )
  
  (let [t (T.)
        c (C.)
        p (parent)
        p2 (specify! p
             peon/IDispatcher
             (dispatcher [this] {:f #(println "qweqwe Dispatched :f with" %)}))]
    (peon/dispatch p2 :f 33)))
