<template>
    <div class="min-h-screen bg-gray-50">
        <!-- 面包屑导航 -->
        <div class="bg-white shadow-sm">
            <div class="container mx-auto px-4 py-4">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                    <el-breadcrumb-item :to="{ path: '/goods', query: { categoryId: product.categoryId } }">
                        {{ product.categoryName }}
                    </el-breadcrumb-item>
                    <el-breadcrumb-item>商品详情</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
        </div>

        <!-- 商品详情内容 -->
        <div class="container mx-auto px-4 py-6">
            <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
                <!-- 左侧商品图片 -->
                <div class="bg-white rounded-lg shadow-sm p-6">
                    <div class="relative">
                        <el-image 
                            :src="product.mainImage" 
                            :alt="product.name"
                            class="w-full h-96 object-cover rounded-lg"
                            fit="cover"
                            :preview-src-list="product.images"
                            :z-index="2000">
                            <template #error>
                                <div class="w-full h-96 bg-gray-100 rounded-lg flex items-center justify-center">
                                    <i class="i-ep-picture text-6xl text-gray-400"></i>
                                </div>
                            </template>
                            <template #placeholder>
                                <div class="w-full h-96 bg-gray-100 rounded-lg flex items-center justify-center">
                                    <i class="i-ep-loading text-4xl text-gray-400 animate-spin"></i>
                                </div>
                            </template>
                        </el-image>
                        
                        <!-- 商品状态标签 -->
                        <div class="absolute top-4 right-4">
                            <el-tag :type="product.status === 'sold' ? 'info' : 'success'" size="large">
                                {{ product.status === 'sold' ? '已售出' : '在售中' }}
                            </el-tag>
                        </div>
                    </div>
                    
                    <!-- 商品图片缩略图 -->
                    <div class="mt-4 flex gap-2 overflow-x-auto">
                        <div v-for="(image, index) in product.images" :key="index"
                             class="flex-shrink-0 w-20 h-20 cursor-pointer border-2 rounded"
                             :class="{ 'border-blue-500': currentImageIndex === index }"
                             @click="currentImageIndex = index">
                            <img :src="image" :alt="`商品图片${index + 1}`" 
                                 class="w-full h-full object-cover rounded" />
                        </div>
                    </div>
                </div>

                <!-- 右侧商品信息 -->
                <div class="bg-white rounded-lg shadow-sm p-6">
                    <!-- 商品基本信息 -->
                    <div class="mb-6">
                        <h1 class="text-2xl font-bold text-gray-900 mb-2">{{ product.name }}</h1>
                        <div class="flex items-center gap-4 text-gray-600 mb-4">
                            <span class="text-sm">发布时间: {{ product.publishTime }}</span>
                            <span class="text-sm">浏览: {{ product.viewCount }}次</span>
                            <span class="text-sm">收藏: {{ product.favoriteCount }}次</span>
                        </div>
                        
                        <!-- 价格信息 -->
                        <div class="mb-4">
                            <div class="flex items-baseline gap-2">
                                <span class="text-3xl font-bold text-red-500">¥{{ product.price }}</span>
                                <span v-if="product.originalPrice" class="text-lg text-gray-400 line-through">
                                    ¥{{ product.originalPrice }}
                                </span>
                                <span v-if="product.discount" class="bg-red-100 text-red-600 px-2 py-1 rounded text-sm">
                                    {{ product.discount }}折
                                </span>
                            </div>
                            <p class="text-gray-500 text-sm mt-1">
                                节省: ¥{{ product.originalPrice ? product.originalPrice - product.price : 0 }}
                            </p>
                        </div>
                        
                        <!-- 商品标签 -->
                        <div class="flex flex-wrap gap-2 mb-4">
                            <el-tag v-for="tag in product.tags" :key="tag" type="info" size="small">
                                {{ tag }}
                            </el-tag>
                        </div>
                    </div>

                    <!-- 卖家信息 -->
                    <div class="border-t border-gray-200 pt-6 mb-6">
                        <h3 class="text-lg font-semibold text-gray-900 mb-4">卖家信息</h3>
                        <div class="flex items-center gap-3">
                            <el-avatar :size="48" :src="product.seller.avatar">
                                <i class="i-ep-user text-gray-400"></i>
                            </el-avatar>
                            <div class="flex-1">
                                <div class="flex items-center gap-2">
                                    <span class="font-medium text-gray-900">{{ product.seller.name }}</span>
                                    <el-rate
                                        v-model="product.seller.rating"
                                        disabled
                                        show-score
                                        text-color="#ff9900"
                                        score-template="{value}"
                                        size="small" />
                                </div>
                                <p class="text-gray-600 text-sm">{{ product.seller.description }}</p>
                            </div>
                            <el-button type="primary" size="small" @click="contactSeller">
                                <i class="i-ep-chat-dot-round mr-1"></i>联系卖家
                            </el-button>
                        </div>
                    </div>

                    <!-- 操作按钮 -->
                    <div class="flex gap-3">
                        <el-button type="primary" size="large" class="flex-1" @click="handleBuy" :disabled="product.status === 'sold'">
                            <i class="i-ep-shopping-cart mr-2"></i>
                            {{ product.status === 'sold' ? '已售出' : '立即购买' }}
                        </el-button>
                        <el-button size="large" @click="toggleFavorite">
                            <i :class="isFavorite ? 'i-ep-star-filled text-yellow-400' : 'i-ep-star'" class="mr-1"></i>
                            {{ isFavorite ? '已收藏' : '收藏' }}
                        </el-button>
                        <el-button size="large" @click="handleShare">
                            <i class="i-ep-share mr-1"></i>分享
                        </el-button>
                    </div>
                </div>
            </div>

            <!-- 商品详情描述 -->
            <div class="mt-8 bg-white rounded-lg shadow-sm p-6">
                <h3 class="text-xl font-semibold text-gray-900 mb-4">商品描述</h3>
                <div class="prose max-w-none">
                    <p class="text-gray-700 leading-relaxed whitespace-pre-line">{{ product.description }}</p>
                </div>
                
                <!-- 商品规格 -->
                <div class="mt-6">
                    <h4 class="text-lg font-medium text-gray-900 mb-3">商品规格</h4>
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <div v-for="spec in product.specifications" :key="spec.name" class="flex">
                            <span class="text-gray-600 w-24 flex-shrink-0">{{ spec.name }}:</span>
                            <span class="text-gray-900">{{ spec.value }}</span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 相关商品推荐 -->
            <div class="mt-8">
                <h3 class="text-xl font-semibold text-gray-900 mb-4">相关推荐</h3>
                <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
                    <GoodCard
                        v-for="relatedProduct in relatedProducts"
                        :key="relatedProduct.id"
                        :product="relatedProduct"
                        @click="handleRelatedProductClick" />
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import GoodCard from '~/components/GoodCard.vue'
import { Product } from '~/types/goods_type'

const route = useRoute()
const router = useRouter()

// 商品数据
const product = ref({
    id: 1,
    name: 'iPhone 13 Pro 256GB',
    description: '99新，仅使用三个月，无任何划痕，全套包装齐全。\n\n商品特点：\n• 原装正品，无拆无修\n• 电池健康度98%\n• 支持面容ID解锁\n• 运行流畅，性能强劲\n• 适合学生党使用',
    price: 4999,
    originalPrice: 7999,
    discount: 6.2,
    mainImage: 'https://via.placeholder.com/600x400/3B82F6/FFFFFF?text=iPhone+13+Pro',
    images: [
        'https://via.placeholder.com/600x400/3B82F6/FFFFFF?text=正面',
        'https://via.placeholder.com/600x400/10B981/FFFFFF?text=背面',
        'https://via.placeholder.com/600x400/8B5CF6/FFFFFF?text=侧面',
        'https://via.placeholder.com/600x400/F59E0B/FFFFFF?text=配件'
    ],
    status: 'selling',
    publishTime: '2024-01-15 14:30',
    viewCount: 128,
    favoriteCount: 24,
    categoryId: 2,
    categoryName: '数码电子',
    tags: ['99新', '正品', '全套包装', '学生优惠'],
    specifications: [
        { name: '品牌', value: 'Apple' },
        { name: '型号', value: 'iPhone 13 Pro' },
        { name: '内存', value: '256GB' },
        { name: '颜色', value: '远峰蓝' },
        { name: '网络', value: '5G全网通' },
        { name: '购买时间', value: '2023年10月' },
        { name: '使用时长', value: '3个月' },
        { name: '保修情况', value: '官方保修至2024年10月' }
    ],
    seller: {
        name: '张三',
        avatar: 'https://via.placeholder.com/48x48/10B981/FFFFFF?text=ZS',
        rating: 4.8,
        description: '诚信卖家，支持当面交易'
    }
})

// 相关商品数据
const relatedProducts = ref<Product[]>([
    {
        id: 2,
        name: 'iPhone 12 128GB',
        description: '95新，使用一年，功能完好',
        price: 2999,
        originalPrice: 5999,
        image: 'https://via.placeholder.com/300x200/10B981/FFFFFF?text=iPhone+12',
        status: 'selling',
        publishTime: '1天前',
        seller: { name: '李四', avatar: 'https://via.placeholder.com/40x40/8B5CF6/FFFFFF?text=LS', rating: 4.9 }
    },
    {
        id: 3,
        name: 'AirPods Pro 2代',
        description: '全新未拆封，正品保障',
        price: 1299,
        originalPrice: 1899,
        image: 'https://via.placeholder.com/300x200/8B5CF6/FFFFFF?text=AirPods',
        status: 'selling',
        publishTime: '2天前',
        seller: { name: '王五', avatar: 'https://via.placeholder.com/40x40/F59E0B/FFFFFF?text=WW', rating: 4.7 }
    },
    {
        id: 4,
        name: 'iPad Air 5代',
        description: 'M1芯片，64GB，学习办公神器',
        price: 3499,
        originalPrice: 4399,
        image: 'https://via.placeholder.com/300x200/F59E0B/FFFFFF?text=iPad+Air',
        status: 'selling',
        publishTime: '3天前',
        seller: { name: '赵六', avatar: 'https://via.placeholder.com/40x40/EC4899/FFFFFF?text=ZL', rating: 4.6 }
    },
    {
        id: 5,
        name: 'MacBook Air M2',
        description: '8核CPU，8核GPU，轻薄便携',
        price: 7999,
        originalPrice: 9499,
        image: 'https://via.placeholder.com/300x200/EC4899/FFFFFF?text=MacBook',
        status: 'selling',
        publishTime: '5小时前',
        seller: { name: '钱七', avatar: 'https://via.placeholder.com/40x40/14B8A6/FFFFFF?text=QQ', rating: 4.8 }
    }
])

// 响应式数据
const currentImageIndex = ref(0)
const isFavorite = ref(false)

// 计算属性
const currentImage = computed(() => product.value.images[currentImageIndex.value])

// 方法
const handleBuy = () => {
    if (product.value.status === 'sold') {
        ElMessage.warning('该商品已售出')
        return
    }
    ElMessage.success('跳转到购买页面')
    // 这里可以添加购买逻辑
}

const toggleFavorite = () => {
    isFavorite.value = !isFavorite.value
    if (isFavorite.value) {
        product.value.favoriteCount++
        ElMessage.success('收藏成功')
    } else {
        product.value.favoriteCount--
        ElMessage.info('取消收藏')
    }
}

const handleShare = () => {
    ElMessage.info('分享功能开发中')
    // 这里可以添加分享逻辑
}

const contactSeller = () => {
    ElMessage.info('联系卖家功能开发中')
    // 这里可以添加联系卖家逻辑
}

const handleRelatedProductClick = (relatedProduct: any) => {
    // 跳转到相关商品的详情页
    router.push(`/detail/${relatedProduct.id}`)
}

onMounted(() => {
    console.log('商品ID:', route.params.id)
    // 这里可以根据路由参数加载商品数据
})
</script>

<style scoped>
.prose {
    max-width: none;
}
</style>