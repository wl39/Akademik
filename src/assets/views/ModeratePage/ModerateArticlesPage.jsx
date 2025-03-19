import React from "react";

import { Link, Navigate } from "react-router-dom";

import Card from "../../../components/Card/Card";
import styles from "./ModerateArticlesPage.module.css";

import { getArticles } from "../../../axios/group20Axios";

import { connect } from "react-redux";

import Button from "../../../components/Button/Button";

class ModerateArticlesPage extends React.Component {
  constructor(props) {
    super(props);

    let currentArticleID = 1;
    let quantity = 4;

    let currentToReviewID = 1;

    //set articles json
    let articlesHTML = [];
    let toReviewHTML = [];

    this.state = {
      articleContents: articlesHTML,
      articlesHTML: articlesHTML,
      toReviewHTML: toReviewHTML,
      currentArticleID: currentArticleID,
      currentToReviewID: currentToReviewID,
      quantity: quantity,
      gettingMore: false,
      showToReivew: false,
    };
  }

  componentDidMount() {
    if (this.props.login.userRole !== 1) {
      this.props.history.push("/login");
      return null;
    }
    getArticles(
      this.getArticlesCallback,
      this.props.login.userid,
      this.props.login.userRole,
      this.state.currentArticleID,
      this.state.quantity
    );
  }

  getToReviewArticlesCallback = (data) => {
    let toReviewHTML = [];

    let articleID;

    Object.values(data).forEach((element) => {
      let authors = [];

      //have to change this (message from Alex, I have already changed this authors -> authorsArray)
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

      toReviewHTML.push(
        <Link
          className={styles.link}
          to={"/review/" + element.id}
          key={"review_" + element.id}
        >
          <Card
            key={element.title}
            inlineStyles={{
              width: "calc(100% - 70px)",
              padding: "30px 35px",
              marginBottom: "30px",
            }}
            styleName="cardArticleWhite article-round relative shadow"
          >
            <div className={styles.title}>{element.title}</div>
            <div className={styles.authorsContainer}>
              <div className={styles.authors}>Authors:</div>
              <div className={styles.authorsContainer}>{authors}</div>
            </div>
            <div className={styles.contents}>{element.abstract}</div>
            <div className={styles.keywordsContainer}>
              <div className={styles.keywords}>License:</div>
              <div className={styles.keywordContainer}>{element.license}</div>
            </div>
          </Card>
        </Link>
      );

      articleID = element.id;
    });

    let currentHTML = this.state.articleContents;

    currentHTML.push(toReviewHTML);

    this.setState({
      articleContents: toReviewHTML,
      toReviewHTML: toReviewHTML,
      currentArticleID: articleID + 1,
      gettingMore: false,
    });
  };

  getArticlesCallback = (data) => {
    let articlesHTML = [];

    let articleID;

    Object.values(data).forEach((element) => {
      let authors = [];

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
          to={"/moderate/articles/" + element.id}
          key={element.id}
        >
          <Card
            key={element.title}
            inlineStyles={{
              width: "calc(100% - 70px)",
              padding: "30px 35px",
              marginBottom: "30px",
            }}
            styleName="cardArticleWhite article-round relative shadow"
          >
            <div className={styles.title}>{element.title}</div>
            <div className={styles.authorsContainer}>
              <div className={styles.authors}>Authors:</div>
              <div className={styles.authorsContainer}>{authors}</div>
            </div>
            <div className={styles.contents}>{element.abstract}</div>
            <div className={styles.keywordsContainer}>
              <div className={styles.keywords}>License:</div>
              <div className={styles.keywordContainer}>{element.license}</div>
            </div>
          </Card>
        </Link>
      );

      articleID = element.id;
    });

    let currentHTML = this.state.articleContents;

    currentHTML.push(articlesHTML);

    this.setState({
      articleContents: currentHTML,
      articlesHTML: currentHTML,
      currentArticleID: articleID + 1,
      gettingMore: false,
    });
  };

  getMoreArticle = () => {
    if (this.state.currentArticleID) {
      if (!this.state.gettingMore) {
        getArticles(
          this.getArticlesCallback,
          this.props.login.userid,
          this.props.login.userRole,
          this.state.currentArticleID,
          this.state.quantity
        );
      }
      this.setState({
        gettingMore: true,
      });
    }
  };

  toReviewToggleHandler = (event) => {
    this.setState({
      showToReivew: event.target.checked,
      articleContents: event.target.checked
        ? this.state.toReviewHTML
        : this.state.articlesHTML,
    });
  };

  render() {
    return (
      <div className={styles.articlesPage}>
        {this.props.login.loggedIn ? null : <Navigate to={{ pathname: "/" }} />}
        <div className={styles.moderateArticlesPageTitle}>
          <div>Moderate</div>
          <div className={styles.subTitle}>Article</div>
        </div>

        {this.state.showToReivew ? (
          <div className={styles.articlesContainer}>
            <div className={styles.articlesContentsContainer}>
              <div className={styles.articleContents}>
                {this.state.articleContents.length ? (
                  this.state.articleContents
                ) : (
                  <div className={styles.toReivewTextContainer}>
                    You don't have any article to review.
                  </div>
                )}
              </div>
              {this.state.gettingMore ? null : this.state.articleContents
                  .length ? (
                <Button
                  text="Get More Articles"
                  onclick={this.getMoreArticle}
                />
              ) : null}
            </div>
          </div>
        ) : (
          <div className={styles.articlesContainer}>
            <div className={styles.articlesContentsContainer}>
              <div className={styles.articleContents}>
                {this.state.articleContents}
              </div>
            </div>
          </div>
        )}
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  login: state.login,
});

export default connect(mapStateToProps)(ModerateArticlesPage);
