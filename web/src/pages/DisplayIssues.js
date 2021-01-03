import React, { useContext, useState } from "react";
import { Link } from "react-router-dom";
import { IssueContex } from "../App";
import Search from "../components/Search";
import SortOptions from "../components/SortOptions";
import styles from "./DisplayIssues.module.css";
import Axios from "axios";
import Loader from "react-loader-spinner";

export default function AllIssues() {
  const [isCheck, setCheck] = useState(false);
  const [multipleDeleteIds, setmultipleDeleteIds] = useState([]);

  const { issues } = useContext(IssueContex);
  const { deleteHandler } = useContext(IssueContex);
  const { editHandler } = useContext(IssueContex);

  const CheckHandler = (event) => {
    const { checked, value } = event.target;

    if (checked) {
      setmultipleDeleteIds((prevState) => {
        return [...prevState, Number(value)];
      });
    } else {
      const deleted = multipleDeleteIds.filter(
        (item) => item !== Number(value)
      );
      setmultipleDeleteIds(deleted);
    }

    setCheck(!isCheck);
  };

  // mapping around all the issues to display one by one
  const Display = !issues ? (
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
    issues.map((item) => (
      <div key={item.id} className={styles.issueCard}>
        <div className="form-group form-check mr-5">
          <input
            value={item.id}
            checked={item.isCheck}
            onChange={CheckHandler}
            className="form-check-input"
            type="checkbox"
          />
        </div>
        <p className={styles.issueTitle}>{item.title}</p>
        <div>
          {item.labels.map((label, i) => (
            <span
              style={{ backgroundColor: `#${label.color}` }}
              className={styles.labelContainer}
              key={i}
            >
              {label.name.charAt(0).toUpperCase() + label.name.slice(1)}
            </span>
          ))}
        </div>
        <div className="ml-auto">
          <button
            id={item.id}
            onClick={editHandler}
            type="button"
            className="btn btn-secondary btn-sm mr-2"
          >
            Edit
          </button>
          <button
            id={item.id}
            onClick={deleteHandler}
            type="button"
            className="btn btn-danger btn-sm"
          >
            Delete
          </button>
        </div>
      </div>
    ))
  );

  const multipleDeleteHandler = async () => {
    const data = {
      method: "delete",
      ids: multipleDeleteIds,
    };
    const response = await Axios.post("/issues/batch", data);
    console.log(response);
  };

  return (
    <div>
      <div className="d-flex mt-5 justify-content-center">
        <Link to="/">
          <button className="btn btn-outline-secondary btn-sm">
            Back Home
          </button>
        </Link>
      </div>
      <div className={styles.container}>
        <div className={styles.navbar}>
          <Link to="/createIssue">
            <button className="btn btn-outline-success btn-sm">
              Create New Issue
            </button>
          </Link>
          <Link to="/allIssues">
            <button
              style={{
                display: multipleDeleteIds.length > 1 ? "block" : "none",
              }}
              onClick={multipleDeleteHandler}
              className="btn btn-outline-danger btn-sm"
            >
              Delete All
            </button>
          </Link>
          <SortOptions />
          <Search />
        </div>
        <div>{Display}</div>
      </div>
    </div>
  );
}
