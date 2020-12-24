import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.css";
import { Multiselect } from "multiselect-react-dropdown";
import Axios from "axios";




function CreateIssue() {
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [labels, setLabels] = useState([]);
    const [options] = useState([
        { Label: "Bug" },
        { Label: "Enhancement" },
        { Label: "Question" },
        { Label: "Suggestion" },
        { Label: "Critical" },
    ]);

    const titleHandler = (event) => {
        setTitle(event.target.value);
    };

    const descriptionHandler = (event) => {
        setDescription(event.target.value);
    };

    const submitHandler = async (event) => {
        event.preventDefault();
        const labelText = labels.data.map((item) => item.Label);
        const newIssue = {
            title: title,
            description: description,
            labels: labelText,
        };

        const URL = "http://localhost:5000/issues";

        const response = await Axios.post(URL, newIssue);





        console.log(response);
    };

    return (
        <form className="w-75 ml-auto mr-auto mt-5"
            onSubmit={submitHandler} >
            <div className="form-group" >
                <label htmlFor="exampleFormControlInput1" > Title </label> <
                    input value={title}
                    onChange={titleHandler}
                    type="text"
                    className="form-control"
                    id="exampleFormControlInput1"
                    placeholder="Please add issue title" /
                >
            </div>
            <div className="form-group" >
                <label htmlFor="exampleFormControlTextarea1" > Issue Description </label>
                <textarea value={description}
                    placeholder="Add descriptive explanation"
                    onChange={descriptionHandler}
                    className="form-control"
                    id="exampleFormControlTextarea1"
                    rows="3" >
                </textarea>
            </div >
            <div className="form-group" >
                <label htmlFor="exampleFormControlSelect1" > Label selection </label> <
                    Multiselect options={options}
                    displayValue="Label"
                    onSelect={
                        (data) => setLabels({ ...data, data })
                    }
                    onRemove={
                        (data) => setLabels({ ...data, data })
                    } />
            </div>
            <button type="submit" class="btn btn-primary" >
                Submit </button>
        </form>
    );
}

export default CreateIssue; 