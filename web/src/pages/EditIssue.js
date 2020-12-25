import React, { useEffect, useState } from "react";
import Axios from "axios";
import "bootstrap/dist/css/bootstrap.css";
import { Multiselect } from "multiselect-react-dropdown";
import { useHistory } from "react-router-dom";

function EditIssue(props) {
  let history = useHistory();

  // get id of the issue to edit
  const id = props.match.params.id;

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [labels, setLabels] = useState([]);
  const [preselect, setPreselect] = useState([]);
  const [options] = useState([
    { Label: "Bug" },
    { Label: "Enhancement" },
    { Label: "Question" },
    { Label: "Suggestion" },
    { Label: "Critical" },
  ]);

  useEffect(async () => {
    const URL = "http://localhost:5000/issues/";
    const response = await Axios.get(URL + id);
    const { title, description, labels } = response.data;
    setTitle(title);
    setDescription(description);

    const select = options.filter(
      (item) => labels.includes(item.Label) === true
    );
    setPreselect(select);

    console.log(response);
  }, []);

  const titleHandler = (event) => {
    setTitle(event.target.value);
  };

  const descriptionHandler = (event) => {
    setDescription(event.target.value);
  };

  const validate = (UpdatedIssue) => {
    if (UpdatedIssue.title.length < 1) {
      alert("Title cannot be left blank");
      return false;
    } else if (UpdatedIssue.title.length > 250) {
      alert("Title cannot exceed 250 characters");
      return false;
    } else if (UpdatedIssue.description.length < 1) {
      alert("Description cannot be left blank");
      return false;
    } else if (UpdatedIssue.description.length > 1000) {
      alert("Description cannot exceed 1000 characters");
      return false;
    }
    return true;
  };

  const submitHandler = async (event) => {
    event.preventDefault();
    const labelText = labels.data.map((item) => item.Label);

    const UpdatedIssue = {
      title: title,
      description: description,
      labels: labelText,
    };

    const URL = "http://localhost:5000/issues/";

    if (validate(UpdatedIssue)) {
      const response = await Axios.put(URL + id, UpdatedIssue);
      history.push("/");

      console.log(response);
    }
  };

  const addLabelHandler = () => {
    console.log("ehy");
  };

  return (
    <form className="w-75 ml-auto mr-auto mt-5" onSubmit={submitHandler}>
      <div className="form-group">
        <label htmlFor="exampleFormControlInput1"> Title </label>
        <input
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
          selectedValues={preselect}
          displayValue="Label"
          onSelect={(data) => setLabels({ ...data, data })}
          onRemove={(data) => setLabels({ ...data, data })}
          emptyRecordMsg="No options available. Add new one"
        />
      </div>
      <button
        type="submit"
        className="btn btn-primary"
        disabled={!(Object.values(labels).length > 0)}
      >
        Submit
      </button>
    </form>
  );
}

export default EditIssue;
