(ns t.core-test
  (:require [clojure.test :refer :all]
            [t.core :as t]))

(deftest no-op
  (testing "monads"
    (is (= 12 (t/no-op-run t/no-op-example)))
    (is (= 2 (t/sc-run (t/sc-example 5))))
    (is (= :error (t/sc-run (t/sc-example -12))))
    (is (= [3 30 3 300] (t/co-run t/co-1 t/co-2)))
    (is (= [30 3 300 3] (t/co-run t/co-2 t/co-1)))))
