import React, { useState, useContext } from "react";
import "bootstrap/dist/css/bootstrap.css";

import { Link, useHistory } from "react-router-dom";


function CreateProject() {
  let history = useHistory();
  const [title, setTitle] = useState("");
  const [name, setName] = useState("");
  const [open, setOpen] = useState(false);
  const [description, setDescription] = useState("");
  

  const titleHandler = (event) => {
    setTitle(
      event.target.value.charAt(0).toUpperCase() + event.target.value.slice(1)
    );
  };

  const descriptionHandler = (event) => {
    setDescription(event.target.value);
  };

  const validate = (newIssue) => {
    if (newProject.title.length < 1) {
      alert("Title cannot be left blank");
      return false;
    } else if (newProject.title.length > 250) {
      alert("Title cannot exceed 250 characters");
      return false;
    } else if (newProject.description.length > 1500) {
      alert("Description cannot exceed 1500 characters");
      return false;
    }
    return true;
  };

  const submitHandler="";

    // create a template to send to database
    const newProject = {
      title: title.trim(),
      description: description,
      
    };

  

  return (
    <form className="w-75 ml-auto mr-auto mt-5">
      <div className="form-group">
        <label htmlFor="exampleFormControlInput1"> Project Title </label>
        <input
          required
          value={title}
          onChange={titleHandler}
          type="text"
          className="form-control"
          id="exampleFormControlInput1"
          placeholder="Please add issue title"
        />
      </div>
      <div className="form-group mt-5">
        <label htmlFor="exampleFormControlTextarea1"> Project Description </label>
        <textarea
          value={description}
          placeholder="Add descriptive explanation"
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
