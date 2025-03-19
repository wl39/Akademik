import React from "react";

import styles from './ModerateUserPage.module.css'

import Card from '../../../components/Card/Card.jsx'
import Button from "../../../components/Button/Button";

import {
    getUnauthorisedReviewers, rejectReviewer, acceptReviewer
} from "../../../axios/group20Axios";


import { connect } from 'react-redux';
import { userLogin } from "../../../store/modules/login";

const button = {
    padding: "0px 8px"
}

class ModerateUserPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            candidates: [],
            candidatesData: []
        }
    }

    componentDidMount() {
        if (this.props.login.userRole !== 1) {
            this.props.history.push('/login')
            return null;
        }

        getUnauthorisedReviewers(this.reviewersCallback, this.props.login.userid);
    }

    acceptCallback = (data, email) => {
        if (!data.state) {
            alert(email + " has been accepted!")

            getUnauthorisedReviewers(this.reviewersCallback, this.props.login.userid);
        }
    }

    rejectCallback = (data, email) => {
        if (!data.state) {
            alert(email + " has been rejected!")

            getUnauthorisedReviewers(this.reviewersCallback, this.props.login.userid);
        }
    }

    accept = (reviewerID, email) => {
        acceptReviewer(this.acceptCallback, this.props.login.userid, reviewerID, email);
    }

    reject = (reviewerID, email) => {
        rejectReviewer(this.rejectCallback, this.props.login.userid, reviewerID, email);
    }

    reviewersCallback = (data) => {

        let candidatesTag = [];
        let candidatesData = [...data];

        if (data.length === 0) {
            candidatesTag.push(
                <div className={styles.noUser} key={"no_user"}>There are no reviewer candidates.</div>
            )
        }

        data.forEach((element) => {
            candidatesTag.push(
                <Card
                    inlineStyles={{
                        "width": "calc(100% - 70px)",
                        "padding": "30px 50px",
                        "marginBottom": "30px",
                        "minHeight": "250px",
                        "position": "relative"
                    }}
                    styleName="cardArticleWhite article-round relative shadow"
                    key={element.reviewerID}
                >
                    <div className={styles.emailContainer}>
                        <div>{element.email}</div>
                    </div>
                    <div>
                        <div className={styles.detailsContainer}>
                            <div className={styles.lineContainer}>
                                <div className={styles.lineHeader}>Institution: </div>
                                {element.institution ? element.institution : "Does not belong to any institution"}
                            </div>
                            <div className={styles.lineContainer}>
                                <div className={styles.lineHeader}>expertise: </div>
                                <div>{element.expertise}</div>
                            </div>
                        </div>
                        <div className={styles.bioContainer}>
                            <div className={styles.bioHeader}>Bio -</div>
                            <div className={styles.bio}>{element.bio}</div>
                        </div>
                    </div>
                    <div className={styles.buttonContainer}>
                        <Button text="Approve" inlineStyle={button} onclick={() => this.accept(element.reviewerID, element.email)} />
                        <Button text="Reject" inlineStyle={button} onclick={() => this.reject(element.reviewerID, element.email)} />
                    </div>
                </Card>
            )

        })

        this.setState({
            candidates: candidatesTag,
            candidatesData: candidatesData
        })
    }


    render() {
        return (
            <div className={styles.moderateContainer}>
                <div className={styles.moderateReviewerPageTitle}>
                    <div>
                        Moderate
                    </div>
                    <div className={styles.subTitle}>
                        Reviewers
                    </div>
                </div>
                <div className={styles.candidatesContainer}>
                    {this.state.candidates}
                </div>
            </div>
        );
    }
}

const mapStateToProps = state => ({
    login: state.login
});

const mapDispatchToProps = dispatch => ({
    userLogin: (userInfo) => dispatch(userLogin(userInfo)),
});

export default connect(mapStateToProps, mapDispatchToProps)(ModerateUserPage);
