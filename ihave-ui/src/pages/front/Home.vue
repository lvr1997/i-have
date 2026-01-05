<template>
  <section>
    <!-- 轮播图 -->
    <div class="flex text-center"> 
      <!-- <div class="w-50% bg-white dark:bg-gray-900">
        <h1 class="mt-20">你想要的，这儿都有~</h1>
        <p class="text-gray-500">让闲置不再闲置...</p>
        <el-button type="primary" @click="toPublish">立即发布</el-button>
      </div> -->
      <el-carousel class="w-full" motion-blur>
        <el-carousel-item class="bg-blue-2" v-for="item in 4" :key="item">
          <h3 class="small justify-center leading-9" text="2xl">{{ item }}</h3>
        </el-carousel-item>
      </el-carousel>
    </div>

    <!-- 首页商品展示 -->
    <div class="block text-center my-6"> 
      <h2>最新发布</h2>
      <div class="flex justify-around ">
        <el-radio-group v-model="categoryId" size="large">
          <el-radio-button label="电子数码" :value="2" />
          <el-radio-button label="棋牌休闲" :value="3" />
          <el-radio-button label="书籍刊物" :value="4" />
          <el-radio-button label="服装衣物" :value="5" />
        </el-radio-group>
      </div>

      <div class="flex flex-wrap justify-center">
        <el-card class="m-4" style="max-width: 480px" v-for="(item, index) in 8" :key="index">
          <template #header>Yummy hamburger</template>
          <img
            src="https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
            style="width: 100%"
          />
        </el-card>
      </div>

      <el-button type="primary" @click="router.push({path:'/goods', query:{categoryId: categoryId}})">更多...</el-button>
    </div>
  </section>
</template>

<script setup lang="ts">
import { useUserStore } from "~/store/user";
import { useRouter } from "vue-router";
import { ref } from "vue";
import { ElMessage } from "element-plus";

const router = useRouter();
const userStore = useUserStore();

const categoryId = ref('New York')

</script>

<style scoped>
.demonstration {
  color: var(--el-text-color-secondary);
}

.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>
