import Layout from '~/components/layouts/index.vue';

const routes = [
    { 
      path: '/', 
      name: 'Layout',
      redirect: '/home',
      component: Layout,
      children: [
        {
          path: '/home',
          name: 'FrontHome',
          component: () => import(`~/pages/front/Home.vue`),
          meta: {
            title: '首页'
          }
        },
        {
          path: '/notice',
          name: 'FrontNotice',
          component: () => import(`~/pages/front/Notice.vue`),
          meta: {
            title: '公告'
          }
        },
        {
          path: '/wanted',
          name: 'FrontWanted',
          component: () => import(`~/pages/front/Wanted.vue`),
          meta: {
            title: '公告'
          }
        },
        {
          path: '/publish',
          name: 'FrontPublish',
          component: () => import(`~/pages/front/Publish.vue`),
          meta: {
            title: '发布闲置'
          }
        },
        {
          path: '/userInfo',
          name: 'FrontUserInfo',
          component: () => import(`~/pages/front/UserInfo.vue`),
          meta: {
            title: '用户信息'
          }
        },
        {
          path: '/goods',
          name: 'FrontGoods',
          component: () => import(`~/pages/front/Goods.vue`),
          meta: {
            title: '全部物品'
          }
        },
        {
          path: '/detail/:id',
          name: 'FrontDetail',
          component: () => import(`~/pages/front/Detail.vue`),
          meta: {
            title: '商品详情'
          }
        },
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import(`~/pages/Login/index.vue`),
      meta: {
        title: '登录'
      }
    },
    { path: '/:pathMatch(.*)*', name: '404', component: () => import(`~/pages/error/404.vue`) }

]

export default routes