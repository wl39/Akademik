import axios from "axios";

const generalToken = "cs3099user20ThisIsSecret";
const modToken = "cs3099user20ThisIsSecreter";

// const URL_PREFIX = 'https://cs3099user20.host.cs.st-andrews.ac.uk';
const URL_PREFIX = "http://localhost:23417";

const postUsers = (callback, state) => {
  //already changed
  axios
    .post(
      URL_PREFIX + "/api/v1/users/register",
      {
        email: state.email,
        password: state.password,
        group: state.group,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const userExists = (callback, state) => {
  //already changed
  axios
    .post(
      URL_PREFIX + "/api/v1/users/exists",
      {
        email: state.email,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data, state);
    })
    .catch((error) => {
      handleError(error, callback, state);
    });
};

const login = (callback, state) => {
  //already changed
  axios
    .post(
      URL_PREFIX + "/api/v1/users/login",
      {
        email: state.email,
        password: state.password,
        group: state.group,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback, state);
    });
};

const logout = (callback, email) => {
  //already changed
  axios
    .post(
      URL_PREFIX + "/api/v1/users/logout",
      {
        email: email,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const createArticle = (callback, state) => {
  axios
    .post(
      URL_PREFIX + "/api/v1/" + state.userid + "/articles/create",
      {
        title: state.title,
        abstract: state.abstract,
        authorsArray: state.authorsArray,
        files: state.fileData,
        uploaderID: state.userid,
        license: state.license,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};
////
const createNewVersion = (callback, userid, versionid, fileData) => {
  axios
    .post(
      "https://cs3099user20.host.cs.st-andrews.ac.uk/api/v1/" +
        userid +
        "/create" +
        versionid,
      {
        files: fileData,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const applyReviewer = (callback, state) => {
  axios
    .post(
      URL_PREFIX + "/api/v1/" + state.userid + "/users/apply/reviewer",
      {
        email: state.email,
        institution: state.institution,
        expertise: state.fieldsOfExpertise,
        bio: state.biography,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const getArticle = (callback, userID, versionID) => {
  //already changed
  axios.defaults.headers = {
    "Cache-Control": "no-cache",
    Pragma: "no-cache",
    Expires: "0",
  };
  axios
    .get(URL_PREFIX + "/api/v1/" + userID + "/articles/get/" + versionID, {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
      },
    })
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const getArticles = (callback, userID, role, startID, quantity) => {
  axios
    .post(
      URL_PREFIX + "/api/v1/" + userID + "/articles/get/",
      {
        userID: userID,
        role: role,
        startID: startID,
        quantity: quantity,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "Content-Type": "application/json",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const migrateVersion = (callback, state) => {
  axios.defaults.headers = {
    "Cache-Control": "no-cache",
    Pragma: "no-cache",
    Expires: "0",
  };
  axios
    .get(
      URL_PREFIX +
        "/api/v1/" +
        state.userid +
        "/articles/get/" +
        state.versionID +
        "/" +
        state.migrateGroup +
        "/migrate",
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const getFiles = (callback, userID, filename, isDir, filePath) => {
  axios
    .post(
      URL_PREFIX + "/api/v1/" + userID + "/file/get/",
      {
        filename: filename,
        isDir: isDir,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "Content-Type": "application/json",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data, filePath);
    })
    .catch((error) => {
      handleError(error, callback, filePath);
    });
};

const getDiscussions = (callback, userID, versionID) => {
  //already changed
  axios.defaults.headers = {
    "Cache-Control": "no-cache",
    Pragma: "no-cache",
    Expires: "0",
  };

  axios
    .get(
      URL_PREFIX +
        "/api/v1/" +
        userID +
        "/articles/get/" +
        versionID +
        "/discussion",
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
          "Content-Type": "application/json",
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const getFileComments = (callback, userID, versionID, filename, code) => {
  axios
    .post(
      URL_PREFIX + "/api/v1/" + userID + "/file/comments",
      {
        versionID: versionID,
        filename: filename,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data, code);
    })
    .catch((error) => {
      handleError(error, callback, code);
    });
};

const postFileComment = (callback, state) => {
  // TODO fix
  axios
    .post(
      URL_PREFIX + "/api/v1/" + state.userid + "/file/create/comment/",
      {
        versionID: state.versionID,
        filename: state.filePath,
        userID: state.userid,
        text: state.discussion,
        lineStart: state.startLine,
        lineEnd: state.endLine,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const editFileComment = (callback, state) => {
  axios
    .put(
      URL_PREFIX + "/api/v1/" + state.userid + "/file/edit/comment/",
      {
        versionID: state.versionID,
        filename: state.filePath,
        userID: state.userid,
        text: state.discussion,
        lineStart: state.startLine,
        lineEnd: state.endLine,
        messageID: state.editStatus,
      },
      {
        headers: {
          "Access-Control-Allow-Methods": "PUT, POST, GET, DELETE, OPTIONS",
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const isRoleInArticle = (callback, versionID, userID) => {
  axios.defaults.headers = {
    "Cache-Control": "no-cache",
    Pragma: "no-cache",
    Expires: "0",
  };
  axios
    .get(
      URL_PREFIX + "/api/v1/articles/" + versionID + "/" + userID + "/role",
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const postDiscussion = (callback, versionID, authorID, text, parentID) => {
  //already changed
  axios
    .post(
      URL_PREFIX +
        "/api/v1/" +
        authorID +
        "/articles/create/" +
        versionID +
        "/discussion",
      {
        userID: authorID,
        text: text,
        parentID: parentID,
        versionID: versionID,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    });
  // .catch(error => {
  //     handleError(error, callback);
  // })
};

const changeEmail = (callback, state) => {
  // backend has to be changed
  axios
    .put(
      URL_PREFIX + "/api/v1/" + state.userid + "/users/update/",
      {
        email: state.newEmail,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const changePassword = (callback, state) => {
  axios
    .put(
      URL_PREFIX + "/api/v1/" + state.userid + "/users/update/",
      {
        password: state.newPassword,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const downloadArticleJSON = (callback, state) => {
  axios.defaults.headers = {
    "Cache-Control": "no-cache",
    Pragma: "no-cache",
    Expires: "0",
  };
  axios
    .get(
      URL_PREFIX +
        "/api/v1/" +
        state.userid +
        "/articles/get/" +
        state.versionID +
        "/download",
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const acceptReviewer = (callback, userID, reviewerID, email) => {
  axios
    .post(
      URL_PREFIX + "/api/v1/" + userID + "/moderator/acceptReviewer",
      {
        userID: userID,
        reviewerID: reviewerID,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": modToken,
        },
      }
    )
    .then((res) => {
      callback(res.data, email);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const rejectReviewer = (callback, userID, reviewerID, email) => {
  axios
    .post(
      URL_PREFIX + "/api/v1/" + userID + "/moderator/rejectReviewer",
      {
        userID: userID,
        reviewerID: reviewerID,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": modToken,
        },
      }
    )
    .then((res) => {
      callback(res.data, email);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const getReviewers = (callback, userID) => {
  axios.defaults.headers = {
    "Cache-Control": "no-cache",
    Pragma: "no-cache",
    Expires: "0",
  };
  axios
    .get(URL_PREFIX + "/api/v1/" + userID + "/moderator/reviewers", {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "X-FOREIGNJOURNAL-SECURITY-TOKEN": modToken,
      },
    })
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const addReviewers = (callback, userID, versionID, reviewers) => {
  axios
    .post(
      URL_PREFIX +
        "/api/v1/" +
        userID +
        "/moderator/reviewers/add/" +
        versionID,
      reviewers,
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": modToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const rejectArticle = (callback, userID, versionID) => {
  axios
    .delete(
      URL_PREFIX +
        "/api/v1/" +
        userID +
        "/moderator/rejectArticle/" +
        versionID,
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": modToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const getUnauthorisedReviewers = (callback, userID) => {
  axios.defaults.headers = {
    "Cache-Control": "no-cache",
    Pragma: "no-cache",
    Expires: "0",
  };

  axios
    .get(
      URL_PREFIX + "/api/v1/" + userID + "/moderator/unauthorised/reviewers",
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": modToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const voteArticle = (callback, email, userID, versionID, voteValue) => {
  axios
    .put(
      URL_PREFIX +
        "/api/v1/" +
        userID +
        "/articles/update/" +
        versionID +
        "/vote/" +
        voteValue,
      {
        email: email,
        id: userID,
      },
      {
        headers: {
          "Access-Control-Allow-Methods": "PUT, POST, GET, DELETE, OPTIONS",
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const search = (callback, state) => {
  axios
    .post(
      URL_PREFIX + "/api/v1/" + state.userid + "/articles/search",
      {
        searchString: state.value,
        groupNumbers: state.groupNumbers,
      },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
        },
      }
    )
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const getRole = (callback, userID) => {
  axios.defaults.headers = {
    "Cache-Control": "no-cache",
    Pragma: "no-cache",
    Expires: "0",
  };

  axios
    .get(URL_PREFIX + "/api/v1/" + userID + "/users/get/role", {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
      },
    })
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

const getEmail = (callback, userid) => {
  axios.defaults.headers = {
    "Cache-Control": "no-cache",
    Pragma: "no-cache",
    Expires: "0",
  };

  axios
    .get(URL_PREFIX + "/api/v1/users/get/" + userid, {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "X-FOREIGNJOURNAL-SECURITY-TOKEN": generalToken,
      },
    })
    .then((res) => {
      callback(res.data);
    })
    .catch((error) => {
      handleError(error, callback);
    });
};

function handleError(error, callback, opts) {
  if (error.response) {
    // Non-200 response received
    if (typeof opts === "undefined") {
      callback(error.response);
    } else {
      callback(error.response);
    }
  } else if (error.request) {
    // No response received
    console.log(error.request);
  } else {
    // Setting up request failed
    console.log("Error", error.message);
  }
}

export {
  postUsers,
  userExists,
  login,
  logout,
  createArticle,
  createNewVersion,
  getArticle,
  getArticles,
  getFiles,
  getDiscussions,
  getFileComments,
  editFileComment,
  postFileComment,
  isRoleInArticle,
  postDiscussion,
  changeEmail,
  changePassword,
  downloadArticleJSON,
  applyReviewer,
  acceptReviewer,
  rejectReviewer,
  getReviewers,
  addReviewers,
  rejectArticle,
  getUnauthorisedReviewers,
  voteArticle,
  search,
  getRole,
  migrateVersion,
  getEmail,
};
