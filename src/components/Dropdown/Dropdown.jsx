import React from 'react'

import expand from '../../assets/icons/expand_more_black_24dp.svg'
import less from '../../assets/icons/expand_less_black_24dp.svg'

import styles from './Dropdown.module.css'

class Dropdown extends React.Component {
    constructor(props) {
        super(props)

        this.state = {
            items: props.items,
            selected: props.selected,
            menu: null,
            opened: false
        }
    }

    selectItem = (item) => {
        this.setState({
            opened: false,
            selected: item
        })

        this.props.onSelect(item)
    }

    generateDropdown = () => {
        let menu = []
        this.state.items.forEach(element => {
            menu.push(<div onClick={() => this.selectItem(element)} key={element} className={styles.menuItems} >{element}</div>)

        });

        this.setState({
            menu: menu
        })
    }



    expand = () => {
        this.setState({
            opened: !this.state.opened
        })
    }

    componentDidMount() {
        this.generateDropdown()
    }

    render() {
        return (
            <div style={this.props.inlineContainerStyle}>
                <div onClick={this.expand} className={styles.dropdown} style={this.props.inlineDropdownStyle}>
                    <div className={styles.selected}>{this.state.selected}</div>
                    {this.state.opened ? <img className={styles.icons} src={less} alt={less} /> : <img className={styles.icons} src={expand} alt={expand} />}
                </div>
                <div className={this.state.opened ? styles.menu : styles.menuClosed} style={this.props.inlineMenuStyle}>
                    {this.state.menu}
                </div>
            </div>
        )
    }
}

export default Dropdown