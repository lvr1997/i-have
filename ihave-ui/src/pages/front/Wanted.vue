<template>
  <section class="Wanted my-6">
    <div class="flex flex-col items-center gap-5 w-full">
      <el-card
        class="bg-white dark:bg-gray-900 rounded-lg shadow-md w-1/2 text-left"
        v-for="(item, index) in wantedList"
        :key="index"
      >
        <h3 class="text-xl font-bold my-2">{{ item.title }}</h3>
        <!-- 修改：发布人和发布时间在同一行显示 -->
        <div class="flex items-center text-sm text-gray-600 gap-2 mb-2">
          <i class="i-ep-user"></i>
          <span>{{ item.name }}</span>
          <i class="i-ep-clock"></i>
          <span>{{ item.time }}</span>
        </div>
        <p class="text-base">{{ item.description }}</p>
        <!-- 新增：查看详情按钮 -->
        <el-button type="text" @click="viewDetails(item)">查看详情</el-button>
        <!-- 新增：联系TA按钮 -->
        <el-button type="primary" size="small" @click="contactUser(item)">联系TA</el-button>
      </el-card>
    </div>

    <!-- 新增：详情弹窗 -->
    <el-dialog v-model="dialogVisible" title="求购详情" width="50%">
      <p>{{ currentWanted?.description }}</p>
    </el-dialog>
  </section>
</template>

<script setup lang="ts">
import { ref } from 'vue';

// 模拟的求购信息数据
const wantedList = [
  { title: '求购笔记本电脑', time: '2023-10-01 10:00', description: '求购二手笔记本电脑，预算不超过3000元。', name: '张三' },
  { title: '求购二手手机', time: '2023-09-25 15:30', description: '求购苹果iPhone 12，成色九成新。', name: '李四' },
  { title: '求购自行车', time: '2023-10-15 14:00', description: '求购山地自行车，适合骑行运动。', name: '王五' }
];

// 控制弹窗显示状态
const dialogVisible = ref(false);
// 存储当前选中的求购信息
const currentWanted = ref();

// 查看详情方法
const viewDetails = (item: any) => {
  currentWanted.value = item;
  dialogVisible.value = true;
};

// 新增：联系TA方法
const contactUser = (item: any) => {
  alert(`正在联系 ${item.name}...`);
};
</script>

<style scoped>
/* 基础样式 */
.Wanted {
  padding: 2rem;
}
</style>