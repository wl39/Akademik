import React from "react";

import styles from './ModeratePage.module.css'

import reviewer from '../../icons/how_to_reg_black_48dp.svg'
import article from '../../icons/post_add_black_48dp.svg'

import { connect } from 'react-redux';
import { userLogin } from "../../../store/modules/login";

import { Link } from "react-router-dom";

class ModeratePage extends React.Component {
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
    }

    render() {
        return (
            <div className={styles.moderatePage}>
                <div className={styles.title}>Moderate</div>
                <div className={styles.container}>
                    <Link className={styles.leftWrapper} to={"/moderate/articles"}>
                        <img className={styles.image} src={article} alt={"article"} />
                        <div className={styles.textContainer}>
                            <div className={styles.text}>
                                Find the best article
                            </div>
                            <div className={styles.text}>
                                to post in Akademik.
                            </div>
                        </div>
                    </Link>
                    <Link className={styles.rightWrapper} to={"/moderate/reviewers"}>
                        <img className={styles.image} src={reviewer} alt={"reviewer"} />
                        <div className={styles.textContainer}>
                            <div className={styles.text}>Choose the best user</div>
                            <div className={styles.text}>for reviewer.</div>
                        </div>
                    </Link>
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

export default connect(mapStateToProps, mapDispatchToProps)(ModeratePage);
