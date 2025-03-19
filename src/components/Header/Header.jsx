import React from "react";
import { Link, Navigate } from "react-router-dom";
import Button from "../Button/Button";
import styles from "./Header.module.css";
import { logout } from "../../axios/group20Axios";
import { connect } from "react-redux";
import { userLogout } from "../../store/modules/login";
import accountCircle from "../../assets/icons/account_circle_black_24dp.svg";
import SearchBar from "../SearchBar/SearchBar";

const akademikButton = {
  width: "200px",
};

const loginButton = {
  width: "84px",
  height: "53px",
  lineHeight: "53px",
  margin: "auto 7px auto 0px",
};

const logoutButton = {
  width: "120px",
  height: "53px",
  lineHeight: "53px",
  margin: "auto 0px auto 7px",
};

const userButton = {
  maxWidth: "350px",
  minWidth: "120px",
  height: "34px",
  margin: "auto 7px auto 0px",
  width: "max-content",
};

const submitArticleButton = {
  width: "180px",
  padding: "5px",
  paddingLeft: "10px",
  paddingRight: "10px",
};

const moderateButton = {
  width: "130px",
  padding: "5px 10px",
  marginRight: "10px",
};

const articlesButton = {
  width: "115px",
  height: "46px",
  lineHeight: "53px",
  marginLeft: "40px",
};
const registerButton = {
  width: "147px",
  height: "53px",
  lineHeight: "53px",
};

const headerScrolled = {
  backgroundColor: "white",
  boxShadow: "0px 3px 10px rgb(0 0 0 / 20%)",
};

const username = {
  maxWidth: "300px",
  overflow: "hidden",
  textOverflow: "ellipsis",
  display: "inline-block",
  verticalAlign: "middle",
};

class Header extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      scrolled: false,
      login: false,
      register: false,
      searchQuery: "",
    };
  }

  logoutCallback = (data) => {
    const { userLogout } = this.props;

    if (!data.status) {
      userLogout();
    } else {
      alert("Log out failed");
    }
  };

  logout = () => {
    logout(this.logoutCallback, this.props.login.userEmail);
  };

  submitArticle = () => {};

  listenScroll = () => {
    if (window.pageYOffset >= 200) {
      this.setState({
        scrolled: true,
      });
    } else {
      this.setState({
        scrolled: false,
      });
    }
  };

  componentDidMount() {
    window.addEventListener("scroll", this.listenScroll);
  }

  componentWillUnmount() {
    window.removeEventListener("scroll", this.listenScroll);
  }

  searchHandler = (event) => {
    this.setState({
      searchQuery: event.target.value,
    });
  };

  render() {
    return (
      <div
        style={this.state.scrolled ? headerScrolled : {}}
        className={
          window.location.pathname === "/"
            ? styles.header
            : styles.headerNotMain
        }
      >
        {this.props.login.loggedIn === false &&
        window.location.pathname !== "/" &&
        window.location.pathname !== "/register" &&
        window.location.pathname !== "/login" ? (
          <Navigate to={{ pathname: "/login" }} />
        ) : null}
        <Link to="/" className={styles.link}>
          <Button
            text="akademik"
            inlineStyle={akademikButton}
            styleName="transparent"
          />
        </Link>
        {this.props.login.loggedIn &&
        (window.location.pathname === "/" ||
          window.location.pathname !== "/articles") ? (
          <Link to="/articles" className={styles.link}>
            <Button
              text="Articles"
              inlineStyle={articlesButton}
              styleName="transparent"
            />
          </Link>
        ) : null}
        {this.props.login.loggedIn &&
        window.location.pathname === "/articles" ? (
          <SearchBar
            onchange={this.searchHandler}
            placeholder="Search..."
          ></SearchBar>
        ) : null}
        {this.props.login.loggedIn ? (
          <div className={styles.signContainer}>
            {window.location.pathname === "/articles" ? (
              <div>
                {this.props.login.isReviewer === true ? (
                  <div className={styles.signContainer}>
                    <div
                      style={{
                        position: "relative",
                        display: "flex",
                      }}
                    >
                      <Link
                        to="/articles/review"
                        style={{
                          display: "flex",
                          textDecoration: "none",
                          color: "inherit",
                          margin: "auto",
                          top: 0,
                          bottom: 0,
                        }}
                      >
                        <Button
                          text="To Review"
                          inlineStyle={submitArticleButton}
                          styleName="black"
                        />
                      </Link>
                    </div>
                  </div>
                ) : null}

                <div className={styles.signContainer}>
                  <div
                    style={{
                      position: "relative",
                      display: "flex",
                    }}
                  >
                    {this.props.login.userRole === 1 ? (
                      <Link
                        to="/moderate"
                        style={{
                          display: "flex",
                          textDecoration: "none",
                          color: "inherit",
                          margin: "auto",
                          top: 0,
                          bottom: 0,
                        }}
                      >
                        <Button
                          text="Moderate"
                          inlineStyle={moderateButton}
                          styleName="black"
                        />
                      </Link>
                    ) : null}

                    <Link
                      to="/articles/upload"
                      style={{
                        display: "flex",
                        textDecoration: "none",
                        color: "inherit",
                        margin: "auto",
                        top: 0,
                        bottom: 0,
                      }}
                    >
                      <Button
                        text="Submit Article"
                        inlineStyle={submitArticleButton}
                        styleName="black"
                      />
                    </Link>
                  </div>
                </div>
              </div>
            ) : null}
            <Button
              text="Logout"
              inlineStyle={logoutButton}
              styleName="transparent"
              onclick={this.logout}
              href="/"
            />
            <div
              style={{
                position: "relative",
                display: "flex",
              }}
            >
              <Link
                to="/users/profile"
                style={{
                  display: "flex",
                  textDecoration: "none",
                  color: "inherit",
                  margin: "auto",
                  top: 0,
                  bottom: 0,
                }}
              >
                <Button
                  text={this.props.login.username}
                  inlineStyle={userButton}
                  textStyle={username}
                  styleName="transparent"
                  account={accountCircle}
                />
              </Link>
            </div>
          </div>
        ) : (
          <div className={styles.signContainer}>
            {window.location.pathname === "/login" ? (
              <div />
            ) : (
              <Link
                to="/login"
                style={{
                  display: "flex",
                  textDecoration: "none",
                  color: "inherit",
                }}
              >
                <Button
                  text="Login"
                  inlineStyle={loginButton}
                  styleName="transparent"
                />
              </Link>
            )}
            {window.location.pathname === "/register" ? (
              <div />
            ) : (
              <Link to="/register">
                <Button
                  text="Register"
                  inlineStyle={registerButton}
                  styleName="black"
                />
              </Link>
            )}
          </div>
        )}
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  login: state.login,
});

const mapDispatchToProps = (dispatch) => ({
  userLogout: () => dispatch(userLogout()),
});

export default connect(mapStateToProps, mapDispatchToProps)(Header);
