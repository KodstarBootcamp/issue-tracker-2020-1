import React, { useState, useEffect } from "react";
import Axios from "axios";
import AllIssues from "./AllIssues";
import "../pages/Home.css";
import "../../src/components/AllIssues.css";

function DisplayAllIssues() {
  const [issues, setIssues] = useState([]);

  useEffect(() => {
    fetchIssues();
  }, []);

  const fetchIssues = async () => {
    const response = await Axios.get("/issues");
    console.log(response, "DisplayAllIssues.js");
    if (response.data.length < 1) {
      setIssues(null);
    } else {
      setIssues(response.data);
    }
  };

  return <div>
    
    
    <div className="big-box-1"><AllIssues issues={issues} /></div>
    
  

  </div>
}

export default DisplayAllIssues;
