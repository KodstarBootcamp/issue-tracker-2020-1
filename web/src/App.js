import React, { useState, useEffect, createContext } from "react";
import { Route, Switch } from "react-router-dom";
import "./App.css";
import CreateIssue from "./pages/CreateIssue";
import EditIssue from "./pages/EditIssue";
import Home from "./pages/Home";
import DisplayIssues from "./pages/DisplayIssues";
import Axios from "axios";
import { useHistory } from "react-router-dom";
import Labels from "./pages/Labels";
import Login from "./pages/Login";
import CreateProject from "./pages/CreateProject";
import Projects from "./pages/Projects";


export const IssueContex = createContext();

function App() {
  let history = useHistory();
  const [issues, setIssues] = useState();
  const [labels, setLabels] = useState();

  //for labels
  useEffect(() => {
    fetchLabels();
  }, []);

  const fetchLabels = async () => {
    const response = await Axios.get("/labels");

    if (response.data !== undefined) {
      setLabels(response.data);
    }
  };
  //for issues
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

  useEffect(() => {
    sortedIssues();
  }, []);

  const sortedIssues = async () => {
    const response = await Axios.get("/issues");

    if (response.data.length < 1) {
      setIssues();
    } else {
      setIssues(response.data);
    }
  };

  const deleteHandler = (event) => {
    // get id to delete sprecific item

    let usersAnswer = window.confirm("Are you sure?");
    if (usersAnswer === true) {
      const id =
        event.target.id.length > 2 ? event.target.id.slice(5) : event.target.id;

      // make delete request
      Axios.delete("/issue/" + id)
        .then((res) => {
          console.log(res, "delete");
        })
        .then(() => {
          window.location.reload();
          history.push("/");
        });
    } else {
    }
  };

  const editHandler = (event) => {
    const id =
      event.target.id.length > 2 ? event.target.id.slice(5) : event.target.id;
    console.log(id);

    history.push(`/editIssue/${id}`);
  };

  return (
    <IssueContex.Provider
      value={{ issues, deleteHandler, editHandler, labels, setIssues }}
    >
      <Switch>
        <Route path="/" exact component={Home} />
        <Route path="/createIssue" exact component={CreateIssue} />
        <Route path="/editIssue/:id" exact component={EditIssue} />
        <Route path="/allIssues" exact component={DisplayIssues} />
        <Route path="/labels" exact component={Labels} />
        <Route path="/login" exact component={Login} />
        <Route path="/createProject" exact component={CreateProject} />
        <Route path="/projects" exact component={Projects} />
      </Switch>
    </IssueContex.Provider>
  );
}

export default App;
