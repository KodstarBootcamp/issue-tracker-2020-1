import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import { BrowserRouter } from "react-router-dom";
import Axios from "axios";

const token = localStorage.getItem("token");

Axios.defaults.baseURL = "https://mini-track.herokuapp.com";
Axios.defaults.headers.common = { Authorization: `Bearer ${token}` };

ReactDOM.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>,
  document.getElementById("root")
);
