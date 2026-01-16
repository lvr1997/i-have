<template>
    <div class="my-6 mx-10">
        <el-form :model="form" label-width="auto">
            <el-form-item label="上传图片">
                <el-upload v-model:file-list="fileList" action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15" list-type="picture-card"
                           :on-preview="handlePictureCardPreview" :on-remove="handleRemove">
                    <el-icon>
                        <i class="i-ep-plus"></i>
                    </el-icon>
                </el-upload>

                <el-dialog v-model="dialogVisible">
                    <img w-full :src="dialogImageUrl" alt="Preview Image" />
                </el-dialog>
            </el-form-item>
            <el-form-item label="闲置名称">
                <el-input v-model="form.name" />
            </el-form-item>
            <el-form-item label="闲置详情">
                <el-input v-model="form.desc" type="textarea" />
            </el-form-item>
            <el-row :gutter="10">
                <el-col :span="8">
                    <el-form-item label="价格">
                        <el-input v-model="form.price" />
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="原价">
                        <el-input v-model="form.realPrice" />
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="插入原购买链接">
                        <el-switch v-model="form.delivery" />
                    </el-form-item>
                </el-col>
            </el-row>

            <el-form-item v-show="form.delivery" label="原购买链接">
                <el-input v-model="form.buyUrl" />
            </el-form-item>

            <el-form-item label="闲置分类">
                <el-select v-model="form.category" placeholder="请选择分类">
                    <el-option label="电子数码" value="1"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="交易方式">
                <el-radio-group v-model="form.buyMethod">
                    <el-radio value="1">在线交易</el-radio>
                    <el-radio value="2">线下交易</el-radio>
                    <el-radio value="3">在线交易/线下交易</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="交易地址">
                <el-input v-model="form.address" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">确认发布</el-button>
                <el-button>取消</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script lang="ts" setup>
import type { UploadProps, UploadUserFile } from 'element-plus'
import { ref, reactive } from 'vue'

const fileList = ref<UploadUserFile[]>([
    {
        name: 'food.jpeg',
        url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
    }
])

const dialogImageUrl = ref('')
const dialogVisible = ref(false)

const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
    console.log(uploadFile, uploadFiles)
}

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
    dialogImageUrl.value = uploadFile.url!
    dialogVisible.value = true
}

// do not use same name with ref
const form = reactive({
    name: '',
    price: 0,
    realPrice:0,
    buyUrl: '',
    buyMethod: '',
    address: '',
    delivery: false,
    category: '',
    desc: '',
})

const onSubmit = () => {
    console.log('submit!')
}
</script>