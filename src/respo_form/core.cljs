
(ns respo-form.core
  (:require [respo.core :refer [defcomp cursor-> list-> <> div button textarea span input]]
            [respo-ui.core :as ui]
            [respo.comp.space :refer [=<]])
  (:require-macros [clojure.core.strint :refer [<<]]))

(defcomp
 comp-form
 (states items form0 on-change options)
 (let [state (or (:data states) form0)]
   (div
    {}
    (list->
     {}
     (->> items
          (map-indexed
           (fn [idx item]
             [idx
              (div
               {:style (merge ui/row {:padding 8})}
               (div
                {:style {:width 100}}
                (<> (:label item))
                (if (:required? item) (<> "*" {})))
               (case (:type item)
                 :input
                   (input
                    {:style ui/input,
                     :placeholder (:placeholder item),
                     :value (get state (:name item)),
                     :on-input (fn [e d! m!]
                       (let [new-form (assoc state (:name item) (:value e))] (m! new-form)))})
                 (<> (<< "Unknown type ~(pr-str (:type item))"))))]))))
    (div
     {:style ui/row-center}
     (button
      {:style ui/button,
       :inner-text "Cancel",
       :on-click (fn [e d! m!] ((:on-cancel options)))})
     (=< 8 nil)
     (button
      {:style ui/button, :inner-text "Submit", :on-click (fn [e d! m!] (on-change state))})))))
