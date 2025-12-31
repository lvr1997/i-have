<script lang="ts" setup>
import { useRouter } from "vue-router";
import { toggleDark } from "~/composables";
import { useUserStore } from "~/store/user";

const router = useRouter();

const userStore = useUserStore();

// 跳转登录
const toLogin = () => {
  router.push("/login");
};
</script>

<template>
  <!-- 学生端前台导航栏 -->
  <header class="bg-white dark:bg-gray-900">
    <nav class="bg-white dark:bg-gray-900">
      <div class="container flex flex-col items-center lg:px-6 mx-auto">
        <el-menu class="w-full justify-center" mode="horizontal" router default-active="/home">
          <el-menu-item index="0">
            <img
              class="w-auto h-6 sm:h-7"
              src="../../assets/images/logo.png"
              alt="IHave logo"
            />
          </el-menu-item>
          <el-menu-item index="/home">首页</el-menu-item>
          <el-menu-item index="/notice">公告</el-menu-item>
          <el-menu-item index="/wanted">求购</el-menu-item>
          <el-menu-item h="full" @click="toggleDark()">
            <button class="border-none w-full bg-transparent cursor-pointer" style="height: var(--ep-menu-item-height)">
              <i inline-flex i="dark:ep-moon ep-sunny" />
            </button>
          </el-menu-item>
          <el-sub-menu index="4" v-if="userStore.username">
            <template #title>{{ userStore.username }}</template>
            <el-menu-item index="/userInfo">个人中心</el-menu-item>
            <el-menu-item>退出登录</el-menu-item>
          </el-sub-menu>
          <div v-else class="ep-menu-item" @click="toLogin">
            <span link>登录</span>
          </div>
        </el-menu>
      </div>
    </nav>
  </header>
</template>
<style scoped>
.ep-menu--horizontal > .ep-menu-item:nth-child(1) {
  margin-right: auto;
}
</style>
