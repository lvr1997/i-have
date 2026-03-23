<template>
    <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
        <div class="sm:mx-auto sm:w-full sm:max-w-sm text-center">
            <img class="mx-auto h-10 w-auto" src="../../assets/images/logo.png" alt="logo_ihave">
            <h2 class="mt-8 text-center text-2xl font-bold leading-9 tracking-tight">{{ data.type === "login" ? "登 录" :
                "注 册" }}</h2>
        </div>
        <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <el-form class="space-y-6 mt-5" ref="account_form" :model="data.form" :rules="data.form_rules"
                label-position="top" size="large">
                <el-form-item prop="phone">
                    <el-input v-model="data.form.phone" placeholder="请输入手机号"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" v-model="data.form.password" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item prop="passwords" v-if="data.type === 'register'">
                    <el-input type="password" v-model="data.form.passwords" placeholder="请输入确认密码"></el-input>
                </el-form-item>
                <el-form-item prop="code">
                    <el-col :span="12">
                        <el-input v-model="data.form.code" placeholder="请输入验证码"></el-input>
                    </el-col>
                    <el-col :span="12">
                        <img class="cursor-pointer" :src="data.captchaUrl" alt="验证码" @click="refreshCaptcha" title="点击可刷新验证码" />
                    </el-col>
                </el-form-item>
                <el-form-item>
                    <template #label>
                        <div class="text-center">
                            <el-button class="text-xs text-gray-500/50" link>
                                忘记密码？
                            </el-button>
                            <el-button v-show="data.type === 'login'" link
                                class="text-xs text-gray-500/50"
                                @click="data.type = 'register'">
                                还没有账号? 去注册
                            </el-button>
                            <el-button link v-show="data.type === 'register'"
                                class="text-xs text-gray-500/50"
                                @click="data.type = 'login'">
                                已有账号? 去登录
                            </el-button>
                        </div>
                    </template>
                    <el-button class="w-full" type="primary" @click="submitForm" :disabled="data.submit_button_disabled"
                        :loading="data.loading">
                        {{ data.type === "login" ? "登录" : "注册" }}
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup lang="ts">
import { reactive, ref } from "vue";
import { useUserStore } from "~/store/user";
import type { FormInstance, FormRules } from 'element-plus'
import { errorMsg, successMsg, warnMsg } from "~/utils/message";
import { useRouter } from "vue-router";
import { useTimestamp } from "@vueuse/core";
// API
import { getCaptcha } from "~/api/common";
import { Register } from "~/api/user";

// https://zhuanlan.zhihu.com/p/691704266 ts封装axios
// https://element-plus.org/zh-CN/component/form element-plus form表单验证规则


const userStore = useUserStore();
const router = useRouter();
const ruleFormRef = ref<FormInstance>()


const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input the password'))
  } else {
    if (data.form.password !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('passwords')
    }
    callback()
  }
}
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请再次输入密码！'))
  } else if (value !== data.form.password) {
    callback(new Error("两次输入的密码不一致！"))
  } else {
    callback()
  }
}

const data = reactive({
    form: {
        phone: "", // 手机号
        password: "", // 密码
        passwords: "", // 确认密码
        code: "", // 验证码
    },
    form_rules: {
        phone: [{ required: true, pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur" }],
        password: [{ validator: validatePass, trigger: "blur" }, { min: 5, max: 20, message: "用户密码长度必须介于 5 和 20 之间", trigger: "blur" }, { pattern: /^[^<>"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\\ |", trigger: "blur" }],
        passwords: [{ validator: validatePass2, trigger: "blur" }, { min: 5, max: 20, message: "用户密码长度必须介于 5 和 20 之间", trigger: "blur" }, { pattern: /^[^<>"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\\ |", trigger: "blur" }],
        code: [{ required: true, message: "验证码不能为空", trigger: "blur" }],
    },
    tab_menu: [
        { type: "login", label: "登录" },
        { type: "register", label: "注册" },
    ],
    type: 'login',
    show_text: "Sign in to your account",
    // 提交按钮
    submit_button_disabled: true,
    loading: false,
    captchaUrl: "",
});


const timestamp = useTimestamp({ offset: 0 })
const captcha = {
    width: 160,
    height: 38,
    timestamp: timestamp.value,
}

const refreshCaptcha = async () => {
    try {
        const { data: res } = await getCaptcha(captcha)

        data.captchaUrl = res.imgsrc;
    } catch (error) {
        errorMsg("获取验证码失败");
    }
}

// Initialize captcha on component mount
refreshCaptcha();


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
const register = async () => {
    const requestData = {
        phone: data.form.phone,
        password: data.form.password,
        code: data.form.code,
    };
    data.loading = true;
    try {
        const { msg } = await Register(requestData);
        successMsg(msg);
        reset();
    } catch (error) {
        data.loading = false;
        return;
    }
};
/** 登录 */
const login = () => {
    const requestData = {
        phone: data.form.phone,
        password: data.form.password,
        code: data.form.code,
    };
    data.loading = true;
    userStore.LoginAction(requestData).then((response) => {
        data.loading = false;
        successMsg(response.msg)
        //路由跳转
        router.push({ path: "/" });
        reset();
    }).catch(() => {
        data.submit_button_loading = false;
        console.log("失败");
    });
};

/** 重置 */
const reset = () => {
    // 重置表单
    // proxy.$refs.form.resetFields();
    // 切回登录模式
    data.type = "login";
    // 禁用提交按钮
    data.submit_button_disabled = true;
    // 取消提交按钮加载
    data.loading = false;
};
</script>

<style scoped lang="scss"></style>
