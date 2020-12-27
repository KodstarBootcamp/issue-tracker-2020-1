import React, { useState, useEffect } from "react";
import Axios from "axios";
import DisplayIssues from "../components/DisplayIssues";

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

  return <DisplayIssues issues={issues} />;
}

export default Home;
