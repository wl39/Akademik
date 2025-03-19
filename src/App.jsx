import { Component } from "react";
import { Route, Routes, Navigate, BrowserRouter } from "react-router-dom";

import ArticlesPage from "./assets/views/ArticlePage/ArticlesPage.jsx";
import ArticlePage from "./assets/views/ArticlePage/ArticlePage.jsx";
import LoginPage from "./assets/views/LoginPage/LoginPage.jsx";
import RegisterPage from "./assets/views/RegisterPage/RegisterPage.jsx";
import MainPage from "./assets/views/MainPage/Mainpage.jsx";
import ProfilePage from "./assets/views/ProfilePage/ProfilePage.jsx";
import ApplyReviewerPage from "./assets/views/ApplyReviewerPage/ApplyReviewerPage.jsx";
import ModeratePage from "./assets/views/ModeratePage/ModeratePage.jsx";
import ModerateUserPage from "./assets/views/ModeratePage/ModerateUserPage.jsx";
import ModerateArticlesPage from "./assets/views/ModeratePage/ModerateArticlesPage.jsx";
import ModerateArticlePage from "./assets/views/ModeratePage/ModerateArticlePage.jsx";
import ReviewPage from "./assets/views/ReviewPage/ReviewPage.jsx";

import Header from "./components/Header/Header.jsx";

import "./App.css";
import ArticleUploadPage from "./assets/views/ArticlePage/ArticleUploadPage.jsx";

class App extends Component {
  render() {
    let routes = (
      // For using Router
      <Routes>
        <Route path="/moderate/reviewers" element={<ModerateUserPage />} />
        <Route
          path="/moderate/articles/:title"
          element={<ModerateArticlePage />}
        />
        <Route path="/moderate/articles/" element={<ModerateArticlesPage />} />
        <Route path="/moderate" element={<ModeratePage />} />
        <Route path="/review/articles/:title" element={<ReviewPage />} />
        <Route path="/articles/upload" element={<ArticleUploadPage />} />
        <Route path="/articles/:title" element={<ArticlePage />} />
        <Route path="/articles" element={<ArticlesPage />} />
        <Route
          path="/users/profile/apply-reviewer"
          element={<ApplyReviewerPage />}
        />
        <Route path="/users/profile" element={<ProfilePage />} />
        <Route path="/login" element={<LoginPage />} />
        {/* if the path starts with '/login' the web shows 'LoginPage' */}
        <Route path="/register" element={<RegisterPage />} />
        {/* if the path starts with '/register' the web shows 'RegisterPage' */}
        <Route path="/" exact element={<MainPage />} />
        {/* if the path starts with '/' the web shows 'MainPage' */}
        <Route path="*" element={<Navigate to="/" />} />
        {/* Any invalid path will redirect the web to '/' path */}
      </Routes>
    );

    return (
      <div className="Akademik">
        <Header location={this.props.location} />
        {routes}
        {/* 'routes' is component for the page*/}
      </div> // accept/reject reviewers & articles to assign reviewers
    );
  }
}

export default App;
