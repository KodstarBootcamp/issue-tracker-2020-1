import React, { useContext, useState, useEffect } from "react";
import { IssueContex } from "../App";
import styles from "./Task.module.css";
import { Draggable } from "react-beautiful-dnd";
import "@szhsin/react-menu/dist/index.css";
import { Menu, MenuItem, MenuButton } from "@szhsin/react-menu";
import { Drawer, Divider, Col, Row } from "antd";
import "antd/dist/antd.css";
import { Multiselect } from "multiselect-react-dropdown";
import Axios from "axios";

function Task({ task, index }) {
  const [visible, setVisible] = useState(false);
  const [show, setShow] = useState(false);
  const [showLabel, setShowLabel] = useState(false);
  const [userList, setUserList] = useState([]);

  const { labels } = useContext(IssueContex);
  const { setUserId } = useContext(IssueContex);
  const { refresh, setRefresh } = useContext(IssueContex);

  // console.log(labels, 20);

  const id = task.id.slice(5);

  const showDrawer = () => {
    setVisible(true);
  };
  const onClose = () => {
    setVisible(false);
  };

  const { deleteHandler } = useContext(IssueContex);
  const { editHandler } = useContext(IssueContex);

  useEffect(() => {
    Axios.get("/user")
      .then((res) => {
        setUserList(res.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const onSelectUser = (data) => {
    const ids = data.map((item) => item.id);
    Axios.put(`/issue/${id}/assignee`, ids)
      .then((res) => {
        console.log(res.data, 49);
        setRefresh(!refresh);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const onRemoveUser = (data) => {
    const ids = data.map((item) => item.id);

    Axios.put(`/issue/${id}/assignee`, ids)
      .then((res) => {
        console.log(res);
        setRefresh(!refresh);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const onSelectLabel = (data) => {
    const id = task.id.slice(5);
    const UpdatedObj = { ...task };
    delete UpdatedObj.id;
    console.log(id, 73);
    const labelInfo = data.map((item) => {
      delete item.id;
      return item;
    });

    const UpdatedIssue = {
      ...UpdatedObj,
      labels: labelInfo,
    };

    console.log(UpdatedIssue, 84);
    Axios.put("/issue/" + id, UpdatedIssue)
      .then((res) => {
        console.log(res.data);
        setRefresh(!refresh);
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const onRemoveLabel = (data) => {
    const id = task.id.slice(5);
    const UpdatedObj = { ...task };
    delete UpdatedObj.id;
    console.log(id);
    const labelInfo = data.map((item) => {
      delete item.id;
      return item;
    });

    const UpdatedIssue = {
      ...UpdatedObj,
      labels: labelInfo,
    };
    Axios.put("/issue/" + id, UpdatedIssue)
      .then((res) => {
        console.log(res.data);
        setRefresh(!refresh);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const toggleForm = (event) => {
    event.preventDefault();
    setShow(!show);
  };

  const toggleFormLabel = (event) => {
    event.preventDefault();
    setShowLabel(!showLabel);
  };

  const toggleIssueHandler = (event) => {
    const id = event.target.id.slice(5);
    const UpdatedObj = { ...task };
    delete UpdatedObj.id;
    const UpdatedIssue = {
      ...UpdatedObj,

      state: UpdatedObj.state === "open" ? "closed" : "open",
    };

    Axios.put("/issue/" + id, UpdatedIssue)
      .then((res) => {
        console.log(res.data);
        setRefresh(!refresh);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const showAssignedUser = (event) => {
    const id = event.target.id;
    setUserId(id);
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
            <div className="col-9">
              <i
                className={`fa fa-circle text-${
                  task.state === "open" ? "success" : "danger"
                } ml-2`}
              ></i>
              <button
                className={styles.cardTitle}
                type="primary"
                onClick={showDrawer}
              >
                {task.title}
              </button>
              <Drawer
                width={340}
                placement="right"
                closable={false}
                onClose={onClose}
                visible={visible}
              >
                <h3 className="my-3">{task.title}</h3>
                <p className="">
                  #{id} opened by {task.openedBy}
                </p>
                <p>
                  <span className={styles.descriptionDrawer}>Description:</span>
                  {task.description}
                </p>
                <Divider />
                <Row>
                  <Col span={20}>
                    <p className={styles.contentTitle}>Assigness</p>
                  </Col>
                  <Col span={4}>
                    <i
                      style={{ cursor: "pointer" }}
                      onClick={toggleForm}
                      className="fa fa-angle-double-down fa-2x text-secondary"
                    ></i>
                  </Col>
                </Row>
                <div className="">
                  {task.users
                    ? task.users.map((item) => (
                        <span
                          style={{
                            backgroundColor: "lightskyblue",
                            padding: "5px 10px",
                            fontSize: "12px",
                            margin: "5px",
                            color: "white",
                            borderRadius: "10px",
                            fontWeight: "bold",
                          }}
                          key={item.id}
                        >
                          {item.username.charAt(0).toUpperCase()}
                        </span>
                      ))
                    : "no user assigned"}
                </div>
                <div
                  style={{ display: show ? "block" : "none" }}
                  className="form-group mt-3"
                >
                  <label htmlFor="exampleFormControlSelect1">Assign User</label>
                  <Multiselect
                    options={userList}
                    displayValue="username"
                    selectedValues={task.users}
                    emptyRecordMsg="No options available. Add new one"
                    onSelect={onSelectUser}
                    onRemove={onRemoveUser}
                  />
                </div>
                <Divider />
                <Row>
                  <Col span={20}>
                    <p className={styles.contentTitle}>Labels</p>
                  </Col>
                  <Col span={4}>
                    <i
                      style={{ cursor: "pointer" }}
                      onClick={toggleFormLabel}
                      className="fa fa-angle-double-down fa-2x text-secondary"
                    ></i>
                  </Col>
                </Row>
                <div>
                  {task.labels.map((label, i) => (
                    <span
                      style={{
                        backgroundColor: `#${label.color}`,
                        marginRight: "10px",
                        color: "white",
                        fontSize: "12px",
                        borderRadius: "20px",
                        padding: "5px 15px",
                      }}
                      key={i}
                    >
                      {label.name.charAt(0).toUpperCase() + label.name.slice(1)}
                    </span>
                  ))}
                </div>
                <div
                  style={{ display: showLabel ? "block" : "none" }}
                  className="form-group mt-3"
                >
                  <label htmlFor="exampleFormControlSelect1">Add Labels</label>
                  <Multiselect
                    options={labels}
                    selectedValues={task.labels}
                    displayValue="name"
                    emptyRecordMsg="No options available. Add new one"
                    onSelect={onSelectLabel}
                    onRemove={onRemoveLabel}
                  />
                </div>

                <Divider />
                <Row>
                  <Col span={24}>
                    <button
                      id={task.id}
                      onClick={toggleIssueHandler}
                      type="button"
                      className={`btn btn-outline-${
                        task.state === "open" ? "danger" : "primary"
                      } w-100`}
                    >
                      {task.state === "open" ? "Close issue" : "Reopen issue"}
                    </button>
                  </Col>
                </Row>
              </Drawer>

              <p className={styles.cardText}>
                #{id} opened by {task.openedBy}
              </p>

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
            <div className="col-3">
              <Menu
                menuButton={
                  <MenuButton
                    styles={{
                      border: "none",
                      boxShadow: "none",
                    }}
                  >
                    ...
                  </MenuButton>
                }
              >
                <MenuItem value="Delete">
                  <button
                    style={{
                      border: "none",
                      width: "100%",
                      backgroundColor: "transparent",
                    }}
                    id={task.id}
                    onClick={deleteHandler}
                  >
                    Delete
                  </button>
                </MenuItem>

                <MenuItem value="Edit">
                  <button
                    style={{
                      border: "none",
                      width: "100%",
                      backgroundColor: "transparent",
                    }}
                    id={task.id}
                    onClick={editHandler}
                  >
                    Edit
                  </button>
                </MenuItem>
              </Menu>
              <div
                style={{
                  display: "flex",
                  flexWrap: "wrap",
                }}
              >
                {task.users
                  ? task.users.map((item) => (
                      <span
                        id={item.id}
                        role="button"
                        onClick={showAssignedUser}
                        style={{
                          backgroundColor: "lightskyblue",
                          padding: "2px 5px",
                          fontSize: "10px",
                          margin: "2px",
                          color: "white",
                          borderRadius: "10px",
                          fontWeight: "bold",
                        }}
                        key={item.id}
                      >
                        {item.username.charAt(0).toUpperCase()}
                      </span>
                    ))
                  : "no user assigned"}
              </div>
            </div>
          </div>
        </div>
      )}
    </Draggable>
  );
}

export default Task;
