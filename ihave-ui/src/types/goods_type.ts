// 定义商品接口
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
    image: string
    status: 'selling' | 'sold'
    publishTime: string
    seller: ProductSeller
}

export {
    Product,
    ProductSeller
}