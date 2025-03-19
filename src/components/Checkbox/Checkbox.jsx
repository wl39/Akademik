import styles from './Checkbox.module.css';
import React from "react";

// let defaultStyle = `${styles.textFieldBlack} `
// let tempStyle = defaultStyle;

class Checkbox extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            on: false
        }

        // if (props.styleName) {
        //     let newArray = props.styleName.split(' ');

        //     newArray.forEach(element => {
        //         defaultStyle += `${styles[element]} `
        //     })

        //     tempStyle = defaultStyle;
        // }
    }



    render() {

        return (
            <div className={styles.container}>
                <input
                    type="checkbox"
                    className={styles.input}
                    id={this.props.id}
                    onClick={(event) => this.props.onclick(event)}
                    checked={this.props.checked}
                    disabled={this.props.disabled}
                />
                <span className={styles.box} />
            </div>
        )
    }
}

export default Checkbox