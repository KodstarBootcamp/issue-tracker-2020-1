import React, { useState, useEffect } from "react";
import Axios from "axios";

import History from "../components/History";

function IssueDetail() {
  const pathname = window.location.pathname;
  const idArray = pathname.split("/");
  const issueId = idArray[idArray.length - 1];

  const [allComments, setAllComment] = useState([]);
  const [issue, setIssue] = useState([]);
  const [data, setData] = useState([]);
  const [comment, setcomment] = useState("");

  const commentHandler = (event) => {
    setcomment(event.target.value);
  };

  //get single issue
  useEffect(() => {
    Axios.get(`issue/${issueId}`)
      .then((res) => {
        console.log(res.data, 23);
        setIssue(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [issueId]);

  useEffect(() => {
    Axios.get(`/issue/${issueId}/comments`)
      .then((res) => {
        console.log(res.data, 35);
        setAllComment(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [issueId]);

  //get history of the issue
  useEffect(() => {
    Axios.get(`issue/${issueId}/history`)
      .then((res) => {
        console.log(res.data);
        setData(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [issueId]);

  const saveCommentHandler = (event) => {
    event.preventDefault();
    const newCommet = {
      content: comment.trim(),
      issueId: issueId,
    };

    Axios.post(`/comment`, newCommet)
      .then((res) => {
        console.log(res.data, "new");
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <div className=" w-75 mx-auto">
      <div className="mb-3">
        <h3 className="mt-5">
          {issue.title} <span className="text-secondary ml-1">#{issueId}</span>
        </h3>
        <span
          style={{
            backgroundColor: issue.state === "open" ? "green" : "red",
            color: "white",
            padding: "5px 8px",
            borderRadius: "10px",
          }}
        >
          {issue.state}
        </span>
        <span
          style={{ marginLeft: "10px", fontWeight: "bold", color: "darkgray" }}
        >
          {issue.openedBy}
        </span>
        <span className="mx-1">opened this issue </span>
      </div>

      <History data={data} />

      <div className="mt-3"></div>
      <div className="form-group my-5 ">
        <label htmlFor="comment">Comment:</label>
        <textarea
          value={comment}
          onChange={commentHandler}
          className="form-control"
          rows="5"
          id="comment"
        ></textarea>
      </div>
      <button
        onClick={saveCommentHandler}
        type="button"
        className="btn btn-success"
      >
        Save
      </button>
    </div>
  );
}

export default IssueDetail;
