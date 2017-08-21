# Peon

Peon is a small library to support idiomatic dispatch of Om Next transactions from
stateless components.

Traditionally, statelful components send handlers down to stateless compoenents via 
mechanisms such as `om/computed`. This approach offers great isolation as well as the
ability to reuse and test components more granularly.

Peon takes that approach further by creating a simple idiomatic protocol for dispatching
actions from stateless components up the component tree to stateful components.

## Table of Contents

* [Getting Started](#getting-started)
* [Usage](#usage)
* [How does it work?](#how-does-it-work)
* [Bugs](#bugs)
* [Help!](#help)

## Getting Started

Add the following dependency to your `project.clj` file:

[![Clojars Project](http://clojars.org/luchiniatwork/peon/latest-version.svg)](http://clojars.org/luchiniatwork/peon)

## Usage

Require `peon.core` on your stateful component:

```clojure
(ns my-project.components.stateful
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [my-project.components.stateless :as stateless]
            [peon.core :as peon]))
```

Use the protocol `peon/IDispatcher` to specify which handlers this stateful component is
responsible for.

The `dispatcher` interface must return a map where each keyword points to a handling
function.

```clojure
(defui RootComponent
  static om/IQuery
  (query [this] `[:app/details ~(om/get-query stateless/HeaderComponent)])
  peon/IDispatcher
  (dispatcher [this] {:change-header #(om/transact! this '[(app/change-details! {:app/header "New header})])
                      :no-params #(println "handler with no params")
                      :two-params #(println "param 1:" %1 "and param 2:" %2)})
  Object
  (render [this]
          (let [{:keys [app/details]} (om/props this)]
            (dom/div nil
                     (stateless/header details)))))

(def root (om/factory RootComponent))
```

The component above handles three actions: `:change-header`, `:no-params` and `:two-params`. The latter two
simply print something out while the first one transacts an hypothetical mutation.

Then, on your stateless component, make sure to require `peon.core`:

```clojure
(ns my-project.components.stateless
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [peon.core :as peon]))
```

You can then dispatch actions using `peon/dispatch`. The signature takes `this` followed by the keyword of
the handler and optionally any arguments you want to pass to the handler.

```clojure
(defui HeaderComponent
  static om/IQuery
  (query [this] '[:app/header])
  Object
  (render [this]
          (let [{:keys [app/header]} (om/props this)]
            (dom/div nil
                     (dom/h1 nil header)
                     (dom/button #js {:onClick #(peon/dispatch this :change-header)} "Change header")
                     (dom/button #js {:onClick #(peon/dispatch this :no-params)} "No params")
                     (dom/button #js {:onClick #(peon/dispatch this :two-params "Don't panic!" 42)} "Two params")
                     (dom/button #js {:onClick #(peon/dispatch this :not-found)} "Not found")))))

(def header (om/factory HeaderComponent))
```

For representation sake, we've got four buttons here - three of which dispatching to the handlers we
defined on the parent component, and a random one that is not going to be found anywhere on the tree.

## How does it work?

Peon will look for any component on the component hierarchy (starting with the current component) that
implemenets `peon/IDispatcher`. When it finds one, it will check whether the handler keyword is available.
If it is, it gets dispatched, if not the algorithm continues to traverse up the hyerarchy until it exhausts
the tree. In that case, a warning is printed on the console because probably that's not what you want.

## Bugs

If you find a bug, submit a [Github issue](https://github.com/luchiniatwork/peon/issues).

## Help

This project is looking for team members who can help this project succeed!
If you are interested in becoming a team member please open an issue.

## License

Copyright © 2017 Tiago Luchini

Distributed under the MIT License.
