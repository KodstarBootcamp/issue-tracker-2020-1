import React from "react";
import axios from 'axios';
import IconButton from "@material-ui/core/IconButton";
import EditIcon from "@material-ui/icons/Edit";
import DeleteIcon from "@material-ui/icons/Delete";

export default function DisplayIssues({ issues }) {

 
  const editHandler = () => {
    console.log("issue must be edited");
  };


  const deleteHandler =(event) => {
    
    const id = event.target.id ;
    console.log(id);
    const URL = "http://localhost:5000/issues"; 

       axios.delete(`http://localhost:5000/issues/${id}`)
         .then(res => {
           console.log(res);
           console.log(res.data);
         })
       window.location.reload();

  };

  const Display = issues.map((item) => (

    <div key={item.id}>
      <h1>{item.title}</h1>
      <div >
        <span>{item.description}</span>
        <IconButton aria-label="EDIT" onClick={editHandler}>
          <EditIcon />
        </IconButton>
        <IconButton aria-label="DELETE" onClick={deleteHandler}>
          <DeleteIcon id={item.id} />
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
