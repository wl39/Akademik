import React from "react";

import { Link } from "react-router-dom";

import Card from "../../../components/Card/Card";
import styles from './ArticlesPage.module.css'

import { getArticles } from "../../../axios/group20Axios";

import { connect } from "react-redux";

import Button from '../../../components/Button/Button'
import Toggle from "../../../components/Toggle/Toggle";

class ArticlePage extends React.Component {
    constructor(props) {
        super(props);

        let currentArticleID = 1
        let quantity = 4

        let currentToReviewID = 1

        //set articles json
        let articlesHTML = [];
        let trendingHTML = [];
        let toReviewHTML = [];

        this.state = {
            trendingContents: trendingHTML,
            articleContents: articlesHTML,
            articlesHTML: articlesHTML,
            toReviewHTML: toReviewHTML,
            currentArticleID: currentArticleID,
            currentToReviewID: currentToReviewID,
            quantity: quantity,
            gettingMore: false,
            showToReview: false,
            userid: props.login.userid,
            role: props.login.userRole
        }

    }

    getTrendingCallback = (data) => {
        if (data.length) {


            let trendingHTML = [];

            Object.values(data).forEach(element => {

                trendingHTML.push(
                    <Card inlineStyles={{ width: "calc(25% - 15px)" }} styleName="cardTrendingWhite article-round relative shadow" key={element.id + "-trending"}>
                        <Link className={styles.link} style={{ width: "100%", height: "100%" }} to={"/articles/" + element.id}>
                            <div className={styles.trending}>
                                <p className={styles.trendingText}>{element.title}</p>
                            </div>
                        </Link>
                    </Card>
                )
            })

            let currentHTML = this.state.trendingContents
            currentHTML.push(trendingHTML)

            this.setState({
                trendingHTML: currentHTML
            })
            getArticles(this.getArticlesCallback, this.props.login.userid, 4, this.state.currentArticleID, this.state.quantity)
        }
    }

    componentDidMount() {

        if (this.props.login.userid < 0) {
            this.props.history.push('/login')
            return null;
        }
        getArticles(this.getTrendingCallback, this.props.login.userid, 4, this.state.currentArticleID, this.state.quantity)

    }

    getToReviewArticlesCallback = (data) => {
        if (data.length) {
            let toReviewHTML = [];

            Object.values(data).forEach(element => {
                let authors = [];

                //have to change this (message from Alex, I have already changed this authors -> authorsArray)
                element.authorsArray.forEach((author, index) => {
                    authors.push(
                        <div className={element.authorsArray.length - 1 === index ? null : styles.author} key={author}>{author}</div>
                    )
                })

                toReviewHTML.push(
                    <Link className={styles.link} to={"/review/articles/" + element.id} key={"review_" + element.id}>
                        <Card key={element.title} inlineStyles={{ width: "calc(100% - 70px)", padding: "30px 35px", marginBottom: "30px" }} styleName="cardArticleWhite article-round relative shadow">
                            <div className={styles.title}>{element.title}</div>
                            <div className={styles.authorsContainer}>
                                <div className={styles.authors}>Authors:</div>
                                <div className={styles.authorsContainer}>{authors}</div>
                            </div>
                            <div className={styles.contents}>{element.abstract}</div>
                            <div className={styles.keywordsContainer}>
                                <div className={styles.keywords}>License:</div>
                                <div className={styles.keywordContainer}>
                                    {element.license}
                                </div>
                            </div>
                        </Card>
                    </Link>
                )


            })

            this.setState({
                toReviewHTML: toReviewHTML,
            })
        }
    }

    getArticlesCallback = (data) => {
        if (data.length) {


            let articlesHTML = [];

            let articleID;

            Object.values(data).forEach(element => {
                let authors = [];


                element.authorsArray.forEach((author, index) => {
                    authors.push(
                        <div className={element.authorsArray.length - 1 === index ? null : styles.author} key={author}>{author}</div>
                    )
                })

                articlesHTML.push(
                    <Link className={styles.link} to={"/articles/" + element.id} key={element.id}>
                        <Card key={element.title} inlineStyles={{ width: "calc(100% - 70px)", padding: "30px 35px", marginBottom: "30px" }} styleName="cardArticleWhite article-round relative shadow">
                            <div className={styles.title}>{element.title}</div>
                            <div className={styles.authorsContainer}>
                                <div className={styles.authors}>Authors:</div>
                                <div className={styles.authorsContainer}>{authors}</div>
                            </div>
                            <div className={styles.contents}>{element.abstract}</div>
                            <div className={styles.keywordsContainer}>
                                <div className={styles.keywords}>License:</div>
                                <div className={styles.keywordContainer}>
                                    {element.license}
                                </div>
                            </div>
                        </Card>
                    </Link>
                )

                articleID = element.id
            })

            let currentHTML = this.state.articleContents

            currentHTML.push(articlesHTML)

            this.setState({
                articleContents: currentHTML,
                articlesHTML: currentHTML,
                currentArticleID: articleID + 1,
                gettingMore: false
            })
        }

        getArticles(this.getToReviewArticlesCallback, this.props.login.userid, 3, this.state.currentArticleID, this.state.quantity)
    }

    getMoreArticle = (role) => {
        if (this.state.currentArticleID) {
            if (!this.state.gettingMore) {

                getArticles(this.getArticlesCallback, this.props.login.userid, role, this.state.currentArticleID, this.state.quantity)
            }
            this.setState({
                gettingMore: true
            })
        }

    }

    toReviewToggleHandler = (event) => {
        this.setState({
            showToReivew: event.target.checked,
            articleContents: event.target.checked ? this.state.toReviewHTML : this.state.articlesHTML
        })
    }

    render() {
        return (
            <div className={styles.articlesPage} >
                <div className={styles.articlesPageTitle}>{this.state.showToReivew ? "To Review" : "Articles"}</div>
                {this.props.login.userRole === 3 ?
                    <div className={styles.toggleContainer}>
                        <div className={styles.toggleText}>Show articles to review</div>
                        <Toggle onchange={this.toReviewToggleHandler} />
                    </div> : null}

                {this.state.showToReivew
                    ?

                    <div className={styles.articlesContainer}>

                        <div className={styles.articlesContentsContainer}>
                            <div className={styles.articleContents}>
                                {this.state.articleContents.length
                                    ?
                                    this.state.articleContents
                                    :
                                    <div className={styles.toReivewTextContainer}>
                                        You don't have any articles to review.
                                    </div>
                                }
                            </div>
                        </div>
                    </div>

                    :

                    <div className={styles.articlesContainer}>
                        <div className={styles.trendingContainer}>
                            <div className={styles.trendingTitle}>Trending</div>
                            <div className={styles.trendingContents}>
                                {this.state.trendingContents}
                            </div>
                        </div>
                        <div className={styles.articlesContentsContainer}>
                            <div className={styles.sortOptionsContainer}>
                                <div className={styles.sortTitle}>Published</div>
                            </div>
                            <div className={styles.articleContents}>
                                {this.state.articleContents}
                            </div>
                            {this.state.gettingMore ? null : this.state.currentArticleID ? <Button text="Get More Articles" onclick={() => this.getMoreArticle(4)} /> : null}
                        </div>
                    </div>

                }


            </div>
        );
    }
}

const mapStateToProps = state => ({
    login: state.login,
})

export default connect(mapStateToProps)(ArticlePage);
