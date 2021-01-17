import React, { useState, useEffect, useContext } from "react";
import Axios from "axios";
import Column from "../components/Column";
import { DragDropContext } from "react-beautiful-dnd";
import { Link } from "react-router-dom";
import { IssueContex } from "../App";
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
  const pathname = window.location.pathname;
  const idArray = pathname.split("/");
  const id = idArray[idArray.length - 1];

  const [removeFilter, setRemoveFilter] = useState(false);
  const [state, setState] = useState(null);

  let { LogOutHandler } = useContext(IssueContex);
  let { UserId, setUserId } = useContext(IssueContex);
  let { refresh } = useContext(IssueContex);

  const exist = (item) => {
    const ids = item.users.map((el) => el.id);
    if (ids.includes(Number(UserId))) {
      return true;
    } else {
      return false;
    }
  };

  useEffect(() => {
    async function fetchIssues() {
      const response = await Axios.get(`/project/${id}/issues`);

      if (response.data) {
        const filteredData = response.data.filter((item) => exist(item));

        const renderedData =
          filteredData.length > 0 ? filteredData : response.data;

        if (filteredData.length > 0) {
          setRemoveFilter(true);
        }

        const formattedResponseData = formatToStringId(renderedData);
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
      } else {
        return;
      }
    }

    fetchIssues();
  }, [id, UserId, refresh]);

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
    Axios.put("/issue/" + id, UpdatedTask)
      .then((res) => {
        console.log(res.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const removeFilterHandler = () => {
    setUserId(null);
    setRemoveFilter(false);
  };

  return (
    <DragDropContext onDragEnd={onDragEndHandler}>
      <div className="d-flex mt-5 justify-content-center">
        <button
          onClick={removeFilterHandler}
          style={{ visibility: removeFilter ? "visible" : "hidden" }}
          className="btn btn-danger btn-sm ml-5"
        >
          Remove Filters
        </button>
        <Link to={`/allIssues/${id}`}>
          <button className="btn btn-info btn-sm ml-5">All Issues</button>
        </Link>
        <Link to="/labels">
          <button className="btn btn-info btn-sm ml-5">All Labels</button>
        </Link>
        <Link to={`/createIssue/${id}`}>
          <button className="btn btn-info btn-sm ml-5">New Issue</button>
        </Link>
        <Link to="/projects">
          <button className="btn btn-info btn-sm ml-5">Projects</button>
        </Link>

        <button onClick={LogOutHandler} className="btn btn-danger btn-sm ml-5">
          LogOut
        </button>
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
            <p style={{ position: "absolute", top: "300px" }}>
              Have you added any issues? If you did not
              <Link to={`/createIssue/${id}`}> Create</Link>
            </p>
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
