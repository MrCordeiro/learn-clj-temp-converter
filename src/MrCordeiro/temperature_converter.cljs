(ns MrCordeiro.temperature-converter
  (:require
   [goog.dom :as gdom]
   [goog.events :as gevents]))


(defn f->c "Converts Fahrenheit to Celsius" [def-f]
  (/ (- def-f 32) 1.8))

(defn c->f "Converts Celsius to Fahrenheit" [def-c]
  (+ (* def-c 1.8) 32))


(def celsius-radio (gdom/getElement "unit-c"))
(def fahrenheit-radio (gdom/getElement "unit-f"))
(def temp-input (gdom/getElement "temp"))
(def output-target (gdom/getElement "temp-out"))
(def output-unit-target (gdom/getElement "unit-out"))


(defn get-input-unit []
  (if (.-checked celsius-radio)
    :celsius
    :fahrenheit))

(defn get-input-temp []
  (js/parseInt (.-value temp-input)))

(defn set-output-temp [temp]
  (gdom/setTextContent output-target (.toFixed temp 2)))

(defn update-output-temp [_]
  (if (= :celsius (get-input-unit))
    (do
      (set-output-temp (c->f (get-input-temp)))
      (gdom/setTextContent output-unit-target "F"))
    (do (set-output-temp (f->c (get-input-temp)))
        (gdom/setTextContent output-unit-target "C"))))


(gevents/listen temp-input "keyup" update-output-temp)
(gevents/listen celsius-radio "click" update-output-temp)
(gevents/listen fahrenheit-radio "click" update-output-temp)



;; specify reload hook with ^:after-load metadata
(defn ^:after-load on-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
