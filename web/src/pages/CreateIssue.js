import React, { useState, useContext } from "react";
import "bootstrap/dist/css/bootstrap.css";
import { Multiselect } from "multiselect-react-dropdown";
import Axios from "axios";
import { Link } from "react-router-dom";
import { IssueContex } from "../App";
import styles from "./CreateIssue.module.css";
import { CirclePicker } from "react-color";

function CreateIssue() {
  const pathname = window.location.pathname;
  const idArray = pathname.split("/");
  const Projectid = idArray[idArray.length - 1];

  const [title, setTitle] = useState("");
  const [name, setName] = useState("");
  const [color, setcolor] = useState("#cddc39");
  const [open, setOpen] = useState(false);
  const [description, setDescription] = useState("");
  const [addLabelSelect, setAddLabelSelect] = useState([]);
  const [labelList, setLabelsList] = useState([]);

  const { labels } = useContext(IssueContex);

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

  const submitHandler = (event) => {
    event.preventDefault();

    const labelInfo = labelList.map((item) => {
      delete item.id;
      return item;
    });

    // create a template to send to database
    const newIssue = {
      title: title.trim(),
      description: description,
      labels: labelInfo,
      projectId: Projectid,
    };

    if (validate(newIssue)) {
      // make a post request to send data
      Axios.post("/issue", newIssue)
        .then((res) => {
          console.log(res.data);
          alert("Succesfully Created");
        })
        .catch((error) => {
          if (error.response.status === 409) {
            alert("Title must be unique");
            return;
          }
        });
    }
  };

  const addLabelHandler = () => {
    setOpen(!open);
  };

  const onSelect = (data) => {
    setAddLabelSelect(data);
    setLabelsList(data);
  };

  const onRemove = (data) => {
    setAddLabelSelect(data);
    setLabelsList(data);
  };

  const nameHandler = (event) => {
    const name = event.target.value;
    setName(name.trim());
  };

  const cancelHandler = () => {
    setOpen(false);
  };

  const createLabelHandler = (event) => {
    event.preventDefault();
    if (name.length < 1) {
      alert("Name can not left blank");
      return;
    }
    const newLabel = {
      name: name.charAt(0).toUpperCase() + name.slice(1),
      color: color.slice(1),
    };

    Axios.post("/label", newLabel)
      .then((res) => {
        console.log(res.data);
        alert("Succesfully Created");
        setOpen(false);
        window.location.reload();
      })
      .catch((error) => {
        console.log(error);
      });
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
            selectedValues={addLabelSelect}
            displayValue="name"
            emptyRecordMsg="No options available. Add new one"
            onSelect={onSelect}
            onRemove={onRemove}
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
        <Link to={`/projects/${Projectid}`}>
          <p style={{ textDecoration: "underline" }}>Back to Issue Page</p>
        </Link>
      </div>
    </form>
  );
}

export default CreateIssue;
