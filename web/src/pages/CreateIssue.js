import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.css";
import { Multiselect } from "multiselect-react-dropdown";
import Axios from "axios";
import { useHistory } from "react-router-dom";

function CreateIssue() {
  let history = useHistory();
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [labels, setLabels] = useState([]);
  const [options] = useState([
    { Label: "Bug" },
    { Label: "Enhancement" },
    { Label: "Question" },
    { Label: "Suggestion" },
    { Label: "Critical" },
  ]);

  const titleHandler = (event) => {
    setTitle(event.target.value);
  };

  const descriptionHandler = (event) => {
    setDescription(event.target.value);
  };

  const validate = (newIssue) => {
    if (newIssue.title.length < 1) {
      alert("Title cannot be left blank");
      return false;
    } else if (newIssue.title.length > 250) {
      alert("Title cannot exceed 250 characters");
      return false;
    } else if (newIssue.description.length < 1) {
      alert("Description cannot be left blank");
      return false;
    } else if (newIssue.description.length > 1000) {
      alert("Description cannot exceed 1000 characters");
      return false;
    }
    return true;
  };

  const submitHandler = async (event) => {
    event.preventDefault();

    // just retrieve text of the labels objects
    const labelText = labels.data.map((item) => item.Label);

    // create a template to send to database
    const newIssue = {
      title: title,
      description: description,
      labels: labelText,
    };

    const URL = "http://localhost:5000/issues";

    if (validate(newIssue)) {
      // make a post request to send data
      const response = await Axios.post(URL, newIssue);
      console.log(response);
      history.push("/");
    }
  };

  return (
    <form className="w-75 ml-auto mr-auto mt-5" onSubmit={submitHandler}>
      <div className="form-group">
        <label htmlFor="exampleFormControlInput1"> Title </label>
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
      <div className="form-group">
        <label htmlFor="exampleFormControlTextarea1"> Issue Description </label>
        <textarea
          required
          value={description}
          placeholder="Add descriptive explanation"
          onChange={descriptionHandler}
          className="form-control"
          id="exampleFormControlTextarea1"
          rows="3"
        ></textarea>
      </div>
      <div className="form-group">
        <label htmlFor="exampleFormControlSelect1"> Label selection </label>{" "}
        <Multiselect
          options={options}
          displayValue="Label"
          onSelect={(data) => setLabels({ ...data, data })}
          onRemove={(data) => setLabels({ ...data, data })}
        />
      </div>
      <button
        type="submit"
        class="btn btn-primary"
        disabled={!(Object.values(labels).length > 0)}
      >
        Submit
      </button>
    </form>
  );
}

export default CreateIssue;
