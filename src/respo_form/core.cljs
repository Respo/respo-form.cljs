
(ns respo-form.core
  (:require [respo.core :refer [defcomp cursor-> list-> <> div button textarea span input]]
            [respo-ui.core :as ui])
  (:require-macros [clojure.core.strint :refer [<<]]))

(defcomp
 comp-form
 (items form0 on-change on-cancel)
 (div
  {}
  (list->
   {}
   (->> items
        (map-indexed
         (fn [idx item]
           [idx
            (div
             {:style ui/row}
             (div {:style {:width 100}} (<> (:label item)))
             (case (:type item)
               :input
                 (input
                  {:style ui/input,
                   :placeholder (:placeholder item),
                   :value (get form0 (:name item)),
                   :on-input (fn [e]
                     (let [new-form (assoc form0 (:name item) (:value e))]
                       (on-change new-form)))})
               (<> (<< "Unknown type ~(pr-str (:type item))"))))]))))))
