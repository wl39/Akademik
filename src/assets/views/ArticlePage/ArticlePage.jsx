import React from "react";

import Card from "../../../components/Card/Card";

import styles from "./ArticlePage.module.css";
import leftArrow from "../../icons/keyboard_arrow_left_black_24dp.svg";
import rightArrow from "../../icons/keyboard_arrow_right_black_24dp.svg";
import file from "../../icons/insert_drive_file_black_24dp.svg";
import folder from "../../icons/folder_black_24dp.svg";
import reply from "../../icons/reply_black_24dp.svg";
import { Document, Page } from "react-pdf";
import {
  getArticle,
  postDiscussion,
  getDiscussions,
  getFiles,
  downloadArticleJSON,
  getFileComments,
  postFileComment,
  editFileComment,
  createNewVersion,
  migrateVersion,
} from "../../../axios/group20Axios";
import Button from "../../../components/Button/Button";

import accountIcon from "../../icons/account_circle_black_24dp.svg";
import editIcon from "../../icons/mode_edit_black_24dp.svg";

import TextField from "../../../components/TextField/TextField";
import { connect } from "react-redux";
import Dropdown from "../../../components/Dropdown/Dropdown";
import { userNavigate } from "../../../store/modules/login";
import Modal from "../../../components/Modal/Modal";

import FileInput from "../../../components/FileInput/FileInput";
import { withRouter } from "../../../utils/withRouter";

const actionButtons = {
  lineHeight: "40px",
  padding: "0 10px",
};

const suggestEditsField = {
  fontSize: "14px",
};

class ArticlePage extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      descriptionHTML: "",
      commentsHTML: null,
      comments: "",
      codeCommentsHTML: "",
      currentTab: "Description",
      codeHTML: null,
      code: [],
      currentHTML: "",
      numPages: null,
      pageNumber: 1,
      codePath: "/",
      width: window.innerWidth * 0.5625 - 70,
      visible: false,
      codeComments: false,
      login: {},
      dropdownMenu: null,
      versionNames: [],
      versionController: [],
      selectedVersion: "",
      pdfPath: "",

      // for migration
      migrateGroupNames: [
        "Group 2",
        "Group 5",
        "Group 8",
        "Group 11",
        "Group 13",
        "Group 17",
        "Group 23",
        "Group 26",
      ],
      migrateGroup: "2",
      migrateDropdown: null,

      ///for exporting Article
      name: "",
      creationDate: "",
      authorName: "",
      versionDate: "",
      files: {},
      currentFileID: -1,
      editStatus: 0,

      // Comments
      versionID: 0,
      filePath: "/",
      userid: this.props.login.userid,
      discussion: "",
      startLine: 0,
      endLine: 0,

      // for new version
      newVersionIsMajor: false,
      showAddNewVersionModal: false,
      topVersionID: 0,
    };

    this.state = {
      ...this.state,
    };

    const { userNavigate } = this.props;

    let userInfo = {
      redirect: this.props.params.title,
    };

    userNavigate(userInfo);
  }

  addCommentsButtonHandler = () => {
    this.setState((prevState) => ({
      visible: !prevState.visible,
      startLine: 0,
      discussion: "",
      endLine: 0,
      editStatus: 0,
    }));
  };

  submitComments = () => {
    let tempState = { ...this.state };

    tempState.filePath = tempState.filePath.substring(1);
    if (this.state.editStatus) {
      editFileComment(this.postFileCommentCallback, tempState);
    } else {
      postFileComment(this.postFileCommentCallback, tempState);
    }

    this.setState((prevState) => ({
      visible: !prevState.visible,
      startLine: 0,
      discussion: "",
      true: 0,
      editStatus: 0,
    }));
  };

  postFileCommentCallback = (data) => {
    if (!data.state) {
      alert("Submitted");
      this.setState({
        discussion: "",
      });

      this.getCodeComments(this.state.filePath);
    }
  };

  getCodes = (codes, startLine, endLine) => {
    let discussionCodeArray = [];

    for (let start = startLine; start <= endLine; start++) {
      discussionCodeArray.push(<div>{codes[start]}</div>);
    }

    return discussionCodeArray;
  };

  commentsHandler = (event) => {
    this.setState({ comments: event.target.value });
  };

  pageDown = () => {
    if (this.state.pageNumber > 1) {
      this.setState({
        pageNumber: this.state.pageNumber - 1,
      });
    }
  };

  pageUp = () => {
    if (this.state.pageNumber < this.state.numPages.numPages) {
      this.setState({
        pageNumber: this.state.pageNumber + 1,
      });
    }
  };

  onDocumentLoadSuccess = (numPages) => {
    this.setState({
      numPages: numPages,
    });
  };

  codeCommentsHandler = (event) => {
    this.setState({
      discussion: event.target.value,
    });
  };

  startLineHandler = (event) => {
    this.setState({
      startLine: parseInt(event.target.value),
    });
  };

  endLineHandler = (event) => {
    this.setState({
      endLine: parseInt(event.target.value),
    });
  };

  tabClicked = (tab) => {
    switch (tab) {
      case "Description":
        this.setState({
          currentTab: tab,
          currentHTML: this.state.descriptionHTML,
        });
        break;
      case "Article":
        this.setState({
          currentTab: tab,
          currentHTML: null,
        });
        break;
      case "Code":
        this.codeDirGenerator();

        this.setState({
          currentTab: tab,
          currentHTML: null,
          codeComments: false,
        });
        break;
      case "Comments":
        this.setState({
          currentTab: tab,
          currentHTML: this.state.commentsHTML,
        });

        break;
      default:
        break;
    }
    this.setState({
      currentTab: tab,
    });
  };

  editInlineComments = (value) => {
    this.setState({
      editStatus: value.messageID,
      startLine: value.lineStart,
      endLine: value.lineEnd,
      discussion: value.text,
      visible: true,
    });
  };

  getFileCommentsCallback = (data, codeArray) => {
    let discussionArray = [];

    data.forEach((value) => {
      discussionArray.push(
        <div className={styles.discussionContainer}>
          <div>{value.timeStamp}</div>
          <div className={styles.commentsLineAuthorContainer}>
            <div className={styles.lineHeader}>
              [LINE {value.lineStart}-{value.lineEnd}]
            </div>
            <div className={styles.userContainer}>
              <div className={styles.authorID}>{value.email}</div>
              <img src={accountIcon} alt={"accountIcon"} />
              {value.userID === this.props.login.userid ? (
                <img
                  src={editIcon}
                  alt={"editIcon"}
                  className={styles.editIcon}
                  onClick={() => this.editInlineComments(value)}
                />
              ) : null}
            </div>
          </div>

          <div className={styles.actualComments}>{value.text}</div>

          <div className={styles.lineComments}>
            {this.getCodes(codeArray, value.lineStart - 1, value.lineEnd - 1)}
          </div>
        </div>
      );
    });

    let codeCommentsHTML;
    codeCommentsHTML = (
      <div>
        <div className={styles.commentsHeader}>
          <div className={styles.commentsFilePath}>{this.state.filePath}</div>
          <div className={styles.commentsButtonContainer}>
            <Button
              inlineStyle={{
                fontSize: "14px",
                padding: "0px 10px",
                height: "30px",
              }}
              text="Suggest edits"
              onclick={this.addCommentsButtonHandler}
            />
          </div>
        </div>
        <div className={styles.commentsCardContainer}>
          <Card
            key={"code"}
            inlineStyles={{ width: "calc(67% - 90px)", padding: "42px 40px" }}
            styleName="cardWhite article-round relative shadow"
          >
            <div>{codeArray}</div>
          </Card>
          <Card
            key={"inline-comments"}
            inlineStyles={{
              width: "calc(33% - 64px)",
              padding: "24px 27px",
              fontSize: "12px",
            }}
            styleName="cardWhite article-round relative shadow"
          >
            <div>
              {discussionArray.length
                ? discussionArray
                : "There is no comment."}
            </div>
          </Card>
        </div>
      </div>
    );

    this.setState({
      codeCommentsHTML: codeCommentsHTML,
    });
  };

  getCode = (data, filePath) => {
    let codeArray = [];

    let codes = data.split("\n");

    for (let line in codes) {
      codeArray.push(
        <div className={styles.commentsCodeContainer}>
          <div className={styles.lineNumber}>{parseInt(line) + 1}</div>
          <div>{codes[line]}</div>
        </div>
      );
    }

    this.setState({
      filePath: filePath,
    });

    getFileComments(
      this.getFileCommentsCallback,
      this.props.login.userid,
      this.state.versionID,
      filePath.substring(1),
      codeArray
    );
  };

  getCodeComments = (filePath) => {
    getFiles(
      this.getCode,
      this.props.login.userid,
      this.state.versionID + filePath,
      false,
      filePath
    );

    this.setState({
      codeComments: true,
    });
  };

  getNestedFiles = (filename) => {
    getFiles(
      this.getFilesCallback,
      this.props.login.userid,
      this.state.versionID + filename,
      true
    );

    this.setState({
      codePath: filename,
    });
  };

  getFilesCallback = (data) => {
    if (data.status) {
      this.setState({
        codeHTML: <div>There is no file.</div>,
        files: data,
      });

      return;
    }

    let tempDir = [];

    if (this.state.codePath !== "/") {
      let tempIndex = this.state.codePath.lastIndexOf("/");
      let prevPath = this.state.codePath.substring(0, tempIndex);

      tempIndex = prevPath.lastIndexOf("/");
      prevPath = prevPath.substring(0, tempIndex);

      tempDir.push(
        <div
          className={styles.codeContainer}
          key="prev"
          onClick={() => this.getNestedFiles(prevPath + "/")}
        >
          <div>..</div>
        </div>
      );
    }

    data.forEach((value) => {
      tempDir.push(
        value.isDir ? (
          <div
            className={styles.codeContainer}
            key={value.filename}
            onClick={() =>
              this.getNestedFiles(this.state.codePath + value.filename + "/")
            }
          >
            {<img className={styles.icon} src={folder} alt="folder" />}
            <div>{value.filename}</div>
          </div>
        ) : (
          <div
            className={styles.codeContainer}
            key={value.filename}
            onClick={() =>
              this.getCodeComments(this.state.codePath + value.filename)
            }
          >
            {<img className={styles.icon} src={file} alt="file" />}
            <div>{value.filename}</div>
          </div>
        )
      );
    });

    this.setState({
      codeHTML: <div>{tempDir}</div>,
      files: data,
    });
    getDiscussions(
      this.getDiscussionsCallback,
      this.props.login.userid,
      this.state.versionID
    );
  };

  getPDFPathCallback = (data) => {
    if (data.status) {
      return;
    }

    data.forEach((value) => {
      if (!value.isDir) {
        if (value.filename.split(".").pop() === "pdf") {
        }
      }
    });
  };

  getPDFPath = () => {
    getFiles(
      this.getPDFPathCallback,
      this.props.login.userid,
      this.state.versionID + (this.state.codePath ? this.state.codePath : ""),
      true
    );
  };

  codeDirGenerator = (versionID, codePath) => {
    // TODO isDir needs to indicate whether the file is a directory or not
    if (versionID) {
      getFiles(
        this.getFilesCallback,
        this.props.login.userid,
        versionID + (codePath ? codePath : ""),
        true
      );
    } else {
      getFiles(
        this.getFilesCallback,
        this.props.login.userid,
        this.state.versionID + (this.state.codePath ? this.state.codePath : ""),
        true
      );
    }
  };

  getArticleCallback = (data) => {
    // populate divs with pulled article data from database

    if (data.status) {
      return;
    }

    let authors = [];

    data.authorsArray.forEach((author, index) => {
      authors.push(
        <div
          className={
            data.authorsArray.length - 1 === index ? null : styles.author
          }
          key={author + index}
        >
          {author}
        </div>
      );
    });

    let descriptionHTML;

    descriptionHTML = (
      <Card
        key={data.name}
        inlineStyles={{
          width: "calc(100% - 70px)",
          padding: "30px 35px",
          marginBottom: "10px",
        }}
        styleName="cardWhite article-round relative shadow"
      >
        <div className={styles.title}>{data.title}</div>
        <div className={styles.authorsContainer}>
          <div className={styles.authors}>Authors:</div>
          <div className={styles.authorsContainers}>{authors}</div>
        </div>
        <div className={styles.contents}>{data.abstract}</div>
        <div className={styles.keywordsContainer}>
          <div className={styles.keywords}>License:</div>
          <div className={styles.keywordContainer}>{data.license}</div>
        </div>
      </Card>
    );

    let versionNames = [];
    let versionController = {};

    data.versions.forEach((value) => {
      versionNames.push(
        value.version + "." + value.subVersion + "." + value.versionID
      );

      versionController[
        value.version + "." + value.subVersion + "." + value.versionID
      ] = value.versionID;
    });

    this.setState({
      descriptionHTML: descriptionHTML,
      currentHTML: descriptionHTML,
      name: data.title,
      authorName: data.authorsArray[0],
      creationDate: data.creationDate,
      versionDate: data.creationDate,
      versionID: data.topVersionID,
      topVersionID: data.topVersionID,
      versionNames: versionNames,
      versionController: versionController,
      dropdownMenu: (
        <Dropdown
          onSelect={this.selected}
          items={versionNames}
          selected={versionNames[versionNames.length - 1]}
          inlineDropdownStyle={{
            width: "120px",
            height: "40px",
            backgroundColor: "white",
            borderRadius: "5px",
            paddingLeft: "10px",
            boxShadow: "0px 3px 10px rgba(0, 0, 0, .2)",
          }}
          inlineMenuStyle={{ width: "120px" }}
        />
      ),
    });

    getDiscussions(
      this.getDiscussionsCallback,
      this.props.login.userid,
      this.state.versionID
    );

    return data;
  };

  postDiscussionCallback = (data) => {
    if (!data.status) {
      alert("Submitted");
      this.setState({
        comments: "",
      });
      getDiscussions(
        this.getDiscussionsCallback,
        this.props.login.userid,
        this.state.versionID
      );
    }
  };

  postDiscussionHandler = (parentID) => {
    if (this.props.login.username) {
      postDiscussion(
        this.postDiscussionCallback,
        this.state.versionID,
        this.props.login.userid,
        this.state.comments,
        parentID
      );
    } else {
      alert("You have to login first");
    }
  };

  getDiscussionsCallback = (data) => {
    let commentsHTML = [];

    data.forEach((value) => {
      if (value.parentID === 0) {
        commentsHTML.push(
          <div key={value.messageID}>
            <div className={styles.familyContainer}>
              <div className={styles.cIconContainer}>
                <img
                  className={styles.cIcon}
                  src={accountIcon}
                  alt="commentA"
                />
              </div>

              <div className={styles.cTextareaContainer}>
                <div>{value.email}</div>
                <div>{value.text}</div>
              </div>
            </div>
            <div className={styles.replyButtonContainer}>
              <Button
                inlineStyle={{
                  marginLeft: "auto",
                  width: "75px",
                  height: "36px",
                  lineHeight: "32px",
                  fontSize: "18px",
                }}
                text="reply"
                onclick={() => this.postDiscussionHandler(value.messageID)}
              />
            </div>
          </div>
        );
      } else {
        commentsHTML.forEach((eachTag, index) => {
          if (eachTag.key === value.parentID.toString()) {
            commentsHTML.splice(
              index + 1,
              0,
              <div key={value.messageID}>
                <div className={styles.childContainer} key={value.messageID}>
                  <img className={styles.replyIcon} src={reply} alt="reply" />
                  <div className={styles.cIconContainer}>
                    <img
                      className={styles.cIcon}
                      src={accountIcon}
                      alt="commentA"
                    />
                  </div>

                  <div className={styles.cTextareaContainer}>
                    <div>{value.email}</div>
                    <div>{value.text}</div>
                  </div>
                </div>
                <div className={styles.replyButtonContainer}>
                  <Button
                    inlineStyle={{
                      marginLeft: "auto",
                      width: "75px",
                      height: "36px",
                      lineHeight: "32px",
                      fontSize: "18px",
                    }}
                    text="reply"
                    onclick={() => this.postDiscussionHandler(value.messageID)}
                  />
                </div>
              </div>
            );
          }
        });
      }
    });

    this.setState({
      commentsHTML: commentsHTML,
    });

    this.getPDFPath();
  };

  downloadArticleJSONCallback = (data) => {
    if (!data.status) {
      const element = document.createElement("a");
      const file = new Blob([JSON.stringify(data)], { type: "text/plain" });
      element.href = URL.createObjectURL(file);
      element.download = "article.json";
      document.body.appendChild(element);
      element.click();
    } else {
      alert("Download unsuccessful.");
    }
  };

  migrateVersionCallback = (data) => {
    if (!data.status) {
      alert("Successfully migrated to group " + this.state.migrateGroup);
    } else {
      alert("Migration unsuccessful.");
    }
  };

  download = () => {
    downloadArticleJSON(this.downloadArticleJSONCallback, this.state);
  };

  migrate = () => {
    migrateVersion(this.migrateVersionCallback, this.state);
  };

  addVersion = () => {
    this.setState({
      showAddNewVersionModal: true,
    });
  };

  selected = (value) => {
    this.setState({
      selectedVersion: value,
      versionID: this.state.versionController[value],
      codePath: "/",
      codeComments: false,
    });

    this.codeDirGenerator(this.state.versionController[value], "/");
  };

  migrateSelected = (value) => {
    const regex = "\\d+";
    const matched_group = value.match(regex);
    this.setState({
      migrateGroup: matched_group[0],
    });
  };

  componentDidMount() {
    if (this.props.login.userid < 0) {
      alert("You need to login first.");

      this.props.history.push("/login");

      return null;
    }

    const { userNavigate } = this.props;

    let userInfo = {
      redirect: 0,
    };

    userNavigate(userInfo);

    getArticle(
      this.getArticleCallback,
      this.props.login.userid,
      this.props.params.title
    );

    window.addEventListener("resize", () => {
      this.setState({
        width: window.innerWidth * 0.5625 - 70,
      });
    });
  }

  majorMinorToggleHandler = (event) => {
    this.setState({
      newVersionIsMajor: event.target.checked,
    });
  };

  hideAddNewVersionModal = () => {
    this.setState({ showAddNewVersionModal: false });
  };

  getFileDetails = (value) => {
    this.setState({
      files: value,
    });
  };

  createNewVersionCallback = (data) => {
    if (!data.status) {
      alert("Success: New version submitted.");
    }
  };

  tryChangeDetails = () => {
    createNewVersion(
      this.createNewVersionCallback,
      this.props.login.userid,
      this.state.versionID,
      this.state.files
    );
  };

  render() {
    return (
      <div
        className={
          this.state.codeComments ? styles.articleComment : styles.articlePage
        }
      >
        <Modal
          show={this.state.showAddNewVersionModal}
          handleClose={this.hideAddNewVersionModal}
          style={{ width: "300px" }}
        >
          <div style={{ display: "flex", flexDirection: "column" }}>
            <p style={{ fontWeight: "bold", textAlign: "center" }}>
              Submit a new version
            </p>

            <FileInput
              getFileDetails={(value) => this.getFileDetails(value)}
              inlineStyle={{ margin: "20px 0" }}
            />
            <Button
              onclick={this.tryChangeDetails}
              text={"Submit"}
              styleName={"black"}
            >
              inlineStyle={{ padding: "0 10px" }}
            </Button>
          </div>
        </Modal>
        <div className={styles.articleHeader}>
          <div
            className={
              this.state.currentTab === "Description"
                ? styles.descriptionActive
                : styles.descriptionInactive
            }
            onClick={() => this.tabClicked("Description")}
          >
            Description
          </div>
          <div
            className={
              this.state.currentTab === "Article"
                ? styles.articleActive
                : styles.articleInactive
            }
            onClick={() => this.tabClicked("Article")}
          >
            Article
          </div>
          <div
            className={
              this.state.currentTab === "Code"
                ? styles.codeActive
                : styles.codeInactive
            }
            onClick={() => this.tabClicked("Code")}
          >
            Code
          </div>
          <div
            className={
              this.state.currentTab === "Comments"
                ? styles.commentsActive
                : styles.commentsInactive
            }
            onClick={() => this.tabClicked("Comments")}
          >
            Comments
          </div>
        </div>
        <div className={styles.articleContainer}>
          {this.state.visible ? (
            <Card
              inlineStyles={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "space-between",
                position: "fixed",
                zIndex: "2",
                right: "20px",
                top: "223px",
                width: "220px",
                borderRadius: "10px",
                padding: "20px",
              }}
              styleName="cardWhite article-round relative shadow"
            >
              <div className={styles.editLineContainer}>
                <TextField
                  inlineStyle={suggestEditsField}
                  onchange={this.startLineHandler}
                  type="number"
                  placeholder="Start Line"
                  value={this.state.startLine ? this.state.startLine : ""}
                />
                <TextField
                  inlineStyle={suggestEditsField}
                  onchange={this.endLineHandler}
                  type="number"
                  placeholder="End Line"
                  value={this.state.endLine ? this.state.endLine : ""}
                />
              </div>
              <textarea
                className={styles.suggestEditTextArea}
                placeholder="Comment"
                onChange={this.codeCommentsHandler}
                value={this.state.discussion}
              />
              <Button
                onclick={this.submitComments}
                inlineStyle={{ width: "140px", marginLeft: "auto" }}
                text="Submit"
              />
            </Card>
          ) : null}
          {this.state.currentTab === "Comments" ? null : this.state.currentHTML}
          {this.state.currentTab === "Article" ? (
            <Card
              inlineStyles={{
                width: "calc(100% - 70px)",
                padding: "30px 35px",
                marginBottom: "10px",
              }}
              styleName="cardWhite article-round relative shadow"
            >
              {this.state.pdfPath ? (
                <Document
                  file={this.state.pdfPath}
                  onLoadSuccess={this.onDocumentLoadSuccess}
                  className={styles.document}
                >
                  <Page
                    className={styles.page}
                    pageNumber={this.state.pageNumber}
                    margin="auto"
                    width={this.state.width}
                  />
                  <div className={styles.pageController}>
                    <img
                      className={styles.leftArrow}
                      onClick={this.pageDown}
                      src={leftArrow}
                      alt="left_arrow"
                    />
                    <div className={styles.pageNumber}>
                      {this.state.pageNumber}
                    </div>
                    <img
                      className={styles.rightArrow}
                      onClick={this.pageUp}
                      src={rightArrow}
                      alt="right_arrow"
                    />
                  </div>
                </Document>
              ) : (
                <div>NO PDF ARTICLE</div>
              )}
            </Card>
          ) : null}
          {this.state.currentTab === "Code" ? (
            this.state.codeComments ? (
              this.state.codeCommentsHTML
            ) : (
              <Card
                inlineStyles={{
                  width: "calc(100% - 70px)",
                  padding: "30px 35px",
                  marginBottom: "10px",
                }}
                styleName="cardWhite article-round relative shadow"
              >
                <div className={styles.path}>{this.state.codePath}</div>
                <div>{this.state.codeHTML}</div>
              </Card>
            )
          ) : null}
          {this.state.currentTab === "Comments" ? (
            <Card
              inlineStyles={{
                display: "flex",
                flexDirection: "column",
                width: "calc(100% - 70px)",
                padding: "30px 35px",
                marginBottom: "10px",
              }}
              styleName="cardWhite article-round relative shadow"
            >
              <div className={styles.cContainer}>
                <div className={styles.cIconContainer}>
                  <img
                    className={styles.cIcon}
                    src={accountIcon}
                    alt="commentA"
                  />
                </div>

                <div className={styles.cTextareaContainer}>
                  <div>
                    {this.props.login.username
                      ? this.props.login.username
                      : "You have to login first"}
                  </div>
                  <textarea
                    className={styles.customTextarea}
                    placeholder="Description"
                    onChange={this.commentsHandler}
                    value={this.state.comments}
                  />
                </div>
              </div>
              <Button
                inlineStyle={{
                  marginLeft: "auto",
                  width: "150px",
                  height: "45px",
                  lineHeight: "45px",
                  marginBottom: "15px",
                }}
                text="Comment"
                onclick={() => this.postDiscussionHandler()}
              />
              {this.state.commentsHTML}
            </Card>
          ) : null}
          <div
            style={{
              display: "flex",
              justifyContent: "right",
              marginTop: "20px",
            }}
          >
            <Button
              text="Download"
              styleName="black"
              onclick={this.download}
              inlineStyle={actionButtons}
            />
            <div className={styles.migrateContainer}>
              <Dropdown
                onSelect={this.migrateSelected}
                items={this.state.migrateGroupNames}
                selected={this.state.migrateGroupNames[0]}
                inlineContainerStyle={{ width: "150px" }}
                inlineDropdownStyle={{
                  height: "40px",
                  backgroundColor: "white",
                  borderRadius: "5px",
                  paddingLeft: "10px",
                  boxShadow: "0px 3px 10px rgba(0, 0, 0, .2)",
                }}
                inlineMenuStyle={{ width: "inherit" }}
              />
              <Button
                text="Migrate"
                styleName="black"
                onclick={this.migrate}
                inlineStyle={actionButtons}
              />
            </div>
            <p style={{ paddingRight: "20px" }}>Version: </p>
            {this.state.dropdownMenu}
            <Button
              text="+"
              styleName="black"
              onclick={this.addVersion}
              inlineStyle={actionButtons}
            />
          </div>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state) => ({
  login: state.login,
});

const mapDispatchToProps = (dispatch) => ({
  userNavigate: (userInfo) => dispatch(userNavigate(userInfo)),
});

export default withRouter(
  connect(mapStateToProps, mapDispatchToProps)(ArticlePage)
);
