(ns {{namespace}}-test
  (:require #?(:clj  [clojure.test :refer :all]
               :cljs [cljs.test :refer-macros [deftest is testing]])
            [{{namespace}}]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))
