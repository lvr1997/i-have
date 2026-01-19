import axios from "~/api/index";
import { CaptchaRequest, CaptchaResult } from "./model";

enum CommonApi { 
    GET_CAPTCHA = '/captcha'
 }

/** 获取验证码 */
export function getCaptcha(params: CaptchaRequest) {
  return axios.get<CaptchaResult>(CommonApi.GET_CAPTCHA, params);
}