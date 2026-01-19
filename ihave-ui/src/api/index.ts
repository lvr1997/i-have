import axios, { AxiosError, AxiosInstance, AxiosRequestConfig, AxiosResponse } from "axios";
import { errorMsg } from "~/utils/message";
import { ResultData } from "./index.data";

const baseURL: string = import.meta.env.VITE_APP_BASE_URL
console.log(`====================当前环境：${import.meta.env.VITE_APP_BASE_URL}=======================`);

enum RequestEnums {
  TIMEOUT = 20000,
  OVERDUE = 401, // 登录失效
  FAIL = 400, // 请求失败
  SUCCESS = 200 // 请求成功
}
const config = {
  // 默认地址
  baseURL,
  // 设置超时时间
  timeout: RequestEnums.TIMEOUT as number,
  // 跨域时候允许携带凭证
  withCredentials: true
}

class HttpRequest {
  service: AxiosInstance;
  public constructor(config: AxiosRequestConfig) {
    this.service = axios.create(config);
    // 请求拦截器
    this.service.interceptors.request.use((config => {
      return config;
    }), (error: AxiosError) => {
      Promise.reject(error);
    });
    // 响应拦截器
    this.service.interceptors.response.use((response: AxiosResponse) => {
      const { data } = response;

      // 登录失效处理
      if (data.code === RequestEnums.OVERDUE) {
        return Promise.reject(data)
      }
      // 全局错误信息拦截（防止下载文件得时候返回数据流，没有code，直接报错）
      if (data.code && data.code !== RequestEnums.SUCCESS) {
        errorMsg(data.msg)
        return Promise.reject(data)
      }

      return data
    }, (error: AxiosError) => {
      const { response } = error
      if (response) {
        this.handleCode(response.status)
      }
      if (!window.navigator.onLine) {
        errorMsg('网络连接失败')
      }
    })
  }

  handleCode(code: number): void {
    switch (code) {
      case 401:
        errorMsg('登录失效，请重新登录')
        break
      default:
        errorMsg('请求失败')
        break
    }
  }

  // 常用方法封装
  get<T>(url: string, params?: object): Promise<ResultData<T>> {
    return this.service.get(url, { params })
  }
  post<T>(url: string, params?: object): Promise<ResultData<T>> {
    return this.service.post(url, params)
  }
  put<T>(url: string, params?: object): Promise<ResultData<T>> {
    return this.service.put(url, params)
  }
  delete<T>(url: string, params?: object): Promise<ResultData<T>> {
    return this.service.delete(url, { params })
  }
}

// 导出一个实例对象
export default new HttpRequest(config)