(ns peon.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [peon.core-test]))

(enable-console-print!)

(doo-tests 'peon.core-test)
