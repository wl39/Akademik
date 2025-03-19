import styles from './Card.module.css';
import React from "react";

function Card({ ...props }) {
    const {
        children,
    } = props;

    let result = ``;

    if (props.styleName) {
        let newArray = props.styleName.split(' ');

        newArray.forEach(element => {
            result += `${styles[element]} `
        })
    }

    return (
        <div className={result} style={props.inlineStyles}>
            {children}
        </div>
    )
}

export default Card