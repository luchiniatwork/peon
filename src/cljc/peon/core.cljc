(ns peon.core
  (:require [goog.object :as gobj]))

(defprotocol IDispatcher
  (dispatcher [this]))

(defn- get-prop
  "PRIVATE: Do not use"
  [c k]
  #?(:clj  (get (:props c) k)
     :cljs (gobj/get (.-props c) k)))

(defn- get-parent [c]
  (get-prop c "omcljs$parent"))

(declare dispatch*)

(defn- bubble-up
  [c k args]
  (println "bubblig up")
  (if-let [parent (get-parent c)]
    (apply (partial dispatch* parent k) args)))

(defn- dispatch*
  [c k args]
  (if-let [f (some->> c dispatcher k)]
    (apply f args)
    (bubble-up c k args)))

(defn dispatch
  [c k & args]
  (println "dispatch called")
  (println (satisfies? IDispatcher c))
  (if (satisfies? IDispatcher c)
    (dispatch* c k args)
    (bubble-up c k args)))
