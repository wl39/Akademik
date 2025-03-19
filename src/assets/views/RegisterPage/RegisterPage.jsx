import React from "react";
import { Navigate } from "react-router-dom";

import styles from "./RegisterPage.module.css";

import RegisterPath from "../../images/register_path.png";
import registerBackground from "../../images/register_background.png";
import Button from "../../../components/Button/Button";
import TextField from "../../../components/TextField/TextField";
import { userExists, postUsers } from "../../../axios/group20Axios";

import { connect } from "react-redux";

const textField = {
  height: "34px",
  fontSize: "16px",
};

const registerButton = {
  width: "90px",
  height: "40px",
  lineHeight: "40px",
  margin: "40px auto 0px",
  fontSize: "14px",
};

class RegisterPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      group: 4,
      email: "",
      password: "",
      repassword: "",
      validEmail: false,
      validPassword: false,
      validRepassword: false,
      redirect: false,
    };
  }

  registerCallback = (data) => {
    if (data.status) {
      // sanctity checks passed -> post user
      postUsers(this.postUserCallback, this.state);
    } else {
      alert("This email is already registered.");
    }
    return data;
  };

  //handle error
  postUserCallback = (data) => {
    if (!data.state) {
      alert("User is created successfully");

      this.setState({
        redirect: true,
      });
    } else {
      alert("Registration unsuccessful");
    }
  };

  register = () => {
    // checking for empty fields
    let hasEmptyField = 0;
    if (!this.state.email) {
      alert("Email field cannot be empty");
      hasEmptyField = 1;
    }
    if (!this.state.password) {
      alert("Password field cannot be empty");
      hasEmptyField = 1;
    }
    if (!this.state.repassword) {
      alert("Repeat password field cannot be empty");
      hasEmptyField = 1;
    }
    if (hasEmptyField) {
      return;
    }

    // check for matching password/repassword
    if (!(this.state.password === this.state.repassword)) {
      alert("Passwords must match");
      return;
    }

    if (!this.state.validPassword) {
      alert("Password is too short");
      return;
    }

    // check for email format
    const nameMatch = this.state.email.match(/^([^@]*)@/);
    if (!nameMatch) {
      alert("Invalid email address format");
      return;
    }
    userExists(this.registerCallback, this.state);
  };

  email = (event) => {
    this.setState({
      email: event.target.value,
      validEmail: this.state.email.match(/^([^@]*)@/),
    });
  };

  password = (event) => {
    this.setState({
      password: event.target.value,
      validPassword: event.target.value.length >= 4,
    });
  };

  repassword = (event) => {
    this.setState({
      repassword: event.target.value,
      validRepassword:
        this.state.password === event.target.value && this.state.validPassword,
    });
  };

  render() {
    return (
      <div className={styles.registerPage}>
        {this.state.redirect ? (
          <Navigate
            to={{ pathname: "/login", state: { id: this.state.email } }}
          />
        ) : null}
        <div className={styles.card}>
          <div className={styles.containerLeft}>
            <img className={styles.path} src={RegisterPath} alt="regpath" />

            <img
              className={styles.background}
              src={registerBackground}
              alt="regback"
            />

            <div className={styles.greeting}>Welcome.</div>
            <div className={styles.subTextContainer}>
              <div>Become an</div>
              <div className={styles.subBold}>akademik</div>
              <div>today!</div>
            </div>
          </div>

          <div className={styles.containerRight}>
            <div className={styles.registerContainer}>
              <div className={styles.registerText}>Register</div>
              <div className={styles.textFieldContainer}>
                <TextField
                  inlineStyle={textField}
                  placeholder="E-mail"
                  onchange={this.email}
                  form={true}
                  valid={!this.state.email || this.state.validEmail}
                  InvalidText="Invalid email address format"
                />
                <TextField
                  inlineStyle={textField}
                  placeholder="Password"
                  type={"password"}
                  onchange={this.password}
                  form={true}
                  valid={!this.state.password || this.state.validPassword}
                  InvalidText="Password must have at least 4 characters"
                />
                <TextField
                  inlineStyle={textField}
                  placeholder="Repeat password"
                  type={"password"}
                  onchange={this.repassword}
                  form={true}
                  valid={!this.state.repassword || this.state.validRepassword}
                  InvalidText="Passwords must match"
                />
              </div>
              <Button
                text="Register"
                onclick={this.register}
                inlineStyle={registerButton}
              />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  login: state.login,
});

export default connect(mapStateToProps)(RegisterPage);
