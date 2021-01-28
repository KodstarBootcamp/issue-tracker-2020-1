import React, { useState, useContext } from "react";
import styles from "./Labels.module.css";
import { CirclePicker } from "react-color";
import { Link, useHistory } from "react-router-dom";
import { IssueContex } from "../App";
import Loader from "react-loader-spinner";
import Axios from "axios";

function Labels() {
  let history = useHistory();
  const [color, setcolor] = useState("#cddc39");
  const [open, setopen] = useState(false);
  const [edit, setedit] = useState(false);
  const [name, setName] = useState("");
  const [openNewLabel, setopenNewLabel] = useState(false);

  const { labels } = useContext(IssueContex);

  const cancelHandler = () => {
    setopenNewLabel(false);
    setedit(false);
    setopen(false);
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
    setopenNewLabel(false);
    window.location.reload();
  };

  const deleteLabelHandler = (event) => {
    const id = event.target.id;
    console.log(id);

    // make delete request
    Axios.delete("/label/" + id)
      .then((res) => {
        console.log(res, "delete");
      })
      .then(() => {
        window.location.reload();
        history.push("/labels");
      });
  };
  const editLabelHandler = async (event) => {
    setedit(true);
    const id = event.target.id;
    window.localStorage.setItem("id", id);
    const response = await Axios.get(`/label/${id}`);
    setName(response.data.name);
    const updatedColor = "#" + response.data.color;
    setcolor(updatedColor);
  };

  const editSubmitHandler = async () => {
    const id = window.localStorage.getItem("id");
    if (name.length < 1) {
      alert("Name can not left blank");
      return;
    }
    const UpdatedLabel = {
      name: name.charAt(0).toUpperCase() + name.slice(1),
      color: color.slice(1),
    };
    const responseUpdate = await Axios.put("/label/" + id, UpdatedLabel);
    console.log(responseUpdate);
    alert("Succesfully edited");
    setopenNewLabel(false);
    setedit(false);
    window.location.reload();
    window.localStorage.removeItem("id");
  };

  const nameHandler = (event) => {
    const name = event.target.value;
    setName(name.trim());
  };

  return (
    <div className={styles.container}>
      <div className={styles.navbar}>
        <button
          onClick={() => {
            setopenNewLabel(!openNewLabel);
          }}
          type="button"
          className="btn btn-success"
        >
          New Label
        </button>

        <button
          onClick={() => {
            window.history.go(-1);
          }}
          className="btn btn-outline-secondary btn-sm"
        >
          Back Home
        </button>
      </div>
      <div
        style={{ display: openNewLabel ? "flex" : "none" }}
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
            setopen(!open);
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
      <div
        style={{ display: edit ? "flex" : "none" }}
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
            setopen(!open);
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
            onClick={editSubmitHandler}
            type="button"
            className="btn btn-success"
          >
            Submit
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
      {!labels ? (
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
        labels.map((item) => (
          <div key={item.id} className={styles.labelCard}>
            <span
              style={{ backgroundColor: `#${item.color}` }}
              className={styles.labelContainer}
            >
              {item.name}
            </span>
            <div>
              <button
                onClick={editLabelHandler}
                id={item.id}
                className={styles.buttons}
              >
                Edit
              </button>
              <button
                id={item.id}
                onClick={deleteLabelHandler}
                className={styles.buttons}
              >
                Delete
              </button>
            </div>
          </div>
        ))
      )}
    </div>
  );
}

export default Labels;
