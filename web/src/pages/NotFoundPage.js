import React from 'react';
import { Link } from "react-router-dom";


 const NotFoundPage = ({ location }) => (
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="error-template" style={{padding: 40,marginTop:100,textAlign: "center" }}>
                   <h1>Oops!</h1>
                   <h2>404 Not Found</h2>
                   <div class="error-details">
                      Sorry, an error has occured, Requested page not found!
                    </div>
                    <div class="error-actions" style= {{marginTop:15, marginBottom:15}}>
                       <Link to="/projects" class="btn btn-primary btn-lg" style={{marginLeft:15}}>
                        Go Back to Home </Link>
                    </div>
                </div>
            </div>
        </div>
    </div>

  );

  export default NotFoundPage;