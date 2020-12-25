import React from "react";
import axios from "axios";
import IconButton from "@material-ui/core/IconButton";
import EditIcon from "@material-ui/icons/Edit";
import DeleteIcon from "@material-ui/icons/Delete";
import { Link } from "react-router-dom";

export default function DisplayIssues({ issues }) {
  const deleteHandler = (event) => {
    // get id to delete sprecific item
    const id = event.target.id;

    // the url that we need to make request
    const URL = "http://localhost:5000/issues/";

    // make delete request
    axios.delete(URL + id).then((res) => {
      console.log(res);
    });

    //reload the page after process
    window.location.reload();
  };

  // mapping around all the issues to display one by one
  const Display = issues.map((item) => (
    <div key={item.id}>
      <h1>{item.title}</h1>
      <div>
        <span>{item.description}</span>
        <IconButton aria-label="EDIT">
          <Link to={`/editIssue/${item.id}`}>
            <EditIcon />
          </Link>
        </IconButton>
        <IconButton aria-label="DELETE" onClick={deleteHandler}>
          <DeleteIcon id={item.id} />
        </IconButton>
      </div>
      <ul>
        {item.labels.map((label) => (
          <li>{label}</li>
        ))}
      </ul>
    </div>
  ));

  return (
    <>
      <div>{Display}</div>
      <Link to="/createIssue">
        <button>Create New Issue</button>
      </Link>
    </>
  );
}
