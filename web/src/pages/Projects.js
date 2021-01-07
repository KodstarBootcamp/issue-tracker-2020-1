import React, { useContext } from "react";
import { Link } from "react-router-dom";
import { IssueContex } from "../App";
import styles from "./Project.module.css";
import Axios from "axios";
import Loader from "react-loader-spinner";

export default function Projects() {
  let { projects } = useContext(IssueContex);

  const deleteHandler = (event) => {
    const id = event.target.id;

    let usersAnswer = window.confirm("Are you sure?");
    if (usersAnswer === true) {
      Axios.delete("/project/" + id)
        .then((res) => {
          console.log(res, "delete");
        })
        .then(() => {
          window.location.reload();
        });
    } else {
      return;
    }
  };
  const editHandler = async (event) => {
    const id = event.target.id;
    const response = await Axios.get("/project/" + id);
    let name = window.prompt("Please enter name:", response.data.name);
    let description = window.prompt(
      "Please enter description:",
      response.data.description
    );
    if ((name == null || name == "") && description == null) {
      return;
    } else {
      const updatedProject = {
        name: name.trim(),
        description: description,
      };
      const response = await Axios.put("/project/" + id, updatedProject);
      window.location.reload();
      console.log(response, 444);
    }
  };

  // mapping around all the issues to display one by one
  const Display = !projects ? (
    <div
      style={{
        width: "100%",
        height: "100",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <Loader type="ThreeDots" color="#2BAD60" height="100" width="100" />
    </div>
  ) : (
    projects.map((item) => (
      <div key={item.id} className={styles.projectCard}>
        <Link to={`projects/${item.id}`}>
          <p className={styles.projectName}>{item.name}</p>
        </Link>
        <p className="w-25">{item.description}</p>

        <div>
          <button
            id={item.id}
            onClick={editHandler}
            type="button"
            className="btn btn-secondary btn-sm mr-2"
          >
            Edit
          </button>
          <button
            id={item.id}
            onClick={deleteHandler}
            type="button"
            className="btn btn-danger btn-sm"
          >
            Delete
          </button>
        </div>
      </div>
    ))
  );

  return (
    <div>
      <div className="d-flex mt-5 justify-content-center">
        <Link to="/">
          <button className="btn btn-outline-secondary btn-sm">
            Back Home
          </button>
        </Link>
      </div>
      <div className={styles.container}>
        <div className={styles.navbar}>
          <Link to="/createProject">
            <button className="btn btn-success btn-sm">
              Create New Project
            </button>
          </Link>
        </div>
        <div>{Display}</div>
      </div>
    </div>
  );
}
