import React from "react";
import axios from "axios";
import { Link, useHistory } from "react-router-dom";
import "../../src/components/DisplayIssues.css";

export default function DisplayIssues({ issues }) {
  let history = useHistory();
  const deleteHandler = (event) => {
    // get id to delete sprecific item
    const id = event.target.id;
    console.log(id);

    // make delete request
    axios
      .delete("/issue/" + id)
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

  // mapping around all the issues to display one by one
  const Display = !issues
    ? history.push("/createIssue")
    : issues.map((item) => (
        <div key={item.id} className="card mt-3" style={{ width: "18rem" }}>
          <div className="card-body">
            <h5 className="card-title">{item.title}</h5>
            <p className="card-text">{item.description}</p>
            <ul>
              {item.labels.map((label, i) => (
                <li key={i}>
                  {label.charAt(0).toUpperCase() + label.slice(1)}
                </li>
              ))}
            </ul>
            <div className="btn-1">
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
    <h1>
      <div className="d-flex flex-column flex-wrap justify-content-around">
        {Display}
      </div>

      <Link to="/createIssue">
        <button style={{ position: "fixed", top: "35px", right: "35px" }}>
          Create New Issue
        </button>
      </Link>
    </h1>
  );
}
