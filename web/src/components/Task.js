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
  const [userList, setUserList] = useState([]);
  const [nameList, setNameList] = useState([]);

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
        console.log(res.data, 29);
        setUserList(res.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const onSelect = (data) => {
    const ids = data.map((item) => item.id);
    Axios.put(`/issue/${id}/assignee`, ids)
      .then((res) => {
        setNameList(res.data.users);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const onRemove = (data) => {
    const ids = data.map((item) => item.id);

    Axios.put(`/issue/${id}/assignee`, ids)
      .then((res) => {
        setNameList(res.data.users);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const toggleForm = (event) => {
    event.preventDefault();
    setShow(!show);
  };

  const namelistUser =
    nameList.length < 1
      ? ""
      : nameList.map((item) => <li key={item.id}>{item.username}</li>);

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
              <button
                className={styles.cardTitle}
                type="primary"
                onClick={showDrawer}
              >
                {task.title}
              </button>
              <Drawer
                width={440}
                placement="right"
                closable={false}
                onClose={onClose}
                visible={visible}
              >
                <h3 className="my-3">{task.title}</h3>
                <p className="">
                  #{id} opened by {task.description}
                </p>
                <p>
                  <span className={styles.descriptionDrawer}>
                    Description :
                  </span>
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
                  <ul>{namelistUser}</ul>
                </div>
                <div
                  style={{ display: show ? "block" : "none" }}
                  className="form-group mt-3"
                >
                  <label htmlFor="exampleFormControlSelect1">Assign User</label>
                  <Multiselect
                    options={userList}
                    displayValue="username"
                    emptyRecordMsg="No options available. Add new one"
                    onSelect={onSelect}
                    onRemove={onRemove}
                  />
                </div>
                <Divider />
              </Drawer>

              <p className={styles.cardText}>
                #{id} opened by {task.description}
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
                <MenuItem
                  styles={{ borderBottom: "2px solid gray" }}
                  value="setclose"
                >
                  <button
                    style={{
                      border: "none",
                      width: "100%",
                      backgroundColor: "transparent",
                    }}
                    id={task.id}
                  >
                    Close Issue
                  </button>
                </MenuItem>
                <MenuItem value="Close">Close Window</MenuItem>
              </Menu>
            </div>
          </div>
        </div>
      )}
    </Draggable>
  );
}

export default Task;
