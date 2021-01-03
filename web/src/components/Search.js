import React, { useState } from "react";

function Search() {
  const [search, setSearch] = useState("");

  const searchHandler = (event) => {
    const query = event.target.value;
    setSearch(query.trim().toLowerCase());
  };
  return (
    <form className="form-inline">
      <input
        style={{ width: "250px" }}
        value={search}
        onChange={searchHandler}
        className="form-control ml-3"
        type="text"
        placeholder="Search"
        aria-label="Search"
      />
      <button className="btn btn-outline-success btn-sm ml-2 " type="submit">
        Search
      </button>
    </form>
  );
}

export default Search;
