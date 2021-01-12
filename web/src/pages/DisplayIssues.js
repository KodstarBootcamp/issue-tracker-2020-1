import React, { useContext, useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { IssueContex } from "../App";
import styles from "./DisplayIssues.module.css";
import Axios from "axios";
import Loader from "react-loader-spinner";

export default function AllIssues() {
  const pathname = window.location.pathname;
  const idArray = pathname.split("/");
  const id = idArray[idArray.length - 1];

  const [isCheck, setCheck] = useState(false);
  const [search, setSearch] = useState("");
  const [option, setoption] = useState("");
  const [multipleDeleteIds, setmultipleDeleteIds] = useState([]);
  const [issues, setIssues] = useState([]);

  useEffect(() => {
    function fetchIssues() {
      Axios.get(`/project/${id}/issues`)
        .then((res) => {
          console.log(res.data);
          setIssues(res.data);
        })
        .catch((error) => {
          console.log(error);
        });
    }

    fetchIssues();
  }, [id]);

  const { deleteHandler } = useContext(IssueContex);
  const { editHandler } = useContext(IssueContex);

  useEffect(() => {
    async function fetchData() {
      if (option !== "" && search.length > 6) {
        const field = search.split(":")[0];
        const key = search.split(":")[1];
        const URL = `/project/${id}/issues/search?field=${field}&key=${key}&sort=${option}`;
        const response = await Axios.get(URL);
        if (response.data.length > 0) {
          setIssues(response.data);
        }
      } else if (option === "" && search.length > 6) {
        console.log("hey2");
        const field = search.split(":")[0];
        const key = search.split(":")[1];
        const URL = `/project/${id}/issues/search?field=${field}&key=${key}`;
        const response = await Axios.get(URL);
        console.log(response.data);
        if (response.data.length > 0) {
          setIssues(response.data);
        }
      } else if (option !== "" && search.length < 1) {
        console.log("hey3");

        const URL = `/project/${id}/issues/search?sort=${option}`;
        const response = await Axios.get(URL);
        setIssues(response.data);
      }
    }
    fetchData();
  }, [option, search, id]);

  const optionHandler = (event) => {
    setoption(event.target.value);
  };

  const searchHandler = (event) => {
    const query = event.target.value;
    setSearch(query.trim().toLowerCase());
  };

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
        <Link to={`/projects/${id}`}>
          <button className="btn btn-outline-secondary btn-sm">
            Back Home
          </button>
        </Link>
      </div>
      <div className={styles.container}>
        <div className={styles.navbar}>
          <Link to={`/createIssue/${id}`}>
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
          <select
            style={{ width: "150px" }}
            value={option}
            onChange={optionHandler}
            className="form-control "
          >
            <option value="">Sort By</option>
            <option value="newest">Newest</option>
            <option value="oldest">Oldest</option>
            <option value="recent">Recent Updated</option>
            <option value="latest">Latest Updated</option>
          </select>
          <form className={styles.searchArea}>
            <input
              style={{ width: "250px" }}
              value={search}
              onChange={searchHandler}
              className="form-control ml-3"
              type="text"
              placeholder="title:keyword ,description:keyword"
              aria-label="Search"
            />
          </form>
        </div>
        <div>{Display}</div>
      </div>
    </div>
  );
}
