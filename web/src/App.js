import React from "react";
import { Route, Switch } from "react-router-dom";
import "./App.css";
import CreateIssue from "./pages/CreateIssue";
import EditIssue from "./pages/EditIssue";
import Home from "./pages/Home";

function App() {
  return (
    <Switch>
      <Route path="/" exact component={Home} />
      <Route path="/createIssue" exact component={CreateIssue} />
      <Route path="/editIssue/:id" exact component={EditIssue} />
    </Switch>
  );
}

export default App;
