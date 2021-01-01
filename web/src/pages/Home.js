import React, { useState, useEffect } from "react";
import Axios from "axios";
import Column from "../components/Column";
import { DragDropContext } from "react-beautiful-dnd";

function Home() {
  const [state, setState] = useState();

  useEffect(() => {
    fetchIssues();
  }, []);

  const fetchIssues = async () => {
    const response = await Axios.get("/issues");
    console.log(response.data, "Home.js");

    const newData = response.data.map((item) => {
      item.id = `task-${item.id}`;
      return item;
    });

    if (response.data.length > 1) {
      let newObj = {};
      newData.forEach((data) => {
        return (newObj[data.id] = data);
      });

      const backlogIds = newData
        .filter((item) => item.category === "backlog")
        .map((item) => item.id);

      const newIds = newData
        .filter((item) => item.category === "new")
        .map((item) => item.id);

      const startedIds = newData
        .filter((item) => item.category === "started")
        .map((item) => item.id);
      console.log(startedIds, 27);
      const reviewIds = newData
        .filter((item) => item.category === "review")
        .map((item) => item.id);
      console.log(reviewIds);

      const finishedIds = newData
        .filter((item) => item.category === "finished")
        .map((item) => item.id);

      const initialData = {
        tasks: newObj,
        columns: {
          "column-1": {
            id: "column-1",
            title: "New",
            taskIds: newIds || [],
          },
          "column-2": {
            id: "column-2",
            title: "Backlog",
            taskIds: backlogIds || [],
          },
          "column-3": {
            id: "column-3",
            title: "Started",
            taskIds: startedIds || [],
          },
          "column-4": {
            id: "column-4",
            title: "Review",
            taskIds: reviewIds || [],
          },
          "column-5": {
            id: "column-5",
            title: "Finished",
            taskIds: finishedIds || [],
          },
        },
        // Facilitate reordering of the columns
        columnOrder: [
          "column-1",
          "column-2",
          "column-3",
          "column-4",
          "column-5",
        ],
      };
      setState(initialData);
    }
  };

  const onDragEndHandler = (result) => {
    const { destination, source, draggableId } = result;

    console.log(state, 100);
    console.log(result);
    console.log(draggableId, 87);
    // hedef yoksa oldugun yere geri dön
    if (!destination) {
      console.log("hey");
      return;
    }

    //task aynı yere tekrar konduysa bir şey yapma
    if (
      destination.droppableId === source.droppableId &&
      destination.index === source.index
    ) {
      return;
    }

    const start = state.columns[source.droppableId]; // başlangıc
    console.log(start);
    const finish = state.columns[destination.droppableId]; // hedef
    console.log(finish);

    if (start === finish) {
      const newTaskIds = Array.from(start.taskIds);
      newTaskIds.splice(source.index, 1);
      newTaskIds.splice(destination.index, 0, draggableId);

      const newColumn = {
        ...finish,
        taskIds: newTaskIds,
      };

      const newState = {
        ...state,
        columns: {
          ...state.columns,
          [newColumn.id]: newColumn,
        },
      };

      setState(newState);
      return;
    }

    // Moving from one list to another
    const startTaskIds = Array.from(start.taskIds);
    startTaskIds.splice(source.index, 1);
    const newStart = {
      ...start,
      taskIds: startTaskIds,
    };

    const finishTaskIds = Array.from(finish.taskIds);
    finishTaskIds.splice(destination.index, 0, draggableId);
    const newFinish = {
      ...finish,
      taskIds: finishTaskIds,
    };

    const newState = {
      ...state,
      columns: {
        ...state.columns,
        [newStart.id]: newStart,
        [newFinish.id]: newFinish,
      },
    };
    setState(newState);
  };

  return (
    <DragDropContext onDragEnd={onDragEndHandler}>
      <div className="d-flex overflow-auto mt-5">
        {state === undefined ? (
          <p>Add</p>
        ) : (
          state.columnOrder.map((columnId) => {
            const column = state.columns[columnId];

            const tasks = column.taskIds.map((taskId) => state.tasks[taskId]);

            return <Column key={column.id} column={column} tasks={tasks} />;
          })
        )}
      </div>
    </DragDropContext>
  );
}

export default Home;
