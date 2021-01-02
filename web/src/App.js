import React, { useState, useEffect, createContext } from "react";
import { Route, Switch } from "react-router-dom";
import "./App.css";
import CreateIssue from "./pages/CreateIssue";
import EditIssue from "./pages/EditIssue";
import Home from "./pages/Home";
import DisplayIssues from "./pages/DisplayIssues";
import Axios from "axios";
import { useHistory } from "react-router-dom";

export const IssueContex = createContext();

function App() {
  let history = useHistory();
  const [issues, setIssues] = useState();

  const deleteHandler = (event) => {
    // get id to delete sprecific item
    const id = event.target.id;
    console.log(id);

    // make delete request
    Axios.delete("/issue/" + id)
      .then((res) => {
        console.log(res, "delete");
      })
      .then(() => {
        window.location.reload();
        history.push("/");
      });
  };

  const editHandler = (event) => {
    const id = event.target.id;

    history.push(`/editIssue/${id}`);
  };

  useEffect(() => {
    fetchIssues();
  }, []);

  const fetchIssues = async () => {
    const response = await Axios.get("/issues");
    if (response.data.length < 1) {
      setIssues();
    } else {
      setIssues(response.data);
    }
  };

  return (
    <IssueContex.Provider value={{ issues, deleteHandler, editHandler }}>
      <Switch>
        <Route path="/" exact component={Home} />
        <Route path="/createIssue" exact component={CreateIssue} />
        <Route path="/editIssue/:id" exact component={EditIssue} />
        <Route path="/allIssues" exact component={DisplayIssues} />
      </Switch>
    </IssueContex.Provider>
  );
}

export default App;
