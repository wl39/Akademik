import styles from './MultiDropdown.module.css';
import React from "react";
import Checkbox from '../Checkbox/Checkbox';

class MultiDropdown extends React.Component {
    constructor(props) {
        super(props)

        this.state = {
            items: props.items,
            selectedTag: [],
            placehold: props.placehold,
            menu: null,
            values: {},
            opened: false,
            entered: false,
        }

        this.setWrapperRef = this.setWrapperRef.bind(this);
        this.handleClickOutside = this.handleClickOutside.bind(this);
    }

    enter = () => {
        this.setState({
            entered: true
        })
    }

    leave = () => {
        this.setState({
            entered: false
        })
    }

    temp = (item) => {

        let values = { ...this.state.values }

        let selectedTag = []

        values[item] = false

        document.getElementById(item).checked = false


        for (let key in values) {
            if (values[key]) {
                selectedTag.push(
                    <div key={key} onmouse={this.enter} onMouseLeave={this.leave} className={styles.selectedValue} onClick={() => this.temp(key)}>
                        <div className={styles.selectedKey}>{key}</div>
                    </div>
                )
            }
        }

        this.setState({
            values: values,
            selectedTag: selectedTag,
            entered: false,
        })

        this.props.onchange(values)


    }

    selectItem = (item) => {
        if (this.props.disabled && !this.props.values[item]) {
            this.props.disableAlert()
        }

        if (!this.props.disabled || this.props.values[item]) {
            let values = { ...this.state.values }


            let selectedTag = []

            document.getElementById(item).checked = !values[item]

            values[item] = !values[item]

            for (let key in values) {
                if (values[key]) {
                    selectedTag.push(
                        <div key={key} onMouseEnter={this.enter} onMouseLeave={this.leave} className={styles.selectedValue} onClick={() => this.temp(key)}>
                            <div className={styles.selectedKey}>{key}</div>
                        </div>
                    )
                }
            }

            this.setState({
                values: values,
                selectedTag: selectedTag
            })

            this.props.onchange(values)
        }
    }

    selectItemNoDisplay = (item) => {
        if (this.props.disabled && !this.props.values[item]) {
            this.props.disableAlert()
        }

        if (!this.props.disabled || this.props.values[item]) {
            let values = { ...this.state.values }


            let selectedTag = []

            document.getElementById(item).checked = !values[item]

            values[item] = !values[item]

            this.setState({
                values: values,
                selectedTag: selectedTag
            })

            this.props.onchange(values)
        }
    }

    checkboxHandler = (event) => {

        let values = { ...this.state.values }

        values[event.target.id] = event.target.checked

        this.setState({
            values: values
        })
    }

    generateDropdown = () => {
        let menu = []
        let values = {}

        this.state.items.forEach(element => {
            values[element] = false
            menu.push(

                <div onClick={() => this.props.noDisplay ? this.selectItemNoDisplay(element) : this.selectItem(element)} key={element} className={styles.menuItems} >
                    <div>{element}</div>
                    <Checkbox id={element} onclick={this.checkboxHandler} disabled={!this.props.values[element]} />
                </div>
            )

        });

        this.setState({
            menu: menu,
            values: values
        })
    }

    expand = () => {
        if (!this.state.entered)
            this.setState({
                opened: !this.state.opened
            })
    }


    componentDidMount() {
        this.generateDropdown();
        document.addEventListener("mousedown", this.handleClickOutside);
    }

    componentWillUnmount() {
        document.removeEventListener("mousedown", this.handleClickOutside);
    }

    setWrapperRef(node) {
        this.wrapperRef = node;
    }

    handleClickOutside(event) {
        if (this.wrapperRef && !this.wrapperRef.contains(event.target)) {
            this.setState({
                opened: false
            })
        }
    }


    render() {
        return (
            <div ref={this.setWrapperRef} style={this.props.inlineStyles}>
                <div onMouseEnter={this.test} onClick={this.expand} className={styles.dropdown} style={this.props.inlineDropdownStyle}>
                    <div className={this.state.selectedTag.length ? styles.selected : styles.none}>{this.state.selectedTag.length ? this.state.selectedTag : this.state.placehold}</div>
                </div>
                <div className={this.state.opened ? styles.menu : styles.menuClosed}>
                    {this.state.menu}
                </div>
            </div>
        )
    }
}

export default MultiDropdown