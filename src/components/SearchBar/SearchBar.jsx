import styles from './SearchBar.module.css';
import React from "react";
import TextField from "../TextField/TextField";
import SearchResult from '../SearchResult/SearchResult';
import Loader from '../Loader/Loader';
import Card from '../Card/Card';
import { search } from '../../axios/group20Axios'
import { connect } from 'react-redux';
import { Link } from "react-router-dom";
import MultiDropdown from "../MultiDropdown/MultiDropdown";

let defaultStyle = `${styles.searchBar} `

class SearchBar extends TextField {
    constructor(props) {
        super(props);

        this.state = {
            value: "",
            result: false,
            click: false,
            userid: this.props.login.userid,
            groupNumbers: [20],
            searchResults: [],
            opened: false,
            allGroups: ["20", "2", "5", "8", "11", "13", "17", "23", "26"],
            candidateConditions: { 20: true, 2: false, 5: false, 8: false, 11: false, 13: false, 17: false, 23: false, 26: false }
        }
    }

    searchCallback = (data) => {
        this.state.searchResults = [];
        if (!data.state) {
            if (data.length > 0) {
                for (let i = 0; i < data.length; i++) {
                    const regex = ".+(/articles/.+)$";
                    const ourGroupRegex = "s3099user20";
                    const articleURL = data[i].articleURL.match(regex);
                    const ourGroup = data[i].articleURL.match(ourGroupRegex);
                    if (ourGroup !== null) {
                        this.state.searchResults.push(
                            <Link to={articleURL[1]} style={{ textDecoration: 'none', color: "black" }}>
                                <SearchResult key={i} data={data[i]} />
                            </Link>
                        );
                    } else {
                        this.state.searchResults.push(
                            <Link target="_blank" to={{ pathname: data[i].articleURL }} style={{ textDecoration: 'none', color: "black" }}>
                                <SearchResult key={i} data={data[i]} />
                            </Link>
                        );
                    }
                }
                this.setState({
                    result: true,
                })
            } else {
                this.setState({
                    result: false,
                })
            }

        } else {
            this.setState({
                result: false,
            })
        }

    }

    onchange = (event) => {
        this.setState({
            value: event.target.value
        })

        if (event.target.value === "") {
            this.setState({
                opened: false
            })
        } else {
            this.setState({
                opened: true
            })
        }

        let tempState = {
            value: event.target.value,
            userid: this.state.userid,
            groupNumbers: this.state.groupNumbers
        }

        search(this.searchCallback, tempState);

    }

    expand = () => {
        if (!this.state.entered)
            this.setState({
                opened: !this.state.opened
            })
    }

    groupSearchHandler = (values) => {
        let checkedState = { ...values }
        let checkedArray = []

        for (let key in checkedState) {
            if (checkedState[key]) {
                checkedArray.push(parseInt(key));
            }
        }

        this.setState({
            groupNumbers: checkedArray
        })
    }

    render() {
        return (
            <div style={{ display: "flex", maxHeight: "53px", width: "100%", margin: "0 20px" }}>
                <div className={styles.searchBarContainer}>
                    <svg width="25" height="25" viewBox="0 0 25 25" fill="rgba(8, 8, 8, 1)" className={styles.searchIcon}>
                        <path d="M20.07 18.93l-4.16-4.15a6 6 0 1 0-.88.88l4.15 4.16a.62.62 0 1 0 .89-.89zM6.5 11a4.75 4.75 0 1 1 9.5 0 4.75 4.75 0 0 1-9.5 0z" />
                    </svg>
                    <input type={this.state.type} placeholder={this.state.placeholder} className={defaultStyle} onChange={(event) => this.onchange(event)} value={this.props.value} />
                    {this.state.opened && this.state.result
                        ?
                        <Card inlineStyles={resultsBox} styleName="cardTrendingWhite article-round absolute shadow">
                            {this.state.searchResults}
                        </Card>
                        :
                        (this.state.opened
                            ?
                            <Card inlineStyles={resultsBox} styleName="cardTrendingWhite article-round absolute shadow">
                                <Loader />
                            </Card>
                            :
                            null)
                    }
                </div>
                <MultiDropdown
                    inlineStyles={{}}
                    inlineDropdownStyle={{ height: "100%" }}
                    noDisplay={true}
                    items={this.state.allGroups}
                    values={this.state.candidateConditions}
                    placehold="Groups"
                    onchange={(values) => this.groupSearchHandler(values)}
                />
            </div>
        )
    }
}

const mapStateToProps = (state) => ({
    login: state.login,
})

const resultsBox = {
    width: "calc(100% - 25px)",
    position: "absolute",
    top: "55px",
    left: "-1px",
    padding: "20px 15px",
    height: "auto"
}

export default connect(mapStateToProps)(SearchBar)