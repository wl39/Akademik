import styles from './Loader.module.css';
import React from "react";

// let defaultStyle = `${styles.searchBar} `

class Loader extends React.Component {

    render() {
        return (
            <div className={styles.loader} />
        )
    }
}

export default Loader