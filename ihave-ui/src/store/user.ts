import { defineStore } from "pinia";
import { Login } from "~/api/user";
import { RequestData } from "~/types/user_type";

export const useUserStore = defineStore("user", {
    state: () => ({
        username: "",
        role: 'STUDENT',
    }),
    getters: {
       
    },
    actions: {
        //执行登录请求
        LoginAction(requestData: RequestData) {
            return new Promise((resolve) => {
                Login(requestData).then((res) => {
                    resolve(res)
                }).catch(err => {
                    resolve(err)
                })
            });
        },
        testlogin(requestData: RequestData) {
            let res: any;
            if(requestData.username === '13212341234' && requestData.password === '123456aa') {
                this.username = requestData.username
                this.role = 'STUDENT'
                res =  { code: 200, msg: '登录成功', role: 'STUDENT' }
            } else {
                res = { code: 400, msg: '账号或密码错误' }
            }
            return new Promise((resolve) => {
                resolve(res)
            })
           
        },
        // 退出登录
        LogoutAction() {
            // 清空用户名和角色
            this.username = ''
            this.role = 'STUDENT'
            return new Promise((resolve) => {
                resolve({ code: 200, msg: '退出登录成功' })
            })
        },
    },
});