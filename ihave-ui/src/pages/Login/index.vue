<template>
    <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
        <div class="sm:mx-auto sm:w-full sm:max-w-sm text-center">
            <img class="mx-auto h-10 w-auto" src="../../assets/images/logo.png" alt="logo_ihave">
            <h2 class="mt-8 text-center text-2xl font-bold leading-9 tracking-tight">{{ data.type === "login" ? "登 录" : "注 册" }}</h2>
        </div>
        <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <el-form class="space-y-6 mt-5" ref="account_form" :model="data.form" :rules="data.form_rules" label-position="top" size="large">
                <el-form-item prop="phone">
                    <el-input v-model="data.form.phone" placeholder="用户名/手机号"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" v-model="data.form.password" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item prop="passwords" v-if="data.type === 'register'">
                    <el-input type="password" v-model="data.form.passwords" placeholder="确认密码"></el-input>
                </el-form-item>
                <el-form-item prop="code">
                    <el-row :gutter="5">
                        <el-col :span="12">
                            <el-input v-model="data.form.code" placeholder="验证码"></el-input>
                        </el-col>
                        <el-col :span="12">
                            <el-button class="w-full" type="success" :loading="data.code_button_loading" :disabled="data.code_button_disabled" @click="handlerGetCode">
                                {{ data.code_button_text }}
                            </el-button>
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item label-position="left">
                    <template #label>
                        <a class="text-xs font-display font-semibold text-gray-500 hover:text-gray-600 cursor-pointer" href="#">
                            忘记密码？
                        </a>
                    </template>
                </el-form-item>
                <el-form-item>
                    <el-button class="w-full" type="primary" @click="submitForm" :disabled="data.submit_button_disabled" :loading="data.loading">
                        {{ data.type === "login" ? "登录" : "注册" }}
                    </el-button>
                </el-form-item>
                <el-form-item>
                    <template #label>
                        <div class="text-center">
                            <el-button v-show="data.type === 'login'" link class="text-xs text-gray-500 uppercase dark:text-gray-400 hover:underline"  
                                            @click="data.type = 'register'">
                                还没有账号? 去注册
                            </el-button>
                            <el-button link v-show="data.type === 'register'" class="text-xs text-gray-500 uppercase dark:text-gray-400 hover:underline"  
                                            @click="data.type = 'login'">
                                已有账号? 去登录
                            </el-button>
                        </div>
                    </template>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup lang="ts">
import { onBeforeUnmount, reactive, ref } from "vue";
import { useUserStore } from "~/store/user";
import { validate_password, validate_phone } from "~/utils/validate";
// import sha1 from "js-sha1";
// API
import { Register } from "~/api/user";
import { errorMsg, successMsg, warnMsg } from "~/utils/message";

const userStore = useUserStore();
const emits = defineEmits(["closeDialog"]);

const data = reactive({
    form: {
        phone: "", // 手机号
        password: "", // 密码
        passwords: "", // 确认密码
        code: "", // 验证码
    },
    form_rules: {
        phone: [{ required: true, pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur" }],
        password: [{ required: true, message: "用户密码不能为空", trigger: "blur" }, { min: 5, max: 20, message: "用户密码长度必须介于 5 和 20 之间", trigger: "blur" }, { pattern: /^[^<>"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\\ |", trigger: "blur" }],
        passwords: [{ required: true, message: "用户密码不能为空", trigger: "blur" }, { min: 5, max: 20, message: "用户密码长度必须介于 5 和 20 之间", trigger: "blur" }, { pattern: /^[^<>"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\\ |", trigger: "blur" }],
        code: [{ required: true, message: "验证码不能为空", trigger: "blur" }],
    },
    tab_menu: [
        { type: "login", label: "登录" },
        { type: "register", label: "注册" },
    ],
    type: 'login',
    show_text: "Sign in to your account",
    /**
     * 获取验证码按钮交互
     */
    code_button_disabled: false,
    code_button_loading: false,
    code_button_text: "获取验证码",
    code_button_timer: null,
    // 提交按钮
    submit_button_disabled: true,
    loading: false,
});

// 获取验证码
const handlerGetCode = () => {
    const username = data.form.phone;
    const password = data.form.password;
    const passwords = data.form.passwords;
    // 校验用户名
    if (!validate_phone(username)) {
        warnMsg("手机号格式不正确");
        return false;
    }
    // 校验密码
    if (!validate_password(password)) {
        warnMsg("密码格式不正确")
        return false;
    }
    // 判断非 登录 时，校验两次密码
    if (data.type === "register" && password !== passwords) {
        warnMsg("两次密码不一致")
        return false;
    }

    // 获取验证码接口
    const requestData = {
        username: data.form.phone,
    };
    data.code_button_loading = true;
    data.code_button_text = "发送中";

    setTimeout(() => {
        const resData = 1234;
        // 激活提交按钮
        data.submit_button_disabled = false;
        // 成功 Elementui 提示
        successMsg(resData)
        // 执行倒计时
        countdown();
    }, 2000);   
    // GetCode(requestData).then((response) => {
    //     const resData = response;
    //     // 激活提交按钮
    //     data.submit_button_disabled = false;
    //     // 用户名存在
    //     if (resData.resCode === 1024) {
    //         warnMsg(resData.message);
    //         return false;
    //     }
    //     // 成功 Elementui 提示
    //     successMsg(resData.message)
    //     // 执行倒计时
    //     countdown();
    // }).catch(() => {
    //     data.code_button_loading = false;
    //     data.code_button_text = "获取验证码";
    // });
};

/** 倒计时 */
const countdown = (time?: number) => {
    if (time && typeof time !== "number") {
        return false;
    }
    let second = time || 60; // 默认时间
    data.code_button_loading = false; // 取消加载
    data.code_button_disabled = true; // 禁用按钮
    data.code_button_text = `倒计时${second}秒`; // 按钮文本
    // 判断是否存在定时器，存在则先清除
    if (data.code_button_timer) {
        clearInterval(data.code_button_timer);
    }
    // 开启定时器
    data.code_button_timer = setInterval(() => {
        second--;
        data.code_button_text = `倒计进${second}秒`; // 按钮文本
        if (second <= 0) {
            data.code_button_text = `重新获取`; // 按钮文本
            data.code_button_disabled = false; // 启用按钮
            clearInterval(data.code_button_timer); // 清除倒计时
        }
    }, 1000);
};
/** 表单提交 */
const account_form = ref();
// formName
const submitForm = () => {
    account_form.value.validate((valid) => {
        if (valid) {
            data.type === "login" ? login() : register();
        } else {
            warnMsg("表单验证不通过");
            return false;
        }
    });
};
/** 注册 */
const register = () => {
    const requestData = {
        username: data.form.phone,
        password: data.form.password,
        code: data.form.code,
        create: 1,
    };
    data.loading = true;
    Register(requestData)
        .then((response) => {
            successMsg(response.message)
            reset();
        })
        .catch(() => {
            data.loading = false;
        });
};
/** 登录 */
const login = () => {
    const requestData = {
        username: data.form.phone,
        password: data.form.password,
        code: data.form.code,
    };
    data.loading = true;
    userStore.testlogin(requestData).then(res => {
        if (res.code === 200) {
            data.loading = false;
            emits("closeDialog", false);
            reset();
        } else {
            errorMsg(res.msg)
        }
    })
    // userStore.LoginAction(requestData).then((response) => {
    //     data.submit_button_loading = false;
    //     successMsg(response.message)
    //     //路由跳转
    //     router.push({ path: "/" });
    //     reset();
    // }).catch(() => {
    //     data.submit_button_loading = false;
    //     console.log("失败");
    // });
};

/** 重置 */
const reset = () => {
    // 重置表单
    // proxy.$refs.form.resetFields();
    // 切回登录模式
    data.type = "login";
    // 清除定时器
    data.code_button_timer && clearInterval(data.code_button_timer);
    // 获取验证码重置文本
    data.code_button_text = "获取验证码";
    // 获取验证码激活
    data.code_button_disabled = false;
    // 禁用提交按钮
    data.submit_button_disabled = true;
    // 取消提交按钮加载
    data.loading = false;
};
// 组件销毁之前 - 生命周期
onBeforeUnmount(() => {
    clearInterval(data.code_button_timer); // 清除倒计时
});

</script>

<style scoped lang="scss"></style>
