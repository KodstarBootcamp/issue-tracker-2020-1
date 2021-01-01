import React from "react";
import axios from "axios";
import { useHistory } from "react-router-dom";
import styles from "./Task.module.css";
import { Draggable } from "react-beautiful-dnd";

function Task({ task, index }) {
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

  return (
    <Draggable key={task.id} draggableId={task.id} index={index}>
      {(provided) => (
        <div
          className={styles.container}
          ref={provided.innerRef}
          {...provided.draggableProps}
          {...provided.dragHandleProps}
        >
          <div className="row">
            <div className="col-sm-8">
              <h5 className={styles.cardTitle}>{task.title}</h5>
              <p className={styles.cardText}>{task.description}</p>
              <div className={styles.listLabels}>
                {task.labels.map((label, i) => (
                  <div className={styles.itemLabels} key={i}>
                    {label.charAt(0).toUpperCase() + label.slice(1)}
                  </div>
                ))}
              </div>
            </div>
            <div className="col-sm-4">
              <div className={styles.buttons}>
                <button
                  id={task.id}
                  onClick={editHandler}
                  type="button"
                  className="btn btn-secondary btn-sm mb-1 mt-3"
                >
                  Edit
                </button>
                <button
                  id={task.id}
                  onClick={deleteHandler}
                  type="button"
                  className="btn btn-danger btn-sm"
                >
                  Delete
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </Draggable>
  );
}

export default Task;
