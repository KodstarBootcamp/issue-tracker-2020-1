import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.css";
import { Link } from "react-router-dom";

const Register = () => {
  const [values, setValues] = useState({
    username: "",
    email: "",
    password: "",
    password2: "",
  });

  const [errors, setErrors] = useState({});

  const handleChange = (event) => {
    const { name, value } = event.target;
    setValues({
      ...values,
      [name]: value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    setErrors(validateInfo(values));
  };

  function validateInfo(values) {
    let errors = {};

    if (!values.username.trim()) {
      errors.username = "Username required*";
    }

    if (!values.email) {
      errors.email = "Email is required*";
    } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(values.email)) {
      errors.email = "Email adress is invalid*";
    }

    if (!values.password) {
      errors.password = "Password is required*";
    } else if (values.password.length < 6) {
      errors.password = "Password needs to be 6 characters or more*";
    }

    if (!values.password2) {
      errors.password2 = "Password is required*";
    } else if (values.password2 !== values.password) {
      errors.password2 = "Password do not match*";
    }

    return errors;
  }

  return (
    <div>
      <div className="container h-100">
        <div className="row h-100 justify-content-center align-items-center">
          <div className="col-10 col-md-8 col-lg-6">
            <form className="form-example" action="" method="post">
              <h2 style={{ marginTop: 40 }}>Register</h2>
              <hr></hr>

              <div className="form-group">
                <label htmlFor="username">Username</label>
                <input
                  type="text"
                  className="form-control username"
                  id="username"
                  placeholder="Enter your username"
                  name="username"
                  value={values.username}
                  onChange={handleChange}
                />
                {errors.username && (
                  <p style={{ fontSize: 14, color: "red", paddingLeft: 14 }}>
                    <i>{errors.username}</i>
                  </p>
                )}
              </div>

              <div className="form-group">
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
                  type="password"
                  className="form-control password"
                  id="password"
                  placeholder="Enter your password"
                  name="password"
                  value={values.password}
                  onChange={handleChange}
                />
                {errors.password && (
                  <p style={{ fontSize: 14, color: "red", paddingLeft: 14 }}>
                    <i>{errors.password}</i>
                  </p>
                )}
              </div>
              <div className="form-group">
                <label htmlFor="password2">Confirm password</label>
                <input
                  type="password"
                  className="form-control password2"
                  id="password2"
                  placeholder="Confirm your password"
                  name="password2"
                  value={values.password2}
                  onChange={handleChange}
                />
                {errors.password2 && (
                  <p style={{ fontSize: 14, color: "red", paddingLeft: 14 }}>
                    <i>{errors.password2}</i>
                  </p>
                )}
              </div>
              <div>
                <button
                  type="submit"
                  className="btn btn-primary btn-customized"
                  onClick={handleSubmit}
                >
                  Register
                </button>
                <hr></hr>

                <p
                  style={{
                    position: "absolute",
                    bottom: 8,
                    right: 16,
                  }}
                >
                  Have you already an account ? <Link to="/"> Login </Link>
                </p>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Register;
