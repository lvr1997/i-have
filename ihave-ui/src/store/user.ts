import { defineStore } from "pinia";
import { Login, Logout } from "~/api/user";
import { LoginUserRequest } from "~/api/user/model";

export const useUserStore = defineStore("user", {
    state: () => ({
        userId: '',
        phone: '',
        username: "",
        role: 'STUDENT',
    }),
    getters: {
       
    },
    actions: {
        //执行登录请求
        LoginAction(requestData: LoginUserRequest) {
            return new Promise((resolve) => {
                Login(requestData).then((res) => {
                    resolve(res)
                }).catch(err => {
                    resolve(err)
                })
            });
        },
        // 退出登录
        LogoutAction(requestData: string) {
            // 清空用户名和角色
            this.username = ''
            this.role = 'STUDENT'
            return new Promise((resolve) => {
                Logout(requestData).then((res) => {
                    resolve(res)
                }).catch(err => {
                    resolve(err)
                })
            })
        },
    },
});