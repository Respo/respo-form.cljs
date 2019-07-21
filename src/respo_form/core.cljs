
(ns respo-form.core
  (:require [respo.core :refer [defcomp cursor-> list-> <> div button textarea span input]]
            [respo-ui.core :as ui]
            [respo.comp.space :refer [=<]])
  (:require-macros [clojure.core.strint :refer [<<]]))

(defn render-custom [state item modify-form!]
  (let [render (:render item), value (get state (:name item))]
    (render value item modify-form! state)))

(defn render-input [value item modify-form!]
  (input
   {:style ui/input,
    :placeholder (:placeholder item),
    :value value,
    :on-input (fn [e d! m!] (modify-form! m! {:name item, :value e}))}))

(defn render-label [item]
  (div {:style {:width 100}} (<> (:label item)) (if (:required? item) (<> "*" {}))))

(defcomp
 comp-form
 (states items form0 on-change options)
 (let [state (or (:data states) form0)
       modify-form! (fn [m! pairs] (let [new-form (merge state pairs)] (m! new-form)))]
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
               (render-label item)
               (case (:type item)
                 :input (render-input (get state (:name item)) item modify-form!)
                 :custom (render-custom state item modify-form!)
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
