<script setup lang="ts">
import { ref } from 'vue';

const user = {
  avatar: 'https://placehold.co/150x150',
  name: 'jackzj',
  level: 'Lv5',
  id: '291423473',
  followers: 0,
  following: 10,
  likes: 8
};

const items = [
  { icon: 'i-ep-document', text: '我发布的', link: '/userInfo/published' },
  { icon: 'i-ep-chat-dot-round', text: '我买到的', link: '/userInfo/comments' },
  { icon: 'i-ep-folder-opened', text: '我的评论', link: '/userInfo/collections' },
  { icon: 'i-ep-star-off', text: '我收藏的', link: '/userInfo/favorites' },
  { icon: 'i-ep-user', text: '我举报的', link: '/userInfo/followers' }
];

const activeTab = ref('/userInfo/published');

const posts = [
  {
    id: 1,
    publishTime: '2023-01-01',
    title: '我发布的文章1',
    content: '这是我发布的第一篇文章',
    time: '2023-01-01',
    likes: 10,
    comments: 5,
    views: 100,
    image: 'https://placehold.co/300x200'
  },
  {
    id: 2,
    title: '我发布的文章2',
    content: '这是我发布的第二篇文章',
    time: '2023-01-02',
    likes: 15,
    comments: 8,
    collections: 4,
    image: 'https://placehold.co/300x200'
  }
]
</script>

<template>
  <section class="UserInfo flex flex-col">
    <!-- 用户基本信息 -->
    <div class="PersonTop">
      <div class="PersonTop_img">
        <img src="../../assets/vue.svg" />
      </div>
      <div class="PersonTop_text">
        <div class="user_text">
          <div class="user_name">
            <span> {{ 'jack' }} </span>
          </div>
          <div class="user-v">
            <span class="user-v-font">优质媒体作者</span>
          </div>
          <div class="user_qianming">
            <span> {{ '个性签名' }}</span>
          </div>
          <div class="user_anniu">
            <el-button
              class="el-icon-edit"
              type="primary"
              size="small"
              plain
              >编辑</el-button
            >
            <!-- <el-button
              v-else
              @click="follow"
              type="primary"
              size="medium"
              icon="el-icon-check"
              v-text="
                isfollowid.indexOf(this.$route.params.id) > -1
                  ? '已关注'
                  : '关注'
              "
            ></el-button> -->
          </div>
        </div>
        <div class="user_num">
          <div style="cursor: pointer">
            <div class="num_number">{{ 100 }}</div>
            <span class="num_text">累计收入</span>
          </div>
          <div style="cursor: pointer">
            <div class="num_number">{{ 100 }}</div>
            <span class="num_text">累计支出</span>
          </div>
          <div>
            <div class="num_number">{{ 100 }}</div>
            <span class="num_text">获赞</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 导航栏 -->
    <el-tabs v-model="activeTab" type="border-card" tab-position="left" class="w-full mt-4">
      <el-tab-pane v-for="(item, index) in items" :key="index" :name="item.link">
        <template #label>
          <div class="flex items-center py-2 cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-800 rounded">
            <i :class="item.icon" class="mr-2"></i>
            <span>{{ item.text }}</span>
          </div>
        </template>
        <!-- Tab 内容可以在此处添加 -->
        <div v-if="activeTab === '/userInfo/published'" class="mb-4">
          <h3>我的发帖</h3>
          <div v-for="(post, index) in posts" :key="index" class="mb-4">
            <p><i class="el-icon-time mr-2"></i>{{ post.publishTime }}</p>
            <h4>{{ post.title }}</h4>
            <p>{{ post.content }}</p>
            <img :src="post.image" alt="Post Image" class="w-16 h-16 mr-4">
            <div class="flex justify-between mt-2">
              <div>
                <i class="el-icon-view mr-2"></i>{{ post.views }}
                <i class="el-icon-chat-dot-round mr-2 ml-4"></i>{{ post.comments }}
                <i class="el-icon-thumb-up mr-2 ml-4"></i>{{ post.likes }}
              </div>
              <el-button size="small">祈愿分享</el-button>
            </div>
          </div>
          <p class="text-center mt-4">没有更多数据了</p>
        </div>
      </el-tab-pane>
    </el-tabs>
  </section>
</template>

<style scoped>
.PersonTop {
  width: 1000px;
  height: 140px;
  padding-top: 20px;
  background-color: white;
  margin-top: 30px;
  transform: translateX(-50%);
  display: flex;
  border-radius: 5px;
}

.PersonTop_img {
  width: 150px;
  height: 120px;
  background-color: #8c939d;
  margin-right: 24px;
  margin-left: 20px;
  overflow: hidden;
  border-radius: 20px;
}

.PersonTop_img img {
  width: 100%;
  height: 100%;
  border-radius: 20px;
}

.PersonTop_text {
  height: 120px;
  width: 880px;
  display: flex;
}

.user_text {
  width: 60%;
  height: 100%;
  line-height: 30px;
}

.user_name {
  font-weight: bold;
}
.user-v {
  margin-bottom: -5px;
}
.user-v-img {
  width: 15px;
  height: 15px;
}
.user-v-font {
  font-size: 15px;
  color: #00c3ff;
}
.user_qianming {
  font-size: 14px;
  color: #999;
}

.user_num {
  width: 40%;
  height: 100%;
  display: flex;
  align-items: center;
}

.user_num > div {
  text-align: center;
  border-right: 1px dotted #999;
  box-sizing: border-box;
  width: 80px;
  height: 40px;
  line-height: 20px;
}

.num_text {
  color: #999;
}

.num_number {
  font-size: 20px;
  color: #333;
}
.el-menu-item>span {
  font-size: 16px;
  color: #999;
}
</style>