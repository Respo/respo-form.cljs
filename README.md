
Respo Form
----

> Form component designed for Respo.

Since I maintained [meson-form](https://github.com/jimengio/meson-form) for a long while, this library will probably inherit features from meson-form.

### Usage

[![Clojars Project](https://img.shields.io/clojars/v/respo/form.svg)](https://clojars.org/respo/form)

```edn
[respo/form "0.1.0-a1"]
```

Example http://repo.respo-mvc.org/form/

Define elements:

```clojure
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
        :on-click (fn [e d! m!] (modify-form! m! {(:name item) (inc value)}))}
       (<> (or value 0))))}])
```

Render component:

```clojure
(cursor-> :form-example comp-form states items {}
 (fn [form] (println "form" form))
 {:on-cancel (fn [] (println "cancel"))})
```

### Workflow

Workflow https://github.com/mvc-works/calcit-workflow

### License

MIT
