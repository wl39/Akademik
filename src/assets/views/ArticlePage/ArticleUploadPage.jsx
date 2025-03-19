import React from 'react'

import styles from './ArticleUploadPage.module.css'

import addCircle from '../../icons/add_circle_black_24dp.svg'

import Card from "../../../components/Card/Card";
import TextField from '../../../components/TextField/TextField';
import { createArticle, getEmail } from "../../../axios/group20Axios";
import Button from "../../../components/Button/Button";

import { connect } from 'react-redux';
import FileInput from "../../../components/FileInput/FileInput";

const submitArticleButton = {
    fontSize: "18px",
    padding: "5px 15px",
    float: "right"
}

const textField = {
    fontSize: "14px",
    height: "30px",
    width: "100%"
}

class ArticleUploadPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            title: '',
            abstract: "",
            authorID: '',
            authors: [this.props.login.userEmail],
            reviewerID: '',
            category: '',
            filespath: [],
            license: "",
            fileData: {
                filename: "submission.zip",
                base64Value: ""
            },
            userid: props.login.userid
        }
    }

    componentDidMount() {
        if (this.props.login.userid < 0) {
            this.props.history.push('/login')
            return null;
        }
    }

    createArticleCallback = (data) => {
        if (!data.state) {
            alert("Success: Article submitted.")

            this.props.history.push('/articles')
        }
    }

    submitArticle = () => {
        // TODO check title field
        if (!this.state.title) {
            alert("Title is empty.")
            return;
        }
        // TODO check author field
        // TODO check abstract field
        if (!this.state.abstract) {
            alert("Abstract is empty.")
            return;
        }
        // TODO check license field
        if (!this.state.license) {
            alert("License is empty.")
            return;
        }
        // TODO check files
        if (!this.state.fileData.base64Value) {
            alert("File is empty.")
            return;
        }
        // TODO have to check error in here

        createArticle(this.createArticleCallback, this.state)
    }

    titleHandler = (event) => {
        this.setState({
            title: event.target.value
        })
    }

    licenseHandler = (event) => {
        this.setState({
            license: event.target.value
        })
    }

    authorHandler = (event) => {
        let regex = /\s*(?:,|$)\s*/;

        let authors = event.target.value.split(regex);

        authors = authors.filter(e => e);

        authors.unshift(this.props.login.userEmail)

        this.setState({
            authorID: event.target.value,
            authorsArray: authors
        })
    }

    selected = (value) => {
        this.setState({
            license: value
        })
    }

    abstractHandler = (event) => {
        this.setState({
            abstract: event.target.value
        })
    }

    getEmailCallback = (data) => {
        let authors = this.state.authors

        if (!authors.includes(data.email))
            authors = [...authors, data.email]

        this.setState({
            authorsArray: authors,
            authorID: authors.length === 1 ? authors[0] : authors.toString().replaceAll(',', ', ')
        })
    }

    jsonFileHandler = (event) => {
        let fr = new FileReader()

        fr.readAsText(event.target.files[0])

        fr.onload = () => {
            let result = fr.result;

            let jsonFile = JSON.parse(result)

            let parsedFile = []

            jsonFile.metadata.authors.forEach(value => {
                getEmail(this.getEmailCallback, value.userId);
            })

            this.setState({
                title: jsonFile.name,
                filespath: parsedFile,
                license: jsonFile.metadata.license,
                abstract: jsonFile.metadata.abstract
            })
        }
    }

    getFileDetails = (value) => {

        this.setState({
            fileData: value
        })
    }

    render() {
        return (
            <div className={styles.main}>
                <div className={styles.vBox} style={{ flex: "3" }}>
                    <div className={styles.header}>Manual Submit</div>
                    <Card inlineStyles={{ height: "550px", padding: "30px", display: "inlineBlock", marginRight: "20px" }} styleName="cardArticleWhite article-round relative shadow">
                        <div className={styles.firstContainer}>
                            <div className={styles.firstFlexContainer}>
                                <div className={styles.inputContainer}>
                                    <div className={styles.containerHeader}>
                                        Article Title
                                    </div>
                                    <TextField
                                        inlineStyle={textField}
                                        onchange={this.titleHandler}
                                        placeholder="Title"
                                        value={this.state.title}
                                    />
                                </div>

                                <div className={styles.inputContainer}>
                                    <div className={styles.containerHeader}>
                                        Other Authors
                                    </div>
                                    <TextField
                                        inlineStyle={textField}
                                        onchange={this.authorHandler}
                                        placeholder="e.g. Aldiyar Ablyazov, Jim Cramer"
                                        value={this.state.authorID}
                                    />

                                </div>
                                <div className={styles.inputContainer}>
                                    <div className={styles.containerHeader}>
                                        License
                                    </div>
                                    <TextField
                                        inlineStyle={textField}
                                        onchange={this.licenseHandler}
                                        placeholder="e.g. MIT"
                                        value={this.state.license}
                                    />
                                </div>
                            </div>

                            <FileInput getFileDetails={(value) => this.getFileDetails(value)} />
                        </div>

                        <div className={styles.inputContainer}>
                            <div className={styles.containerHeader}>
                                abstract
                            </div>
                            <textarea
                                className={styles.customTextarea}
                                placeholder="abstract"
                                onChange={this.abstractHandler}
                                value={this.state.abstract}
                            />
                        </div>
                        <Button text="Submit Article"
                            inlineStyle={submitArticleButton}
                            styleName="black"
                            onclick={this.submitArticle}
                        />
                    </Card>
                </div>
                <div className={styles.vBox} style={{ flex: "1" }}>
                    <div className={styles.header}>Import Submit</div>
                    <Card inlineStyles={{ padding: "30px", display: "inlineBlock" }} styleName="cardArticleWhite article-round relative shadow">
                        <div className={styles.thirdFlexContainer}>

                            <div className={styles.jsonFileContainer}>
                                {/* tag for single file */}
                                <input onChange={this.jsonFileHandler} className={styles.inputfile} type="file" id="JSONfile" data-caption="{count} file selected" />
                                {/* tag for multiple files */}
                                {/* <input onChange={this.fileHandler} className={styles.inputfile} type="file" id="file" data-multiple-caption="{count} files selected" multiple directory="" webkitdirectory="" /> */}
                                <label style={{ height: "119px" }} htmlFor="JSONfile">
                                    <img style={{ marginTop: "20px" }} className={styles.icon} src={addCircle} alt="adding" />
                                    <div style={{ top: "75px" }} className={styles.folderDescription}>Select a JSON file</div>
                                    {/* <div className={styles.fileabstract}>Upload files</div> */}
                                </label>
                            </div>
                        </div>
                    </Card>
                </div>
            </div>
        )
    }
}

const mapStateToProps = state => ({
    login: state.login
})

export default connect(mapStateToProps)(ArticleUploadPage)