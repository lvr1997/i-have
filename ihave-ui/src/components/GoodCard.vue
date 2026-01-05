<template>
    <div class="good-card bg-white rounded-lg shadow-sm hover:shadow-md transition-shadow duration-300 cursor-pointer"
         @click="handleClick">
        <!-- 商品图片 -->
        <div class="relative">
            <el-image 
                :src="product.image" 
                :alt="product.name"
                class="w-full h-48 object-cover rounded-t-lg"
                fit="cover"
                :preview-src-list="[product.image]"
                :z-index="1000">
                <template #error>
                    <div class="w-full h-48 bg-gray-100 rounded-t-lg flex items-center justify-center">
                        <i class="i-ep-picture text-4xl text-gray-400"></i>
                    </div>
                </template>
                <template #placeholder>
                    <div class="w-full h-48 bg-gray-100 rounded-t-lg flex items-center justify-center">
                        <i class="i-ep-loading text-2xl text-gray-400 animate-spin"></i>
                    </div>
                </template>
            </el-image>
            
            <!-- 商品状态遮罩 -->
            <div v-if="product.status === 'sold'" class="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center rounded-t-lg">
                <span class="text-white font-semibold text-lg">已售出</span>
            </div>
            
            <!-- 商品状态标签 -->
            <div class="absolute top-2 right-2">
                <el-tag :type="product.status === 'sold' ? 'info' : 'success'" size="small">
                    {{ product.status === 'sold' ? '已售' : '在售' }}
                </el-tag>
            </div>
        </div>
        
        <!-- 商品信息 -->
        <div class="p-4">
            <!-- 商品名称和描述 -->
            <h3 class="text-lg font-semibold text-gray-900 mb-1 line-clamp-1">{{ product.name }}</h3>
            <p class="text-gray-600 text-sm mb-3 line-clamp-2">{{ product.description }}</p>
            
            <!-- 价格信息 -->
            <div class="flex items-baseline justify-between mb-3">
                <div class="flex items-baseline gap-1">
                    <span class="text-xl font-bold text-red-500">¥{{ product.price }}</span>
                    <span v-if="product.originalPrice" class="text-sm text-gray-400 line-through">
                        ¥{{ product.originalPrice }}
                    </span>
                </div>
                <span v-if="product.discount" class="bg-red-100 text-red-600 px-2 py-1 rounded text-xs">
                    {{ product.discount }}折
                </span>
            </div>
            
            <!-- 发布时间和卖家信息 -->
            <div class="flex items-center justify-between text-gray-500 text-xs">
                <span>{{ product.publishTime }}</span>
                <div class="flex items-center gap-1">
                    <el-avatar :size="20" :src="product.seller.avatar">
                        <i class="i-ep-user text-gray-400"></i>
                    </el-avatar>
                    <span>{{ product.seller.name }}</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router'

// 定义接口
interface ProductSeller {
    name: string
    avatar: string
    rating: number
}

interface Product {
    id: number
    name: string
    description: string
    price: number
    originalPrice?: number
    discount?: number
    image: string
    status: 'selling' | 'sold'
    publishTime: string
    seller: ProductSeller
}

// 定义Props
const props = defineProps<{
    product: Product
}>()

// 定义事件
const emit = defineEmits<{
    click: [product: Product]
}>()

const router = useRouter()

// 处理点击事件
const handleClick = () => {
    // 触发自定义事件
    emit('click', props.product)
    
    // 跳转到商品详情页
    router.push(`/detail/${props.product.id}`)
}
</script>

<style scoped>
.line-clamp-1 {
    overflow: hidden;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 1;
}

.line-clamp-2 {
    overflow: hidden;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
}

.good-card:hover {
    transform: translateY(-2px);
}
</style>