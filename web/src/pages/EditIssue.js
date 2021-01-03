import React, { useEffect, useState, useContext } from "react";
import Axios from "axios";
import { IssueContex } from "../App";
import "bootstrap/dist/css/bootstrap.css";
import { Multiselect } from "multiselect-react-dropdown";
import { useHistory, Link } from "react-router-dom";
import styles from "./CreateIssue.module.css";
import { CirclePicker } from "react-color";

function EditIssue(props) {
  let history = useHistory();

  // get id of the issue to edit
  const id = props.match.params.id.slice(5);

  const [title, setTitle] = useState("");
  const [color, setcolor] = useState("#cddc39");
  const [name, setName] = useState("");
  const [open, setOpen] = useState(false);
  const [description, setDescription] = useState("");
  const [labelsList, setLabelsList] = useState([]);
  const [preselect, setPreselect] = useState([]);

  const { labels } = useContext(IssueContex);

  useEffect(async () => {
    const response = await Axios.get("/issue/" + id);
    console.log(response.data, "edit");

    const { title, description, labels } = response.data;

    setTitle(title);
    setDescription(description);

    setPreselect(labels);
    setLabelsList(labels);
  }, []);

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

    /*   const UpdatedIssue = {
      title: title.trim(),
      description: description,
      labels: labelText,
    }; */

    /*   if (validate(UpdatedIssue)) {
      const response = await Axios.put("/issue/" + id, UpdatedIssue);
      console.log(response);
      alert("Succesfully edited");
      history.push("/");
    } */
  };

  const addLabelHandler = () => {
    setOpen(!open);
  };

  const onSelect = (data) => {
    setPreselect(data);
    setLabelsList(data);
  };

  const onRemove = (data) => {
    setPreselect(data);
    setLabelsList(data);
  };

  const nameHandler = (event) => {
    const name = event.target.value;
    setName(name.trim());
  };

  const cancelHandler = () => {
    setOpen(false);
  };

  const createLabelHandler = async (event) => {
    event.preventDefault();
    if (name.length < 1) {
      alert("Name can not left blank");
      return;
    }
    const newLabel = {
      name: name.charAt(0).toUpperCase() + name.slice(1),
      color: color.slice(1),
    };
    const response = await Axios.post("/label", newLabel);
    console.log(response, 33);
    alert("Succesfully created");
    setOpen(false);
    window.location.reload();
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
            options={labels}
            selectedValues={preselect}
            displayValue="name"
            onSelect={onSelect}
            onRemove={onRemove}
            emptyRecordMsg="No options available. Add new one"
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
      <div
        style={{ display: open ? "flex" : "none" }}
        className={styles.addLabel}
      >
        <input
          value={name}
          onChange={nameHandler}
          style={{ width: "200px" }}
          type="text"
          className="form-control"
          placeholder="Label Name"
        ></input>
        <button
          className={styles.pickerbutton}
          style={{ backgroundColor: color }}
          onClick={() => {
            setOpen(!open);
          }}
        >
          Select Color
        </button>

        <div>
          <button
            onClick={cancelHandler}
            type="button"
            className="btn btn-secondary mx-3"
          >
            Cancel
          </button>
          <button
            onClick={createLabelHandler}
            type="button"
            className="btn btn-success"
          >
            Create
          </button>
        </div>
      </div>
      <div className={styles.pickerarea}>
        <div style={{ display: open ? "block" : "none" }}>
          <CirclePicker
            color={color}
            onChangeComplete={(color) => setcolor(color.hex)}
          />
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

export default EditIssue;
