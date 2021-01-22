import React from "react";
import moment from "moment-timezone";
const History = ({ data }) => {
  const caseHandler = (item) => {
    if (item.action === "added") {
      if (item.field === "comment") {
        return (
          <span className="">
            <span style={{ color: "black", fontWeight: "bold", margin: "5px" }}>
              {item.action}
            </span>
            a comment this issue {moment(item.createdAt + "Z").fromNow()}{" "}
            <p
              style={{
                padding: "10px",
                backgroundColor: "#F6F8FA",
                margin: "5px",
              }}
            >
              {item.newValue}
            </p>{" "}
          </span>
        );
      } else {
        return (
          <span className="">
            <span style={{ color: "black", fontWeight: "bold", margin: "5px" }}>
              {item.action}
            </span>
            this issue {moment(item.createdAt + "Z").fromNow()}
          </span>
        );
      }
    } else if (item.action === "assigned") {
      return (
        <span className="">
          <span style={{ color: "black", fontWeight: "bold", margin: "5px" }}>
            {item.action}
          </span>
          this issue to{" "}
          {item.newValue.map((el) => (
            <span
              key={el.id}
              style={{ color: "black", fontWeight: "bold", margin: "5px" }}
            >
              {el.username}{" "}
              {el === item.newValue[item.newValue.length - 1] ? "" : "and"}
            </span>
          ))}
          {moment(item.createdAt + "Z").fromNow()}
        </span>
      );
    } else if (item.action === "moved") {
      return (
        <span className="">
          <span style={{ color: "black", fontWeight: "bold", margin: "5px" }}>
            {item.action}
          </span>
          this issue from
          <span style={{ color: "black", fontWeight: "bold", margin: "5px" }}>
            {item.oldValue}
          </span>
          to
          <span style={{ color: "black", fontWeight: "bold", margin: "5px" }}>
            {item.newValue}
          </span>
          {moment(item.createdAt + "Z").fromNow()}
        </span>
      );
    } else if (item.action === "updated") {
      if (item.field === "labels") {
        return (
          <span className="">
            <span style={{ color: "black", fontWeight: "bold", margin: "5px" }}>
              {item.action}
            </span>{" "}
            labels
            <span className="mx-1">
              to
              {item.newValue.length > 1 ? (
                item.newValue.map((el) => (
                  <span
                    key={el.id}
                    style={{
                      backgroundColor: `#${el.color}`,
                      color: "white",
                      padding: "5px 8px",
                      borderRadius: "10px",
                      margin: "0px 5px",
                    }}
                  >
                    {el.name}
                  </span>
                ))
              ) : (
                <span
                  style={{
                    backgroundColor: `#${item.newValue[0].color}`,
                    color: "white",
                    padding: "5px 8px",
                    borderRadius: "10px",
                    margin: "0px 5px",
                  }}
                >
                  {item.newValue[0].name}
                </span>
              )}
            </span>
            {moment(item.createdAt + "Z").fromNow()}
          </span>
        );
      } else if (item.field === "state") {
        return (
          <>
            <span style={{ color: "black", fontWeight: "bold", margin: "5px" }}>
              {item.action}
            </span>
            <span>
              state from{" "}
              <span
                style={{ color: "black", fontWeight: "bold", margin: "5px" }}
              >
                {item.oldValue}
              </span>{" "}
              to
              <span
                style={{ color: "black", fontWeight: "bold", margin: "5px" }}
              >
                {item.newValue}
              </span>{" "}
            </span>
          </>
        );
      }
    }
  };

  return (
    <>
      {data.map((item) => (
        <div
          key={item.createdAt + "Z"}
          style={{ borderBottom: "1px solid gray" }}
          className="p-3"
        >
          <i className="fa fa-bell"></i>
          <span className="mx-2">{item.subject}</span>
          {caseHandler(item)}
        </div>
      ))}
    </>
  );
};

export default History;
