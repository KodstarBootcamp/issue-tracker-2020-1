import React from "react";
import styles from "./Column.module.css";
import { Droppable } from "react-beautiful-dnd";
import Task from "./Task";

function Column({ column, tasks }) {
  return (
    <div className={styles.column}>
      <h1 className={styles.columnHeader}>
        {column.title} <span className={styles.count}>{tasks.length}</span>
      </h1>
      <Droppable droppableId={column.id} key={column.id}>
        {(provided) => (
          <div
            {...provided.droppableProps}
            ref={provided.innerRef}
            style={{ minHeight: "300px" }}
          >
            {tasks.map((task, i) => (
              <Task key={`task-${task.id}`} task={task} index={i} />
            ))}
            {provided.placeholder}
          </div>
        )}
      </Droppable>
    </div>
  );
}
export default Column;
