import styles from './TextField.module.css';
import React from "react";

let defaultStyle = `${styles.textFieldBlack} `
let tempStyle = defaultStyle;

let invalidText = {}

let display = {
    color: "red",
    height: "14px",
    fontSize: "12px",
    textAlign: "left",
    position: "absolute",
    top: "-14px"
}

let displayBelow = {
    color: "red",
    height: "14px",
    fontSize: "12px",
    textAlign: "left",
    position: "absolute",
    top: "50px"
}

let none = {
    display: "none"
}

class TextField extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            placeholder: '',
            inlineStyle: {},
            type: '',
            value: '',
            region: ''
        }

        if (props.styleName) {
            let newArray = props.styleName.split(' ');

            newArray.forEach(element => {
                defaultStyle += `${styles[element]} `
            })

            tempStyle = defaultStyle;
        }

        this.state.placeholder = this.props.placeholder ? this.props.placeholder : ''
        this.state.inlineStyle = this.props.inlineStyle ? this.props.inlineStyle : {}
        this.state.type = this.props.type
        this.state.value = this.props.value
        this.state.region = this.props.region
    }

    render() {
        if (this.props.form) {
            if (!this.props.valid) {
                defaultStyle += `${styles.textFieldInvalid} `
                
                    this.state.region === "below"
                    ? invalidText = { ...displayBelow }
                    : invalidText = { ...display }
                
            } else {
                defaultStyle = tempStyle
                invalidText = {
                    ...none
                }
            }
        }

        return (
            <div className={styles.textFieldContainer}>
                <div style={invalidText}>{this.props.InvalidText}</div>
                {this.state.type === "number"
                    ? <input type={this.state.type} min={0} style={this.state.inlineStyle} placeholder={this.state.placeholder} className={defaultStyle} onChange={(event) => this.props.onchange(event)} value={this.props.value}/>
                    : <input type={this.state.type} style={this.state.inlineStyle} placeholder={this.state.placeholder} className={defaultStyle} onChange={(event) => this.props.onchange(event)} value={this.props.value} />}
            </div>
        )
    }
}

export default TextField