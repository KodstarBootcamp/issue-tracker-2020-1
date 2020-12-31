import React, { useState, useEffect } from "react";
import Axios from "axios";
import DisplayIssues from "../components/DisplayIssues";
import "../pages/Home.css";

function Home() {
  const [issues, setIssues] = useState([]);

  useEffect(() => {
    fetchIssues();
  }, []);

  const fetchIssues = async () => {
    const response = await Axios.get("/issues");
    console.log(response, "Home.js");
    if (response.data.length < 1) {
      setIssues(null);
    } else {
      setIssues(response.data);
    }
  };

  return <div>
    <div className="header"><h1 >IssueTracker-01</h1></div>
    
    <div className="big-box"><h1>Backlog</h1><DisplayIssues issues={issues} /></div>
    <div className="big-box"><h1>To do</h1><DisplayIssues issues={issues} /></div>
    <div className="big-box"><h1>In progress</h1></div>
    <div className="big-box"><h1>Review in progress</h1></div>
    <div className="big-box"><h1>Done</h1></div>
  

  </div>
}

export default Home;
