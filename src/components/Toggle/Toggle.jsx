import styles from './Toggle.module.css';
import React from "react";

class Toggle extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            on: false
        }

    }

    

    render() {

        return (
            <label className={styles.switch}>
                <input type="checkbox" onClick={(event) => this.props.onchange(event)}/>
                <span className={styles.slider}></span>
            </label>
        )
    }
}

export default Toggle