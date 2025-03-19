import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";

import "./index.css";

import { BrowserRouter } from "react-router-dom";

import { applyMiddleware, compose, createStore } from "redux";
import rootReducer from "./store/modules";
import { Provider } from "react-redux";

import promiseMiddleware from "redux-promise";
import { thunk } from "redux-thunk";
import storageSession from "redux-persist/lib/storage/session";
import { persistStore, persistReducer } from "redux-persist";
import { PersistGate } from "redux-persist/integration/react";

// //devTools for redux
// const devTools =
//   window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__();

const persistConfig = {
  key: "root",
  storage: storageSession,
};

const persisted = persistReducer(persistConfig, rootReducer);

const store = createStore(
  persisted,
  compose(
    applyMiddleware(promiseMiddleware, thunk)
    // window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
  )
);

const persistor = persistStore(store);

const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
  <Provider store={store}>
    <PersistGate persistor={persistor}>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </PersistGate>
  </Provider>
);
