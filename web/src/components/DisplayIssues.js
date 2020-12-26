import React from "react";
import axios from "axios";
import { Link, useHistory } from "react-router-dom";

export default function DisplayIssues({ issues }) {
  let history = useHistory();
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

  const editHandler = (event) => {
    const id = event.target.id;
    console.log(id);
    history.push(`/editIssue/${id}`);
  };

  // mapping around all the issues to display one by one
  const Display = issues.map((item) => (
    <div key={item.id} className="card mt-3 " style={{ width: "18rem" }}>
      <div className="card-body">
        <h5 className="card-title">{item.title}</h5>
        <p className="card-text">{item.description}</p>
        <ul>
          {item.labels.map((label, i) => (
            <li key={i}>{label}</li>
          ))}
        </ul>
        <div>
          <button
            id={item.id}
            onClick={editHandler}
            type="button"
            className="btn btn-secondary mr-3"
          >
            Edit
          </button>
          <button
            id={item.id}
            onClick={deleteHandler}
            type="button"
            className="btn btn-danger"
          >
            Delete
          </button>
        </div>
      </div>
    </div>
  ));

  return (
    <>
      <div className="d-flex flex-column flex-wrap justify-content-around">
        {Display}
      </div>
      <div style={{ position: "fixed", top: "35px", right: "35px" }}>
        <Link to="/createIssue">
          <button>Create New Issue</button>
        </Link>
      </div>
    </>
  );
}
