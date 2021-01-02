import React, { useContext } from "react";
import { IssueContex } from "../App";
import styles from "./Task.module.css";
import { Draggable } from "react-beautiful-dnd";

function Task({ task, index }) {
  const { deleteHandler } = useContext(IssueContex);
  const { editHandler } = useContext(IssueContex);

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
            <div className="col-8">
              <h5 className={styles.cardTitle}>{task.title}</h5>
              <p className={styles.cardText}>{task.description}</p>
              <div className={styles.listLabels}>
                {task.labels.map((label, i) => (
                  <div
                    style={{ backgroundColor: `#${label.color}` }}
                    className={styles.itemLabels}
                    key={i}
                  >
                    {label.name.charAt(0).toUpperCase() + label.name.slice(1)}
                  </div>
                ))}
              </div>
            </div>
            <div className="col-4">
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
