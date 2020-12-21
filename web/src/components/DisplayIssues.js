import React from "react";
import IconButton from "@material-ui/core/IconButton";
import EditIcon from "@material-ui/icons/Edit";
import DeleteIcon from "@material-ui/icons/Delete";
export default function DisplayIssues({ issues }) {
  const editHandler = () => {
    console.log("issue must be edited");
  };

  const deleteHandler = () => {
    console.log("issue must be deleted");
  };

  const Display = issues.map((item) => (
    <div key={item.id}>
      <h1>{item.title}</h1>
      <div>
        <span>{item.description}</span>
        <IconButton aria-label="EDIT" onClick={editHandler}>
          <EditIcon />
        </IconButton>
        <IconButton aria-label="DELETE" onClick={deleteHandler}>
          <DeleteIcon />
        </IconButton>
      </div>
      <ul>
        {item.labels.map((label) => (
          <li>{label}</li>
        ))}
      </ul>
    </div>
  ));

  return <div>{Display}</div>;
}
