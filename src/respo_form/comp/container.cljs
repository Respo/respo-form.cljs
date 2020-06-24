
(ns respo-form.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core :refer [defcomp >> <> div button textarea span input]]
            [respo.comp.space :refer [=<]]
            [reel.comp.reel :refer [comp-reel]]
            [respo-md.comp.md :refer [comp-md]]
            [respo-form.config :refer [dev?]]
            [respo-form.core :refer [comp-form]]
            [respo.comp.inspect :refer [comp-inspect]]))

(def items
  [{:type :input, :label "Name", :name :name, :required? true, :placeholder "a name"}
   {:type :input, :label "Place", :name :place, :placeholder "a name"}
   {:type :select-popup,
    :name :kind,
    :label "Kind",
    :placeholder "Nothing selected",
    :options [{:value :a, :title "A"} {:value :b, :title "B"}]}
   {:type :custom,
    :name :custom,
    :label "Counter",
    :render (fn [value item modify-form! state]
      (div
       {:style {:cursor :pointer, :padding "0px 8px", :background-color (hsl 0 0 90)},
        :on-click (fn [e d!] (modify-form! d! {(:name item) (inc value)}))}
       (<> (or value 0))))}])

(defcomp
 comp-container
 (reel)
 (let [store (:store reel), states (:states store)]
   (div
    {:style (merge ui/global ui/row)}
    (comp-form
     (>> states :form-example)
     items
     {}
     (fn [form] (println "form" form))
     {:on-cancel (fn [] (println "cancel"))})
    (when dev? (comp-reel (>> states :reel) reel {}))
    (when dev? (comp-inspect "Store" store {:bottom 8})))))
