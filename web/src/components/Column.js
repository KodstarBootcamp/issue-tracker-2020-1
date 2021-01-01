import React from "react";
import styles from "./Column.module.css";
import Task from "./Task";
import { Droppable } from "react-beautiful-dnd";

function Column({ column, tasks }) {
  return (
    <div className={styles.column}>
      <h1 className={styles.columnHeader}>{column.title}</h1>
      <Droppable droppableId={column.id} key={column.id}>
        {(provided) => (
          <div
            className="bg-danger"
            {...provided.droppableProps}
            ref={provided.innerRef}
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
