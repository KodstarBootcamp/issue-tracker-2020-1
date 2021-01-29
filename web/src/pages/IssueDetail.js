import React, { useState, useEffect } from "react";
import Axios from "axios";
import { Modal } from "antd";
import History from "../components/History";
import axios from "axios";

function IssueDetail() {
  const pathname = window.location.pathname;
  const idArray = pathname.split("/");
  const issueId = idArray[idArray.length - 1];

  const [allComments, setAllComment] = useState([]);
  const [issue, setIssue] = useState([]);
  const [data, setData] = useState([]);
  const [comment, setcomment] = useState("");
  const [refresh, setRefresh] = useState(true);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [editComment, setEditComment] = useState();
  const [content, setContent] = useState();
  const [commentId, setcommentId] = useState();

  const commentHandler = (event) => {
    setcomment(event.target.value);
  };
  const commentEditHandler = (event) => {
    setContent(event.target.value);
  };

  const showModal = (event) => {
    setcommentId(event.target.id);
    setIsModalVisible(true);
  };

  const handleOk = (event) => {
    event.preventDefault();
    delete editComment.id;
    const uptadedComment = {
      ...editComment,
      content: content,
    };
    Axios.put(`comment/${commentId}`, uptadedComment).then((res) => {
      console.log(res.data);
      alert("succesfully edited");
      setRefresh(!refresh);
    });

    setIsModalVisible(false);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
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

  //get single comment
  useEffect(() => {
    if (commentId) {
      Axios.get(`comment/${commentId}`)
        .then((res) => {
          console.log(res.data, 57);
          setEditComment(res.data);
          setContent(res.data.content);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, [commentId]);

  useEffect(() => {
    Axios.get(`/issue/${issueId}/comments`)
      .then((res) => {
        console.log(res.data, 35);
        setAllComment(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [issueId, refresh]);

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
  }, [issueId, refresh]);

  const saveCommentHandler = (event) => {
    event.preventDefault();
    const newCommet = {
      content: comment.trim(),
      issueId: issueId,
    };

    Axios.post(`/comment`, newCommet)
      .then((res) => {
        console.log(res.data, "new");
        alert("succesfully added");
        setRefresh(!refresh);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const deleteCommentHandler = (event) => {
    const commentId = event.target.id;

    Axios.delete(`comment/${commentId}`).then((res) => {
      console.log(res.data);
      alert("succesfully deleted");
      setRefresh(!refresh);
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

      <div className="mt-3">
        {allComments.length > 0
          ? allComments.map((item) => (
              <div
                style={{
                  padding: "10px",
                  border: "1px solid lightgray",
                  borderRadius: "10px",
                  display: "flex",
                }}
              >
                <div className="w-75">
                  <div>
                    <p style={{ color: "gray", textDecoration: "underline" }}>
                      {item.commentedBy}
                    </p>
                    <p>{item.content}</p>
                  </div>
                </div>
                <div className=" w-25 justify-content-center d-flex">
                  <button
                    onClick={deleteCommentHandler}
                    id={item.id}
                    style={{ border: "none", backgroundColor: "transparent" }}
                    type="button btn-xl"
                    className="text-secondary"
                  >
                    Delete
                  </button>
                  <button
                    id={item.id}
                    style={{ border: "none", backgroundColor: "transparent" }}
                    type="button btn-xl"
                    className="text-secondary"
                    onClick={showModal}
                  >
                    Edit
                  </button>
                  <Modal
                    title="Edit Comment"
                    visible={isModalVisible}
                    onOk={handleOk}
                    onCancel={handleCancel}
                  >
                    <textarea
                      value={content}
                      onChange={commentEditHandler}
                      className="form-control"
                      rows="5"
                      id="comment"
                    ></textarea>
                  </Modal>
                </div>
              </div>
            ))
          : ""}
      </div>
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
      <div className="d-flex justify-content-between">
        <button
          onClick={saveCommentHandler}
          type="button"
          className="btn btn-success"
        >
          Save
        </button>
        <button
          onClick={() => {
            window.history.go(-1);
          }}
          style={{ border: "none" }}
        >
          Go previous page
        </button>
      </div>
    </div>
  );
}

export default IssueDetail;
