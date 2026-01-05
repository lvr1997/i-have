<template>
  <section class="my-6">
    <div class="flex flex-col items-center justify-center gap-5 w-full">
      <el-card
        class="bg-white dark:bg-gray-900 rounded-lg shadow-md w-1/2 text-left"
        v-for="(notice, index) in notices"
        :key="index"
      >
        <h3 class="text-xl font-bold my-2">{{ notice.title }}</h3>
        <div class="flex items-center text-sm text-gray-600 gap-2 mb-2">
          <div class="i-ep-user"></div>
          <span>{{ notice.name }}</span>
          <div class="i-ep-clock"></div>
          <span>{{ notice.time }}</span>
        </div>
        <p class="text-base">{{ notice.content }}</p>
        <!-- 新增：阅读全文按钮 -->
        <el-button type="text" @click="dialogVisible = true; currentNotice = notice">阅读全文</el-button>
      </el-card>
    </div>

    <!-- 新增：公告详情弹窗 -->
    <el-dialog v-model="dialogVisible" title="公告详情" width="50%">
      <p>{{ currentNotice?.content }}</p>
    </el-dialog>
  </section>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const notices = [
  { title: '系统维护通知', time: '2023-10-01 10:00', content: '系统将于本周六进行维护，请提前做好准备。',name:'系统管理员' },
  { title: '新功能上线', time: '2023-09-25 15:30', content: '新增商品推荐功能，提升购物体验！',name:'系统管理员' },
  { title: '活动预告', time: '2023-10-15 14:00', content: '双十一大促即将开启，敬请期待！',name:'系统管理员' },
  { title: '支付方式更新', time: '2023-10-05 09:00', content: '新增微信支付和支付宝支付选项。',name:'系统管理员' },
  { title: '用户反馈奖励', time: '2023-09-30 18:00', content: '提交有效反馈即可获得积分奖励！',name:'系统管理员' },
  { title: '会员日福利', time: '2023-10-10 12:00', content: '会员日专属折扣，限时优惠不容错过！',name:'系统管理员' },
  { title: '物流升级', time: '2023-10-08 16:00', content: '新增多家快递合作，配送速度更快！',name:'系统管理员' }
];

// 新增：控制弹窗显示状态
const dialogVisible = ref(false);
// 新增：存储当前选中的公告
const currentNotice = ref();
</script>

<style scoped>
</style>