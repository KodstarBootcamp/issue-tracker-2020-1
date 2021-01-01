import React from "react";
import axios from "axios";
import { Link, useHistory } from "react-router-dom";
import "../../src/components/DisplayIssues.css";
import "../../src/components/AllIssues.css";

export default function AllIssues({ issues }) {
  let history = useHistory();
  
  // mapping around all the issues to display one by one
  const Display = !issues
    ? history.push("/createIssue")
    : issues.map((item) => (
        <div key={item.id} className="card-1 mt-3" /* style={{ width: "18rem" }} */>
          
            <div className="checkbox">
              <input type="checkbox" /* onChange={this.handleCheck} defaultChecked={this.state.checked} *//>
            </div>
            <h5 className="card-1-title">{item.title}</h5>
            {/* <p className="card-text">{item.description}</p> */}
            <ul>
              {item.labels.map((label, i) => (
                <li key={i}>
                  {label.charAt(0).toUpperCase() + label.slice(1)}
                </li>
              ))}
            </ul>
            
        
        </div>
      ));

  return (
    <h1>
      <div className="d-flex flex-column flex-wrap justify-content-around">
        {Display}
      </div>

      <Link to="/createIssue">
        <button style={{ position: "fixed", top: "35px", right: "35px" }}>
          Create New Issue
        </button>
      </Link>
    </h1>
  );
}
