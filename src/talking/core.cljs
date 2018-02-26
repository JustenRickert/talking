(ns talking.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [talking.model :refer [tag-sentence-string]]))

;; (enable-console-print!)

(def message "hello hollow traveler of the internet")
(def message-other "I hope the rest of your stay is good")

(defonce app-state (atom {:one? true
                          :message message}))
(defn- handle-change-messages [e]
    (swap! app-state update-in [:message]
           #(if (:one? @app-state)
              message
              message-other))
    (swap! app-state update-in [:one?]
           #(not (:one? @app-state))))
(om/root
 (fn [data owner]
   (reify om/IRender
     (render [_]
       (dom/div nil
                (dom/button #js {:onClick handle-change-messages} "Click to change messages")
                (dom/h1 nil "Original message")
                (dom/h3 nil (:message data))
                (dom/h1 nil "Part of speech tagged message")
                (dom/h3 nil (tag-sentence-string (:message data)))))))
 app-state
 {:target (. js/document (getElementById "app"))})

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
