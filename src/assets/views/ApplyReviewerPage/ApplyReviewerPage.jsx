import React from "react";
import styles from "./ApplyReviewerPage.module.css";
import Card from "../../../components/Card/Card";
import TextField from "../../../components/TextField/TextField";
import { applyReviewer } from "../../../axios/group20Axios";
import Button from "../../../components/Button/Button";
import { connect } from "react-redux";
import { Navigate } from "react-router-dom";

const applyButton = {
  fontSize: "18px",
  padding: "5px 15px",
  float: "right",
};

const textField = {
  fontSize: "14px",
  height: "30px",
};

class ApplyReviewerPage extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      userid: props.login.userid,
      institution: "",
      fieldsOfExpertise: "",
      biography: "",
      redirect: false,
    };
  }

  componentDidMount() {
    if (this.props.login.userid < 0) {
      this.props.history.push("/login");
      return null;
    }
  }

  applyCallback = (data) => {
    if (!data.status) {
      alert("Success: Application submitted.");
      this.props.login.userRole = 3; // sets user to reviewer role (hides apply button)
      this.setState({
        redirect: true,
      });
    } else {
      alert("Error: application could not be submitted");
    }
  };

  apply = () => {
    applyReviewer(this.applyCallback, this.state);
  };

  institutionHandler = (event) => {
    this.setState({
      institution: event.target.value,
    });
  };

  fieldsOfExpertiseHandler = (event) => {
    this.setState({
      fieldsOfExpertise: event.target.value,
    });
  };

  biographyHandler = (event) => {
    this.setState({
      biography: event.target.value,
    });
  };

  render() {
    return (
      <div className={styles.main}>
        {this.state.redirect ? (
          <Navigate to={{ pathname: "/users/profile" }} />
        ) : null}
        <div className={styles.vBox}>
          <div className={styles.header}>Apply to Become a Reviewer</div>
          <Card
            inlineStyles={{ width: "800px", height: "100%", padding: "30px" }}
            styleName="cardArticleWhite article-round relative shadow"
          >
            <div className={styles.inputContainer}>
              <div className={styles.containerHeader}>
                Institution (Optional)
              </div>
              <TextField
                inlineStyle={textField}
                onchange={this.institutionHandler}
                placeholder="e.g. University of St. Andrews"
                value={this.state.institution}
              />
            </div>
            <div className={styles.inputContainer}>
              <div className={styles.containerHeader}>Fields of Expertise</div>
              <TextField
                inlineStyle={textField}
                onchange={this.fieldsOfExpertiseHandler}
                placeholder="e.g. Machine Learning, Blockchain"
                value={this.state.fieldsOfExpertise}
              />
            </div>
            <div className={styles.inputContainer}>
              <div className={styles.containerHeader}>Biography</div>
              <textarea
                className={styles.customTextarea}
                placeholder="Who you are and what you do"
                onChange={this.biographyHandler}
                value={this.state.biography}
              />
            </div>
            <Button
              text="Apply"
              inlineStyle={applyButton}
              styleName="black"
              onclick={this.apply}
            />
          </Card>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  login: state.login,
});

export default connect(mapStateToProps)(ApplyReviewerPage);
