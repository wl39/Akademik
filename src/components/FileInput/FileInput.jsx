import styles from "./FileInput.module.css";
import addCircle from "../../assets/icons/add_circle_black_24dp.svg";
import React from "react";

class FileInput extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            filespath: [],
            fileData: {
                filename: "submission.zip",
                base64Value: ""
            },
            inlineStyle: props.inlineStyle ? props.inlineStyle : null
        }
    }

    fileHandler = (event) => {
        let inputs = document.querySelectorAll('#file');
        let fileName = '';

        Array.prototype.forEach.call(inputs, (input) => {
            let label = input.nextElementSibling;
            label = label.children[1];
            let labelVal = label.innerHTML;

            if (event.target.files && event.target.files.length > 1)
                fileName = (event.target.getAttribute('data-multiple-caption') || '').replace('{count}', event.target.files.length);
            else
                fileName = event.target.value.split('\\').pop();

            if (fileName)
                label.innerHTML = fileName;
            else
                label.innerHTML = labelVal;
        });

        let eachFile;
        let filePaths = []
        if (inputs[0].files.length > 1) {
            for (eachFile in inputs[0].files) {
                if (inputs[0].files[eachFile])
                    if (inputs[0].files[eachFile].webkitRelativePath)
                        filePaths.push(inputs[0].files[eachFile].webkitRelativePath)
            }
        }

        let reader = new FileReader();
        reader.readAsDataURL(event.target.files[0]);
        reader.onload = () => {
            let fileResult = reader.result;

            fileResult = fileResult.split(',');

            let mime = fileResult[0].split(':')[1];
            mime = mime.split(';')[0];

            let fileData = {
                mime: mime,
                filename: fileName,
                base64Value: fileResult[1],
                isDir: false,
            }

            this.setState(prevState => ({
                filespath: [...prevState.filespath, ...filePaths],
                fileData: fileData
            }))

            this.props.getFileDetails(fileData)
        }

        reader.onerror = (error) => {
            console.log('error: ', error);
        }

    }

    render() {
        return (
            <div className={styles.secondFlexContainer} style={this.state.inlineStyle}>
                <div>Files</div>
                <input onChange={this.fileHandler} className={styles.inputfile} type="file" id="file"
                    accept="zip,application/octet-stream,application/zip,application/x-zip,application/x-zip-compressed" />
                <label htmlFor="file">
                    <img className={styles.icon} src={addCircle} alt="adding" />
                    <div className={styles.folderDescription}>Select a Zip File</div>
                </label>
            </div>
        )
    }
}

export default FileInput;