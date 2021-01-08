import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.css";
import Axios from "axios";
import { Link } from "react-router-dom";

function CreateProject() {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");

  const titleHandler = (event) => {
    setName(
      event.target.value.charAt(0).toUpperCase() + event.target.value.slice(1)
    );
  };

  const descriptionHandler = (event) => {
    setDescription(event.target.value);
  };

  const validate = (newProject) => {
    if (newProject.name.length < 1) {
      alert("Title cannot be left blank");
      return false;
    } else if (newProject.name.length > 250) {
      alert("Title cannot exceed 250 characters");
      return false;
    } else if (newProject.description.length > 1500) {
      alert("Description cannot exceed 1500 characters");
      return false;
    }
    return true;
  };

  const submitHandler = (event) => {
    event.preventDefault();
    // create a template to send to database
    const newProject = {
      name: name.trim(),
      description: description,
    };
    if (validate(newProject)) {
      Axios.post("/project", newProject)
        .then((res) => {
          console.log(res.data);
          alert("Succesfully Created");

          window.location.reload();
        })
        .catch((error) => {
          if (error.response.status === 409) {
            alert("Title must be unique");
            return;
          }
        });
    }
  };

  return (
    <form className="w-75 ml-auto mr-auto mt-5">
      <div className="form-group">
        <label htmlFor="exampleFormControlInput1"> Project Name </label>
        <input
          required
          value={name}
          onChange={titleHandler}
          type="text"
          className="form-control"
          id="exampleFormControlInput1"
          placeholder="Please add project name"
        />
      </div>
      <div className="form-group mt-5">
        <label htmlFor="exampleFormControlTextarea1">Project Description</label>
        <textarea
          value={description}
          placeholder="Add descriptive explanation (optional)"
          onChange={descriptionHandler}
          className="form-control"
          id="exampleFormControlTextarea1"
          rows="3"
        ></textarea>
      </div>

      <div className="d-flex mt-5 justify-content-between">
        <button onClick={submitHandler} className="btn btn-primary">
          Submit
        </button>
        <Link to="/">
          <p style={{ textDecoration: "underline" }}>Back to Home Page</p>
        </Link>
        <Link to="/projects">
          <p style={{ textDecoration: "underline" }}>Back to Projects Page</p>
        </Link>
      </div>
    </form>
  );
}

export default CreateProject;
