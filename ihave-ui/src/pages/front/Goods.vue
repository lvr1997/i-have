<template>
    <div class="min-h-screen bg-gray-50">
        <!-- 页面标题 -->
        <div class="bg-white shadow-sm">
            <div class="container mx-auto px-4 py-6">
                <h1 class="text-2xl font-bold text-gray-900">闲置物品列表</h1>
                <p class="text-gray-600 mt-1">发现校园里的优质二手物品</p>
            </div>
        </div>

        <!-- 主要内容区域 -->
        <div class="container mx-auto px-4 py-6">
            <div class="flex gap-6">
                <!-- 左侧分类列表 -->
                <div class="w-64 flex-shrink-0">
                    <div class="bg-white rounded-lg shadow-sm p-4 sticky top-6">
                        <h3 class="text-sm font-semibold text-gray-900 mb-4">商品分类</h3>
                        <el-scrollbar height="400px">
                            <ul class="space-y-2 list-none pl-0">
                                <li v-for="category in categories" :key="category.id" 
                                    class="category-item"
                                    :class="{ 'active': activeCategory === category.id }"
                                    @click="handleCategoryClick(category.id)">
                                    <div class="flex items-center gap-3 p-2 rounded-lg cursor-pointer transition-colors hover:bg-blue-50">
                                        <i :class="category.icon" class="text-gray-500 text-lg"></i>
                                        <span class="text-gray-700">{{ category.name }}</span>
                                        <span class="ml-auto text-sm text-gray-400">{{ category.count }}</span>
                                    </div>
                                </li>
                            </ul>
                        </el-scrollbar>
                        
                        <!-- 价格筛选 -->
                        <div class="mt-6 pt-4 border-t border-gray-200">
                            <h4 class="text-sm font-semibold text-gray-900 mb-3">价格区间</h4>
                            <div class="space-y-2">
                                <el-checkbox-group v-model="priceRanges">
                                    <el-checkbox label="0-50">0-50元</el-checkbox>
                                    <el-checkbox label="50-100">50-100元</el-checkbox>
                                    <el-checkbox label="100-200">100-200元</el-checkbox>
                                    <el-checkbox label="200+">200元以上</el-checkbox>
                                </el-checkbox-group>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 右侧商品卡片区域 -->
                <div class="flex-1">
                    <!-- 搜索和排序 -->
                    <div class="bg-white rounded-lg shadow-sm p-4 mb-6">
                        <div class="flex flex-col sm:flex-row gap-4">
                            <el-input
                                v-model="searchKeyword"
                                placeholder="搜索商品名称或描述"
                                class="flex-1"
                                clearable>
                                <template #prefix>
                                    <i class="i-ep:search text-gray-400"></i>
                                </template>
                            </el-input>
                            
                            <el-select v-model="sortBy" placeholder="排序方式" class="w-40">
                                <el-option label="最新发布" value="newest" />
                                <el-option label="价格从低到高" value="price_asc" />
                                <el-option label="价格从高到低" value="price_desc" />
                                <el-option label="人气最高" value="popular" />
                            </el-select>
                        </div>
                    </div>

                    <!-- 商品网格 -->
                    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
                        <GoodCard
                            v-for="product in products"
                            :key="product.id"
                            :product="product"
                            @click="handleProductClick" />
                    </div>

                    <!-- 分页 -->
                    <div class="flex justify-center mt-8">
                        <el-pagination
                            v-model:current-page="currentPage"
                            v-model:page-size="pageSize"
                            :page-sizes="[12, 24, 36, 48]"
                            :total="totalProducts"
                            layout="total, sizes, prev, pager, next, jumper"
                            background />
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import GoodCard from '~/components/GoodCard.vue'
import { Product } from '~/types/goods_type'

const route = useRoute()

// 分类数据
const categories = ref([
    { id: 1, name: '全部商品', icon: 'i-ep-goods', count: 128 },
    { id: 2, name: '数码电子', icon: 'i-ep-monitor', count: 45 },
    { id: 3, name: '图书教材', icon: 'i-ep-reading', count: 32 },
    { id: 4, name: '服饰鞋包', icon: 'i-ep-lollipop', count: 28 },
    { id: 5, name: '运动户外', icon: 'i-ep-basketball', count: 19 },
    { id: 6, name: '美妆个护', icon: 'i-ep-headset', count: 15 },
    { id: 7, name: '游戏娱乐', icon: 'i-ep-game', count: 12 },
    { id: 8, name: '其他分类', icon: 'i-ep-more', count: 8 }
])

// 商品数据
const products = ref<Product[]>([
    {
        id: 1,
        name: 'iPhone 13 Pro 256GB',
        description: '99新，仅使用三个月，无任何划痕，全套包装齐全',
        price: 4999,
        originalPrice: 7999,
        image: 'https://via.placeholder.com/300x200/3B82F6/FFFFFF?text=iPhone+13',
        status: 'selling',
        publishTime: '2小时前',
        seller: { name: '张三', avatar: 'https://via.placeholder.com/40x40/10B981/FFFFFF?text=ZS', rating: 4.8 }
    },
    {
        id: 2,
        name: 'Java编程思想第五版',
        description: '全新未拆封，正版教材，适合计算机专业学生',
        price: 45,
        originalPrice: 89,
        image: 'https://via.placeholder.com/300x200/10B981/FFFFFF?text=Java+Book',
        status: 'selling',
        publishTime: '1天前',
        seller: { name: '李四', avatar: 'https://via.placeholder.com/40x40/8B5CF6/FFFFFF?text=LS', rating: 4.9 }
    },
    {
        id: 3,
        name: 'Nike Air Force 1',
        description: '白色经典款，42码，仅试穿一次，几乎全新',
        price: 399,
        originalPrice: 799,
        image: 'https://via.placeholder.com/300x200/EF4444/FFFFFF?text=Nike+Shoes',
        status: 'sold',
        publishTime: '3天前',
        seller: { name: '王五', avatar: 'https://via.placeholder.com/40x40/F59E0B/FFFFFF?text=WW', rating: 4.7 }
    },
    {
        id: 4,
        name: 'MacBook Pro 2021',
        description: 'M1芯片，16GB内存，512GB硬盘，性能强劲',
        price: 8999,
        originalPrice: 12999,
        image: 'https://via.placeholder.com/300x200/6366F1/FFFFFF?text=MacBook',
        status: 'selling',
        publishTime: '5小时前',
        seller: { name: '赵六', avatar: 'https://via.placeholder.com/40x40/EC4899/FFFFFF?text=ZL', rating: 4.6 }
    },
    {
        id: 5,
        name: '索尼WH-1000XM4耳机',
        description: '降噪效果极佳，音质优秀，配件齐全',
        price: 1299,
        originalPrice: 2299,
        image: 'https://via.placeholder.com/300x200/8B5CF6/FFFFFF?text=Sony+Headphone',
        status: 'selling',
        publishTime: '1天前',
        seller: { name: '钱七', avatar: 'https://via.placeholder.com/40x40/14B8A6/FFFFFF?text=QQ', rating: 4.8 }
    },
    {
        id: 6,
        name: 'Python数据分析实战',
        description: '数据分析入门教材，附带实战案例代码',
        price: 35,
        originalPrice: 68,
        image: 'https://via.placeholder.com/300x200/F59E0B/FFFFFF?text=Python+Book',
        status: 'selling',
        publishTime: '2天前',
        seller: { name: '孙八', avatar: 'https://via.placeholder.com/40x40/84CC16/FFFFFF?text=SB', rating: 4.5 }
    }
])

// 响应式数据
const activeCategory = ref(1)
const searchKeyword = ref('')
const sortBy = ref('newest')
const priceRanges = ref([])
const currentPage = ref(1)
const pageSize = ref(12)
const totalProducts = ref(128)

// 方法
const handleCategoryClick = (categoryId: number) => {
    activeCategory.value = categoryId
    // 这里可以添加分类切换的逻辑
}

// 商品点击事件
const handleProductClick = (product: any) => {
    console.log('点击商品:', product)
    // 这里可以添加跳转到商品详情页的逻辑
}

onMounted(() => {
    console.log('当前分类ID:', route.query.categoryId)
    if (route.query.categoryId) {
        activeCategory.value = Number(route.query.categoryId)
    }
})
</script>

<style scoped>
.category-item.active i {
    color: #3B82F6;
}

.category-item.active span {
    color: #3B82F6;
    font-weight: 600;
}

.line-clamp-2 {
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
}

:deep(.el-scrollbar__bar) {
    opacity: 0.6;
}
</style>