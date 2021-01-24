import React, { useContext, useState, useEffect } from "react";
import { Link, useHistory } from "react-router-dom";
import { IssueContex } from "../App";
import styles from "./DisplayIssues.module.css";
import Axios from "axios";
import Loader from "react-loader-spinner";

export default function AllIssues() {
  let history = useHistory();
  const pathname = window.location.pathname;
  const idArray = pathname.split("/");
  const id = idArray[idArray.length - 1];

  const [isCheck, setCheck] = useState(false);
  const [open, setOpen] = useState("open");
  const [search, setSearch] = useState("");
  const [option, setoption] = useState("");
  const [multipleDeleteIds, setmultipleDeleteIds] = useState([]);
  const [issues, setIssues] = useState([]);
  const [CurrentPageNumber, setCurrentPageNumber] = useState(0);
  const [TotalPageNumber, setTotalPageNumber] = useState(0);

  useEffect(() => {
    function fetchIssues() {
      Axios.get(`/project/${id}/display?page=${CurrentPageNumber}`)
        .then((res) => {
          console.log(res.data.totalPages);
          setTotalPageNumber(res.data.totalPages);
          setIssues(res.data.issues);
        })
        .catch((error) => {
          console.log(error);
        });
    }

    fetchIssues();
  }, [id, CurrentPageNumber]);

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

  const OpenCloseHandler = (event) => {
    const id = event.target.id;

    Axios.get("/issue/" + id)
      .then((res) => {
        return res.data;
      })
      .then((data) => {
        delete data.id;
        const UpdatedIssue = {
          ...data,

          state: data.state === "open" ? "closed" : "open",
        };
        Axios.put("/issue/" + id, UpdatedIssue)
          .then((res) => {
            console.log(res.data);
            window.location.reload();
          })
          .catch((error) => {
            console.log(error);
          });
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const redirectCommentPage = (event) => {
    const id = event.target.id;
    history.push(`/issueDetail/${id}`);
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
    issues
      .filter((item) => item.state === open)
      .map((item) => (
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
          <p
            role="button"
            onClick={redirectCommentPage}
            id={item.id}
            className={styles.issueTitle}
          >
            {item.title}
          </p>
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
              onClick={OpenCloseHandler}
              type="button"
              className="btn btn-outline-info btn-sm mr-2"
            >
              {item.state === "open" ? "Close" : "Reopen"}
            </button>
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
  const multipleCloseHandler = async () => {
    const data = {
      method: "close",
      ids: multipleDeleteIds,
    };
    const response = await Axios.post("/issues/batch", data);
    window.location.reload();
    console.log(response);
  };

  const openIssue = (e) => {
    e.preventDefault();
    setOpen("open");
  };
  const closeIssue = (e) => {
    e.preventDefault();
    setOpen("closed");
  };

  let pagesArray = [];
  for (let i = 0; i < TotalPageNumber; i++) {
    pagesArray.push(i);
  }

  const changeThePage = (event) => {
    const id = event.target.id;
    setCurrentPageNumber(id);
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
          <div className={styles.isOpen}>
            <span onClick={openIssue} role="button" className="mx-3">
              {issues.filter((item) => item.state === "open").length} Open
            </span>
            <span onClick={closeIssue} role="button" className="text-secondary">
              {issues.filter((item) => item.state === "closed").length} Close
            </span>
          </div>
          <div className={styles.optionBar}>
            <Link to={`/createIssue/${id}`}>
              <button className="btn btn-outline-success btn-sm mx-2">
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
            <button
              style={{
                display: multipleDeleteIds.length > 1 ? "block" : "none",
              }}
              onClick={multipleCloseHandler}
              className="btn btn-outline-danger btn-sm mx-2"
            >
              Close All
            </button>
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
        </div>
        <div>{Display}</div>
        <div className={styles.pagination}>
          {pagesArray.map((item) => (
            <button
              onClick={changeThePage}
              style={{
                border:
                  Number(item) === Number(CurrentPageNumber)
                    ? "2px solid rgb(40,176,198)"
                    : "none",
              }}
              id={item}
              key={item}
              type="button"
              className="btn btn-outline-info m-1 btn-sm"
            >
              {item}
            </button>
          ))}
        </div>
      </div>
    </div>
  );
}
