import React from "react";
import { Route, Switch } from "react-router-dom";
import "./App.css";
import CreateIssue from "./pages/CreateIssue";
import EditIssue from "./pages/EditIssue";
import Home from "./pages/Home";
import DisplayAllIssues from "./components/DisplayAllIssues";
import CommentPage from "./components/CommentPage"

function App() {
  return (
    <Switch>
      <Route path="/" exact component={Home} />
      <Route path="/createIssue" exact component={CreateIssue} />
      <Route path="/editIssue/:id" exact component={EditIssue} />
      <Route path="/allIssues" exact component={DisplayAllIssues} />
      <Route path="/commentPage" exact component={CommentPage} />
    </Switch>
  );
}

export default App;
