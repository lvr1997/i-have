import axios from '~/api/index';
import { LoginUserRequest } from './model';

// 定义接口
enum Api {
    USER_LOGIN = '/front/user/login',
    USER_REGISTER = '/front/user/register',
    USER_LOGOUT = '/front/user/logout',
    GET_USER_INFO = '/front/user/info',
    UPDATE_USER_INFO = '/front/user/updateInfo',
}

/** 登录 */
export function Login(params: LoginUserRequest) {
  return axios.post<LoginUserRequest>(Api.USER_LOGIN, params);
}

/** 注册 */
export const Register = (params: LoginUserRequest) =>  {
  return axios.post<LoginUserRequest>(Api.USER_REGISTER, { params });
}

/** 登出 */
export function Logout(params: string) {
  return axios.get(Api.USER_LOGOUT, { params });
}

/** 获取用户信息 */
export function GetUserInfo() {
  return axios.get(Api.GET_USER_INFO);
}

/** 更新用户信息 */
export function UpdateUserInfo(data = {}) {
  return axios.post(Api.UPDATE_USER_INFO, data);
}
