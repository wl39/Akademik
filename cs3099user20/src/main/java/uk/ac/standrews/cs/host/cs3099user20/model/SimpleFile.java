package uk.ac.standrews.cs.host.cs3099user20.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.File;

import static uk.ac.standrews.cs.host.cs3099user20.service.InjectionPrevention.injectionPrevention;

public class SimpleFile {

        @Schema(example = "tea/ExampleNiceGUI.java", description = "File name")
        String filename;
        @Schema(example = "true", description = "Represents if file is a directory")
        @JsonProperty("isDir")
        boolean isDir;

        public SimpleFile(String filename, boolean isDir) {
                this.filename = injectionPrevention(filename);
                this.isDir = isDir;
        }

        public SimpleFile(String filename) {
                this.filename = filename;
                File file = new File(filename);
                this.isDir = file.isDirectory();
        }

        public String getFilename() {
                return filename;
        }

        public void setFilename(String filename) {
                this.filename = injectionPrevention(filename);
        }

        @JsonProperty(value="isDir")
        public boolean isDir() {
                return isDir;
        }

        public void setIsDir(boolean isDir) {
                this.isDir = isDir;
        }
}
