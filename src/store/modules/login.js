const USER_LOGIN = "login/USER_LOGIN";
const USER_LOGOUT = "login/USER_LOGOUT";
const USER_ROLE = "login/USER_ROLE";
const USER_REDIRECT = "login/USER_REDIRECT";

export const userLogin = (userInfo) => ({ type: USER_LOGIN, userInfo });
export const userLogout = () => ({ type: USER_LOGOUT });
export const setUserRole = (userInfo) => ({ type: USER_ROLE, userInfo });
export const userNavigate = (userInfo) => ({ type: USER_REDIRECT, userInfo });

const initialState = {
  username: "",
  loggedIn: false,
  userEmail: "",
  userid: -1,
  userRole: 4,
  userGroup: -1,
  redirect: 0,
};

export default function login(state = initialState, action) {
  switch (action.type) {
    case USER_LOGIN:
      return {
        ...state,
        username: action.userInfo.username,
        loggedIn: true,
        userEmail: action.userInfo.userEmail,
        userid: action.userInfo.userid,
        userRole: action.userInfo.userRole,
        userGroup: action.userInfo.userGroup,
      };
    case USER_LOGOUT:
      return {
        ...state,
        username: "",
        loggedIn: false,
        userEmail: "",
        userid: -1,
        userRole: 4,
        userGroup: -1,
        redirect: 0,
      };
    case USER_ROLE:
      return {
        ...state,
        userRole: action.userInfo.userRole,
      };
    case USER_REDIRECT:
      return {
        ...state,
        redirect: action.userInfo.redirect,
      };
    default:
      return state;
  }
}
