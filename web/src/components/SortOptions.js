import React, { useState } from "react";

function SortOptions() {
  const [option, setoption] = useState();
  const optionHandler = (event) => {
    setoption(event.target.value);
  };

  return (
    <select
      style={{ width: "150px" }}
      value={option}
      onChange={optionHandler}
      className="form-control "
    >
      <option hidden>Sort By</option>
      <option value="newest">Newest</option>
      <option value="oldest">Oldest</option>
      <option value="recent">Recent Updated</option>
      <option value="latest">Latest Updated</option>
    </select>
  );
}

export default SortOptions;
