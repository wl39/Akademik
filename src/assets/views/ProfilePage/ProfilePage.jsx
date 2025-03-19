import React from "react";
import { Link } from "react-router-dom";

import styles from "./ProfilePage.module.css";

import Card from "../../../components/Card/Card";
import Button from "../../../components/Button/Button";
import Modal from "../../../components/Modal/Modal";
import TextField from "../../../components/TextField/TextField";
import accountCircle from "../../icons/account_circle_black_24dp.svg";

import { connect } from "react-redux";
import { userLogin } from "../../../store/modules/login";
import {
  changeEmail,
  changePassword,
  getArticles,
  login,
} from "../../../axios/group20Axios";

const changeButton = {
  width: "100px",
  height: "30px",
  lineHeight: "35px",
  fontSize: "18px",
};

const applyButton = {
  margin: "20px 50px 0",
  width: "450px",
  height: "30px",
  lineHeight: "35px",
  fontSize: "18px",
};

const password = {
  fontSize: "14px",
  height: "30px",
  width: "300px",
  float: "left",
  marginTop: "10px",
};

const email = {
  fontSize: "14px",
  height: "30px",
  width: "300px",
  float: "left",
  marginTop: "10px",
};

class ProfilePage extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      newEmail: "",
      newPassword: "",

      username: this.props.login.username,
      email: this.props.login.userEmail,
      password: "",
      userid: this.props.login.userid,
      group: this.props.login.userGroup,

      validEmail: false,
      validPassword: false,
      role: props.login.userRole,
      show: false,
      changingEmail: false,

      articlesHTML: [],
      currentArticleID: 1,
      gettingMore: false,
      quantity: 4,
    };
    this.showModal = this.showModal.bind(this);
    this.hideModal = this.hideModal.bind(this);
  }

  changeEmailCallback = (data) => {
    this.hideModal();
    if (!data.status) {
      const { userLogin } = this.props;

      let userInfo = {
        ...this.props.login,
        username: this.state.newEmail.split("@")[0],
        userEmail: this.state.newEmail,
      };

      userLogin(userInfo);

      this.setState({
        email: this.state.newEmail,
      });

      alert("Email changed successfully");
      window.location.reload();
    } else {
      alert("Email change unsuccessful");
    }
    return data;
  };

  changeEmail = () => {
    if (this.state.validEmail) {
      this.setState({
        changingEmail: true,
      });
      this.showModal();
    } else {
      alert("Invalid email format.");
    }
  };

  showModal = () => {
    this.setState({ show: true });
  };

  hideModal = () => {
    this.setState({ show: false });
  };

  changePasswordCallback = (data) => {
    this.hideModal();
    if (!data.status) {
      alert("Password changed successfully");
    } else {
      alert("Password change unsuccessful");
    }
    return data;
  };

  changePassword = () => {
    if (this.state.validPassword) {
      this.setState({
        changingEmail: false,
      });
      this.showModal();
    } else {
      alert("Passwords must be at least 4 characters long.");
    }
  };

  promoteToReviewer = () => {
    alert("Applied!");
  };

  email = (event) => {
    this.setState({
      newEmail: event.target.value,
      validEmail: this.state.newEmail.match(/^([^@]*)@/),
    });
  };

  currentPassword = (event) => {
    this.setState({
      password: event.target.value,
    });
  };

  password = (event) => {
    //match
    this.setState({
      newPassword: event.target.value,
      validPassword: event.target.value.length >= 4,
    });
  };

  detailChangeCallback = (data) => {
    if (!data.status) {
      this.state.changingEmail
        ? changeEmail(this.changeEmailCallback, this.state)
        : changePassword(this.changePasswordCallback, this.state);
    } else {
      alert("Wrong password");
    }
  };

  tryChangeDetails = () => {
    login(this.detailChangeCallback, this.state);
  };

  //Lim added
  getArticlesCallback = (data) => {
    if (data.length) {
      let articlesHTML = [];

      let articleID;

      Object.values(data).forEach((element) => {
        let authors = [];

        let version;
        let subVersion;
        let topVersionID = element.topVersionID;

        element.versions.forEach((value) => {
          if (value.versionID === topVersionID) {
            version = value.version;
            subVersion = value.subVersion;
          }
        });

        let linkpath = "/articles/";
        let status = " Published";
        if (version === 0 && subVersion === 0) {
          linkpath = "/moderate/articles/";
          status = " To be moderated.";
        } else if (version > 0 && subVersion !== 0) {
          linkpath = "/review/articles/";
          status = " To be reviewed.";
        }

        //have to change this
        element.authorsArray.forEach((author, index) => {
          authors.push(
            <div
              className={
                element.authorsArray.length - 1 === index ? null : styles.author
              }
              key={author}
            >
              {author}
            </div>
          );
        });

        articlesHTML.push(
          <Link
            className={styles.link}
            to={linkpath + element.id}
            key={element.id}
          >
            <Card
              key={element.title}
              inlineStyles={{
                width: "56.25%",
                padding: "30px 35px",
                marginBottom: "30px",
              }}
              styleName="cardArticleWhite article-round relative shadow"
            >
              <div className={styles.status}>Status - {status}</div>
              <div className={styles.title}>{element.title}</div>
              <div className={styles.authorsContainer}>
                <div className={styles.authors}>Authors:</div>
                <div className={styles.authorsContainer}>{authors}</div>
              </div>
              <div className={styles.contents}>{element.abstract}</div>
            </Card>
          </Link>
        );

        articleID = element.id;
      });

      this.setState({
        articlesHTML: articlesHTML,
        currentArticleID: articleID + 1,
        gettingMore: false,
      });
    }
  };

  componentDidMount() {
    if (this.props.login.userid < 0) {
      this.props.history.push("/login");
      return null;
    }

    getArticles(
      this.getArticlesCallback,
      this.props.login.userid,
      2,
      this.state.currentArticleID,
      this.state.quantity
    );
  }

  render() {
    return (
      <div className={styles.profileContainer}>
        <Modal show={this.state.show} handleClose={this.hideModal}>
          <p>Enter Current Password to Confirm</p>
          <TextField
            inlineStyle={password}
            onchange={this.currentPassword}
            placeholder={"Password"}
            type={"password"}
            form={true}
            variant="standard"
            valid={true}
          />
          <div style={{ height: "60px" }}></div>
          <Button
            onclick={this.tryChangeDetails}
            text={"Confirm"}
            className={styles.changeButton}
            inlineStyle={changeButton}
            styleName={"black"}
          ></Button>
        </Modal>
        <div className={styles.profileBox}>
          {/*!this.props.login.loggedIn ? <Navigate to={{ pathname: "/login" }} /> : null*/}
          <div className={styles.profileText}>
            <div>Profile</div>
          </div>
          <div className={styles.card}>
            <div className={styles.topContainer}>
              <img
                className={styles.profilePicture}
                src={accountCircle}
                alt={"accountCircle"}
              />
              <div className={styles.usernameContainer}>
                <p className={styles.username}>{this.props.login.userEmail}</p>
              </div>
            </div>
            <div className={styles.bottomContainer}>
              <div className={styles.emailContainer}>
                <div className={styles.infoLeft}>
                  <p className={styles.emailSubtitle}>Email address</p>
                  <TextField
                    inlineStyle={email}
                    placeholder="E-mail"
                    onchange={this.email}
                    value={this.state.newEmail}
                    form={true}
                    valid={!this.state.newEmail || this.state.validEmail}
                    InvalidText="Invalid email address format"
                    region={"below"}
                    defaultValue={this.props.login.userEmail}
                  />
                </div>
                <Button
                  onclick={this.changeEmail}
                  text={"Change"}
                  className={styles.changeButton}
                  inlineStyle={changeButton}
                  styleName={"black"}
                ></Button>
              </div>
              <div className={styles.passwordContainer}>
                <div className={styles.infoLeft}>
                  <p className={styles.passwordSubtitle}>Password</p>
                  <TextField
                    inlineStyle={password}
                    onchange={this.password}
                    placeholder={"Password"}
                    type={"password"}
                    form={true}
                    valid={!this.state.newPassword || this.state.validPassword}
                    variant="standard"
                    region={"below"}
                    InvalidText="Password must have at least 4 characters"
                  />
                </div>
                <Button
                  onclick={this.changePassword}
                  text={"Change"}
                  className={styles.changeButton}
                  inlineStyle={changeButton}
                  styleName={"black"}
                ></Button>
              </div>
            </div>
          </div>
          {this.state.role === 4 ? (
            <Link to="/users/profile/apply-reviewer">
              <Button
                text={"Apply to be Become a Reviewer"}
                className={styles.changeButton}
                inlineStyle={applyButton}
                styleName={"black"}
              ></Button>
            </Link>
          ) : null}
        </div>
        {this.state.articlesHTML.length ? (
          <div className={styles.yourArticles}>Your Articles</div>
        ) : null}
        {this.state.articlesHTML}
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  login: state.login,
});

const mapDispatchToProps = (dispatch) => ({
  userLogin: (userInfo) => dispatch(userLogin(userInfo)),
});

export default connect(mapStateToProps, mapDispatchToProps)(ProfilePage);
