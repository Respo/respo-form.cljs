
(ns respo-form.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core
             :refer
             [defcomp cursor-> action-> <> div button textarea span input]]
            [respo.comp.space :refer [=<]]
            [reel.comp.reel :refer [comp-reel]]
            [respo-md.comp.md :refer [comp-md]]
            [respo-form.config :refer [dev?]]
            [respo-form.core :refer [comp-form]]))

(defcomp
 comp-container
 (reel)
 (let [store (:store reel)
       states (:states store)
       items [{:type :input, :label "Name", :name :name, :placeholder "a name"}]]
   (div
    {:style (merge ui/global ui/row)}
    (comp-form items {} (fn [form] (println "form" form)) (fn [] (println "cancel")))
    (when dev? (cursor-> :reel comp-reel states reel {})))))
