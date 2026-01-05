<script lang="ts" setup>
import { repository } from '~/../package.json'
import { useRouter } from "vue-router";
import { toggleDark } from "~/composables";
import { useUserStore } from "~/store/user";
import { errorMsg, successMsg } from "~/utils/message";
import { ref } from 'vue';
import { ElMessage } from 'element-plus';

const router = useRouter();
const userStore = useUserStore();

// 跳转发布闲置
const toPublish = () => {
  // 跳转发布闲置
  router.push("/publish");
};

// 退出登录
const logout = () => {
  userStore.LogoutAction().then(res => {
    if (res.code === 200) {
      successMsg(res.msg)
      // 路由跳转
      router.push({ path: "/login" });
    } else {
      errorMsg(res.msg)
    }
  })
}
</script>

<template>
  <!-- 学生端前台导航栏 -->
  <header class="bg-white dark:bg-gray-900">
    <nav class="bg-white dark:bg-gray-900">
      <!-- 导航栏 -->
      <el-menu class="el-menu-demo w-full" mode="horizontal" router :ellipsis="false" default-active="/home">
        <el-menu-item index="/">
          <img class="w-auto h-6 sm:h-7" src="../../assets/images/logo.png" alt="IHave logo" />
        </el-menu-item>
        <el-menu-item index="/home">首页</el-menu-item>
        <el-menu-item index="/notice">公告</el-menu-item>
        <el-menu-item index="/wanted">求购</el-menu-item>
        <el-sub-menu index="user" v-if="userStore.username">
          <template #title>{{ userStore.username }}</template>
          <el-menu-item index="/goods">
            <i class="i-ep:upload"></i> 发布闲置
          </el-menu-item>
          <el-menu-item index="/userInfo">
            <i class="i-ep:user"></i> 个人中心
          </el-menu-item>
          <el-menu-item @click="logout">
            <i class="i-ep:switch-button"></i> 退出登录
          </el-menu-item>
        </el-sub-menu>
        <el-menu-item v-else index="/login">登录/注册</el-menu-item>
        <el-menu-item h="full" @click="toggleDark()">
          <button class="w-full cursor-pointer border-none bg-transparent" style="height: var(--ep-menu-item-height)">
            <i inline-flex i="dark:ep-moon ep-sunny" />
          </button>
        </el-menu-item>

        <el-menu-item h="full">
          <a class="size-full flex items-center justify-center" :href="repository.url" target="_blank">
            <div i-ri-github-fill />
          </a>
        </el-menu-item>
      </el-menu>
    </nav>
  </header>
</template>
<style lang="scss" scoped>
.el-menu-demo {
  &.ep-menu--horizontal > .ep-menu-item:nth-child(1) {
    margin-right: auto;
  }
}
</style>
