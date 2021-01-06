import React, { useState, useEffect } from "react";
import Axios from "axios";
import Column from "../components/Column";
import { DragDropContext } from "react-beautiful-dnd";
import { Link } from "react-router-dom";
import Loader from "react-loader-spinner";

const groupBy = function (xs, key) {
  return xs.reduce(function (rv, x) {
    (rv[x[key]] = rv[x[key]] || []).push(x);
    return rv;
  }, {});
};
const getIDs = function (arr = []) {
  return arr.map((e) => e.id);
};

const formatTasks = function (arr) {
  return arr.reduce(function (acc, cur) {
    acc[cur.id] = cur;
    return acc;
  }, {});
};

const formatToStringId = function (arr) {
  return arr.map((e) => {
    return { ...e, id: `task-${e.id}` };
  });
};
function Home() {
  const [state, setState] = useState(null);
  useEffect(() => {
    fetchIssues();
  }, []);
  const fetchIssues = async () => {
    const response = await Axios.get("/issues");
    console.log(response.data, 35);
    const formattedResponseData = formatToStringId(response.data);
    const tasks = groupBy(formattedResponseData, "category");
    const initialData = {
      tasks: formatTasks(formattedResponseData),
      columns: {
        new: {
          id: "new",
          title: "New",
          taskIds: getIDs(tasks.new),
        },
        backlog: {
          id: "backlog",
          title: "Backlog",
          taskIds: getIDs(tasks.backlog),
        },
        started: {
          id: "started",
          title: "Started",
          taskIds: getIDs(tasks.started),
        },
        review: {
          id: "review",
          title: "Review",
          taskIds: getIDs(tasks.review),
        },
        finished: {
          id: "finished",
          title: "Finished",
          taskIds: getIDs(tasks.finished),
        },
      },
      // Facilitate reordering of the columns
      columnOrder: ["new", "backlog", "started", "review", "finished"],
    };
    setState(initialData);
  };

  const findTask = (id) => {
    const task = state.tasks[id];
    return task;
  };

  // ondrag handler
  const onDragEndHandler = async (result) => {
    const { destination, source, draggableId } = result;

    // hedef yoksa oldugun yere geri dön
    if (!destination) {
      console.log("destination is null");
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

    const finish = state.columns[destination.droppableId]; // hedef

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

    const draggedTask = findTask(draggableId);

    const UpdatedTask = { ...draggedTask, category: newFinish.id };
    delete UpdatedTask.id;

    const id = draggedTask.id.slice(5);

    const newState = {
      ...state,
      columns: {
        ...state.columns,
        [newStart.id]: newStart,
        [newFinish.id]: newFinish,
      },
    };
    setState(newState);
    const response = await Axios.put("/issue/" + id, UpdatedTask);
    console.log(response);
  };

  return (
    <DragDropContext onDragEnd={onDragEndHandler}>
      <div className="d-flex mt-5 justify-content-center">
        <Link to="/allIssues">
          <button className="btn btn-info btn-sm ml-5">All Issues</button>
        </Link>
        <Link to="/labels">
          <button className="btn btn-secondary btn-sm ml-5">All Labels</button>
        </Link>
        <Link to="/createIssue">
          <button className="btn btn-success btn-sm ml-5">New Issue</button>
        </Link>
      </div>
      <div className="d-flex overflow-auto mt-1">
        {state === null ? (
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
