import React from "react";

import styles from "./LoginPage.module.css";

import RegisterPath from "../../images/register_path.png";
import registerBackground from "../../images/register_background.png";

import Button from "../../../components/Button/Button";
import TextField from "../../../components/TextField/TextField";
import { login, getRole } from "../../../axios/group20Axios";
import Dropdown from "../../../components/Dropdown/Dropdown";

import { connect } from "react-redux";
import {
  setUserRole,
  userLogin,
  userNavigate,
} from "../../../store/modules/login";
import { withRouter } from "../../../utils/withRouter";

const textField = {
  height: "34px",
  fontSize: "16px",
};

const loginButton = {
  width: "90px",
  height: "40px",
  lineHeight: "40px",
  margin: "40px auto 0px",
  fontSize: "14px",
};

class LoginPage extends React.Component {
  constructor(props) {
    super(props);

    let registeredEmail = "";
    let validEmail = false;

    if (props.location.state) {
      if (props.location.state.id) {
        registeredEmail = props.location.state.id;
        validEmail = true;
      }
    }

    this.state = {
      email: registeredEmail,
      password: "",
      username: "",
      validEmail: validEmail,
      validPassword: false,
      items: [
        "Group 2",
        "Group 5",
        "Group 8",
        "Group 11",
        "Group 13",
        "Group 17",
        "Group 20",
        "Group 23",
        "Group 26",
      ],
      selected: "Group 20",
      group: "20",
      loggedin: false,
      userid: -1,
    };
  }

  getRoleCallback = (data) => {
    if (!data.state) {
      const { setUserRole } = this.props;

      let userInfo = {
        userRole: data.role,
      };

      setUserRole(userInfo);

      if (this.props.redirect) {
        const { userNavigate } = this.props;

        let userInfo = {
          redirect: 0,
        };

        this.props.history.push("/articles/" + this.props.redirect);

        userNavigate(userInfo);
      } else {
        this.props.history.push("/articles");
      }
    } else {
      alert("Failed get user role!");
    }
  };

  loginCallback = (data) => {
    // checking for empty fields
    if (data.status) {
      alert(
        "The email you are trying to log in with is not registered or password is incorrect"
      );
      return;
    }

    const { userLogin } = this.props;

    let splitEmail = this.state.email.split("@");
    let username = splitEmail[0];

    let userInfo = {
      username: username,
      userEmail: this.state.email,
      userid: data ? data.id : -1,
      userRole: 4,
      userGroup: this.state.group,
    };

    // sanctity checks passed -> log in user
    userLogin(userInfo);
    getRole(this.getRoleCallback, data.id);
  };

  login = () => {
    if (!this.state.email) {
      alert("Email field cannot be empty");
      return;
    }
    if (!this.state.password) {
      alert("Password field cannot be empty");
      return;
    }

    // check for email format
    const nameMatch = this.state.email.match(/^([^@]*)@/);

    if (!nameMatch) {
      alert("Invalid email address format");
      return;
    }

    if (!this.state.validPassword) {
      alert("Password is too short");
      return;
    }

    login(this.loginCallback, this.state);
  };

  email = (event) => {
    this.setState({
      email: event.target.value,
      validEmail: this.state.email.match(/^([^@]*)@/),
    });
  };

  password = (event) => {
    //match
    this.setState({
      password: event.target.value,
      validPassword: event.target.value.length >= 4,
    });
  };

  selected = (value) => {
    const regex = "\\d+";
    const matched_group = value.match(regex);
    this.setState({
      selected: value,
      group: matched_group[0],
    });
  };

  render() {
    return (
      <div className={styles.loginPage}>
        <div className={styles.card}>
          <div className={styles.containerLeft}>
            <img className={styles.path} src={RegisterPath} alt="regpath" />

            <img
              className={styles.background}
              src={registerBackground}
              alt="regback"
            />

            <div className={styles.greeting1}>Welcome</div>
            <div className={styles.greeting2}>Back.</div>
            <div className={styles.subTextContainer}>
              <div>To the danger</div>
              <div>zone.</div>
            </div>
          </div>

          <div className={styles.containerRight}>
            <div className={styles.loginContainer}>
              <div className={styles.loginText}>
                <div>Login</div>
                <Dropdown
                  onSelect={this.selected}
                  items={this.state.items}
                  selected={this.state.selected}
                  inlineMenuStyle={{ width: "inherit" }}
                />
              </div>

              <div className={styles.textFieldContainer}>
                <TextField
                  inlineStyle={textField}
                  placeholder="E-mail"
                  onchange={this.email}
                  value={this.state.email}
                  form={true}
                  valid={!this.state.email || this.state.validEmail}
                  InvalidText="Invalid email address format"
                />
                <TextField
                  inlineStyle={textField}
                  onchange={this.password}
                  placeholder={"Password"}
                  type={"password"}
                  form={true}
                  valid={!this.state.password || this.state.validPassword}
                  autoComplete="current-password"
                  variant="standard"
                  InvalidText="Password must have at least 4 characters"
                />
              </div>
              <Button
                text="Login"
                onclick={this.login}
                inlineStyle={loginButton}
              />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  username: state.login.username,
  loggedIn: state.login.loggedIn,
  userEmail: state.login.userEmail,
  userid: state.login.userid,
  userRole: state.login.userRole,
  redirect: state.login.redirect,
});

const mapDispatchToProps = (dispatch) => ({
  userLogin: (userInfo) => dispatch(userLogin(userInfo)),
  setUserRole: (userInfo) => dispatch(setUserRole(userInfo)),
  userNavigate: (userInfo) => dispatch(userNavigate(userInfo)),
});

export default withRouter(
  connect(mapStateToProps, mapDispatchToProps)(LoginPage)
);
