import styles from './SearchResult.module.css';
import React from "react";

class SearchResult extends React.Component {
    constructor(props) {
        super(props);
        props.data.authors = props.data.authors.replace(/,/g, " | ");
    }


    render() {
        return (
            <div className={styles.searchResultContainer}>
                <div className={styles.title}>{this.props.data.title}</div>
                <div className={styles.authorsContainer}>
                    <div className={styles.authors}>Authors:</div>
                    <div>
                        <div className={styles.authorsContainer}>
                            <div className={null} >{this.props.data.authors}</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default SearchResult