import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.css";
import { Multiselect } from "multiselect-react-dropdown";
import Axios from "axios";
import { Link, useHistory } from "react-router-dom";

function CreateIssue() {
  let history = useHistory();
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [labels, setLabels] = useState([]);
  const [options, setOptions] = useState([
    { Label: "Bug" },
    { Label: "Enhancement" },
    { Label: "Question" },
    { Label: "Suggestion" },
    { Label: "Critical" },
  ]);

  const titleHandler = (event) => {
    setTitle(
      event.target.value.charAt(0).toUpperCase() + event.target.value.slice(1)
    );
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
    } else if (newIssue.description.length > 1500) {
      alert("Description cannot exceed 1500 characters");
      return false;
    }
    return true;
  };

  const submitHandler = async (event) => {
    event.preventDefault();

    // just retrieve text of the labels objects
    let labelText;
    if (Object.values(labels).length < 1) {
      labelText = [];
    } else {
      labelText = labels.data.map((item) => item.Label);
    }

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
      alert("Succesfully created");
      history.push("/");
    }
  };

  const addLabelHandler = () => {
    const labelName = prompt("Please enter  label name", "");
    if (labelName === null) {
      return;
    }
    if (labelName.length < 1) {
      alert("Title can not be left blank");
      return;
    }

    const newLabelObject = {
      Label: labelName.charAt(0).toUpperCase() + labelName.slice(1),
    };

    setOptions([...options, newLabelObject]);
  };

  return (
    <form className="w-75 ml-auto mr-auto mt-5">
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
      <div className="form-group mt-5">
        <label htmlFor="exampleFormControlTextarea1"> Issue Description </label>
        <textarea
          value={description}
          placeholder="Add descriptive explanation"
          onChange={descriptionHandler}
          className="form-control"
          id="exampleFormControlTextarea1"
          rows="3"
        ></textarea>
      </div>
      <div className="d-flex ">
        <div className="form-group  flex-grow-1 mt-3">
          <label htmlFor="exampleFormControlSelect1"> Label selection </label>
          <Multiselect
            options={options}
            displayValue="Label"
            emptyRecordMsg="No options available. Add new one"
            onSelect={(data) => setLabels({ ...data, data })}
            onRemove={(data) => setLabels({ ...data, data })}
          />
        </div>
        <div>
          <button
            onClick={addLabelHandler}
            type="button"
            className="btn btn-success mt-5 ml-3"
          >
            Add New Label
          </button>
        </div>
      </div>

      <div className="d-flex mt-5 justify-content-between">
        <button onClick={submitHandler} className="btn btn-primary">
          Submit
        </button>
        <Link to="/">
          <p style={{ textDecoration: "underline" }}>Back to Home Page</p>
        </Link>
      </div>
    </form>
  );
}

export default CreateIssue;
