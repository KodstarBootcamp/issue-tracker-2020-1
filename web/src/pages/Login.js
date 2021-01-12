import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.css";
import { Link, useHistory } from "react-router-dom";
import Axios from "axios";

const Login = () => {
  const [values, setValues] = useState({
    username: "",
    email: "",
    password: "",
  });

  let history = useHistory();

  const [errors, setErrors] = useState({});
  const [showPassword, setshowPassword] = useState(false);

  const toggleShowPassword = (event) => {
    event.preventDefault();
    setshowPassword(!showPassword);
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    setValues({
      ...values,
      [name]: value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    setErrors(validate(values));
    const loginUser = {
      username: values.email,
      password: values.password,
    };

    Axios.post("/auth/login", loginUser)
      .then((res) => {
        const token = res.data.token;
        localStorage.setItem("token", token);
        history.push("/projects");
      })
      .catch((err) => {
        console.log(err.response.status, 35);
        if (err.response.status === 400) {
          alert("Something went wrong. Check your info");
        } else if (err.response.status === 409) {
          alert("");
        }
      });
  };

  function validate(values) {
    let errors = {};

    if (!values.email) {
      errors.email = "Email is required*";
    } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(values.email)) {
      errors.email = "Email adress is invalid*";
    }

    if (!values.password) {
      errors.password = "Password is required*";
    } else if (values.password.length < 8) {
      errors.password = "Password needs to be 8 characters or more*";
    }
    return errors;
  }

  return (
    <div className="container h-100">
      <div className="row h-100 justify-content-center align-items-center">
        <div className="col-10 col-md-8 col-lg-6">
          <form className="form-example" action="" method="post">
            <h2 style={{ marginTop: 50 }}>Welcome to Issue Tracker</h2>

            <div className="form-group mt-5">
              <label htmlFor="email">Email</label>
              <input
                type="email"
                className="form-control email"
                id="email"
                placeholder="Enter your email"
                name="email"
                value={values.email}
                onChange={handleChange}
              />
              {errors.email && (
                <p style={{ fontSize: 14, color: "red", paddingLeft: 14 }}>
                  <i>{errors.email}</i>
                </p>
              )}
            </div>
            <div className="form-group">
              <label htmlFor="password">Password</label>
              <input
                type={showPassword ? "text" : "password"}
                className="form-control password"
                id="password"
                placeholder="Enter your password"
                name="password"
                value={values.password}
                onChange={handleChange}
              />
              <button
                onClick={toggleShowPassword}
                style={{
                  border: "none",
                  backgroundColor: "transparent",
                  fontSize: "10px",
                  color: "gray",
                  fontWeight: 600,
                }}
              >
                {showPassword ? "Hide" : "Show"} Password
              </button>
              {errors.password && (
                <p style={{ fontSize: 14, color: "red", paddingLeft: 14 }}>
                  <i>{errors.password}</i>
                </p>
              )}
            </div>
            <div>
              <button
                type="submit"
                className="btn btn-primary btn-customized"
                onClick={handleSubmit}
              >
                Login
              </button>
              <hr></hr>

              <p
                style={{
                  position: "absolute",
                  bottom: 8,
                  right: 16,
                }}
              >
                New to Issue Tracker ?<Link to="/register"> Register</Link>
              </p>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};
export default Login;
