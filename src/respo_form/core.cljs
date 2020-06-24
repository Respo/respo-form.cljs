
(ns respo-form.core
  (:require [respo.core :refer [defcomp >> list-> <> div button textarea span input]]
            [respo-ui.core :as ui]
            [respo.comp.space :refer [=<]]
            [respo-alerts.core :refer [comp-select]])
  (:require-macros [clojure.core.strint :refer [<<]]))

(defn render-custom [state item modify-form!]
  (let [render (:render item), value (get state (:name item))]
    (render value item modify-form! state)))

(defn render-input [value item modify-form!]
  (input
   {:style ui/input,
    :placeholder (:placeholder item),
    :value value,
    :on-input (fn [e d!] (modify-form! d! {(:name item) (:value e)}))}))

(defn render-label [item]
  (div {:style {:width 100}} (<> (:label item)) (if (:required? item) (<> "*" {}))))

(defn render-select-popup [states cursor value item modify-form!]
  (let [options (->> (:options item)
                     (map (fn [option] {:value (:value option), :display (:title option)})))]
    (comp-select
     (>> states (:name item))
     value
     options
     {}
     (fn [result d!] (modify-form! d! {(:name item) result})))))

(defcomp
 comp-form
 (states items form0 on-change options)
 (let [cursor (:cursor states)
       state (or (:data states) form0)
       modify-form! (fn [d! pairs]
                      (let [new-form (merge state pairs)] (d! cursor new-form)))]
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
                 :select-popup
                   (render-select-popup
                    states
                    cursor
                    (get state (:name item))
                    item
                    modify-form!)
                 :custom (render-custom state item modify-form!)
                 (<> (<< "Unknown type ~(pr-str (:type item))"))))]))))
    (div
     {:style ui/row-center}
     (button
      {:style ui/button, :inner-text "Cancel", :on-click (fn [e d!] ((:on-cancel options)))})
     (=< 8 nil)
     (button
      {:style ui/button, :inner-text "Submit", :on-click (fn [e d!] (on-change state))})))))
