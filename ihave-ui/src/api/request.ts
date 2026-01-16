import axios from "axios";
import { ElMessage } from "element-plus";
 
console.log(`====================当前环境：${import.meta.env.VITE_APP_BASE_URL}=======================`);


//创建实例
const service = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_URL,
  timeout: 5000, //超时
});
 
//添加请求拦截器
service.interceptors.request.use(
  function (config) {
    //在发送请求之前做些什么
    return config;
  },
  function (error) {
    console.log(error.request);
    const errorData = JSON.parse(error.request.response);
    if (errorData.message) {
      //判断是否具有message属性
      ElMessage({
        message: errorData.message,
        type: "error",
      });
    }
    //对请求错误做些什么
    return Promise.reject(errorData);
  }
);
 
//添加响  应拦截器
service.interceptors.response.use(
  function (response) {
    //对响应数据做些什么
    console.log("响应数据", response);
    const data = response.data;
    if (data.resCode === 0) {
      return Promise.resolve(data);
    } else {
      ElMessage({
        message: data.message,
        type: "error",
      });
      return Promise.reject(data);
    }
  },
  function (error) {
    //对响应错误做些什么
    const errorData = JSON.parse(error.request.response);
    if (errorData.message) {
      //判断是否具有message属性
      ElMessage({
        message: errorData.message,
        type: "error",
      });
    }
 
    return Promise.reject(errorData);
  }
);
 
//暴露service
export default service;