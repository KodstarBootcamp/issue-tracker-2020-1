import React, { useState, useEffect } from "react";
import Axios from "axios";
import DisplayIssues from "../components/DisplayIssues";

function Home() {
  const [issues, setIssues] = useState([]);

  useEffect(() => {
    fetchIssues();
  }, []);

  const URL = "http://localhost:5000/issues";

  const fetchIssues = async () => {
    const response = await Axios.get(URL);

    setIssues(response.data);
  };

  return <DisplayIssues issues={issues} />;
}

export default Home;
