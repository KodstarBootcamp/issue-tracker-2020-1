import React, {useState} from "react";
import { Link} from "react-router-dom";
import "./CommentPage.css";
import "bootstrap/dist/css/bootstrap.css";



export default function Comment({ issues }) {
   const [state, setstate] = useState(issues);


    return (
        <div className="Comment-page">
          <nav className="navbar navbar-inverse">
            <div className="container-fluid">
              <div className="navbar-header">
                <a className="navbar-brand" href="#">KodstarBootcamp/Issue-tracker-2020-1</a>
              </div>
              <ul className="nav navbar-nav">
                <li className="active"><a href="#">Projects</a></li>
              </ul>
            </div>
          </nav>
          <body>
            <div className="panel panel-default">
              <div className="panel-heading">#Visual Board representation</div>
              <div className="panel-body">Panel Content</div>
            </div>
            <div className="row">
              <div className="col-xs-9">history</div>  
            </div>
         
            <form className="w-75 ml-auto mr-auto mt-5">
              <div className="form-group mt-5">
                <label htmlFor="exampleFormControlTextarea1"> Write </label>
                <textarea
                  value={"Leave a comment"}
                  placeholder="Leave a comment"
                  className="form-control"
                  id="Textarea1"
                  rows="3"
                  ></textarea>
             </div>
   
             <div className="d-flex mt-5 justify-content-between">
              <button className="btn btn-primary">
                 Comment
              </button>
              <Link to="/">
                <p style={{ textDecoration: "underline" }}>Back to Home Page</p>
              </Link>
             </div>
            </form>
          </body>
        </div>
        
      );
    }