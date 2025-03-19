import styles from './Button.module.css';
import React from "react";

/**
 * Button class is a button component
 * 
 * This component contains three states
 * 1. text: will be loacted in the button
 * 2. styleName: can choose one of css options
 *      white
 *      transparent
 * 3. inlineStyle: user can pass an inline css as a props
 * 
 * Button Component can hold these props
 * text: name of the button
 * styleName: a css option
 * inlineStyle: inline css
 * onclick: a function
 */
class Button extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            text: this.props.text ? this.props.text : "BUTTON",
            styleName: {},
            inlineStyle: this.props.inlineStyle ? this.props.inlineStyle : {},
            textStyle: this.props.textStyle ? this.props.textStyle : {}
        }
        switch (this.props.styleName) {
            case 'white':
                this.state.styleName = styles.buttonWhite
                break;
            case 'transparent':
                this.state.styleName = styles.buttonTransparent
                break;
            default:
                this.state.styleName = styles.buttonBlack
                break;
        }
    }

    render() {
        return (
            <div className={this.state.styleName} style={this.state.inlineStyle} onClick={this.props.onclick}>
                {this.props.account ? <img style={{ margin: "-5px 3px", display: "inline-block"}} src={this.props.account} alt="account" /> : null}
                <div style={this.state.textStyle}>
                    {this.state.text}
                </div>
            </div >
        )
    }
}

export default Button