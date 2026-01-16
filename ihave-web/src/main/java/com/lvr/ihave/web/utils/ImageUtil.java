package com.lvr.ihave.web.utils;

import com.lvr.ihave.ex.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 图片工具类 - 统一处理文件上传
 * @ClassName ImageUtil
 * @Author lan
 * @Date 2021/4/18 20:10
 **/
@Component
public class ImageUtil {
    
    /** 上传基础路径 */
    private static String imagesPath;
    
    /** 允许的文件类型 */
    private static String allowedTypes;
    
    /** 最大文件大小 */
    private static String allowedMaxSize;
    
    /** URL前缀 */
    private static String urlPrefix;
    
    @Value("${imagesPath}")
    public void setImagesPath(String imagesPath) {
        ImageUtil.imagesPath = imagesPath;
    }
    
    @Value("${allowedTypes:jpg,jpeg,png,gif}")
    public void setAllowedTypes(String allowedTypes) {
        ImageUtil.allowedTypes = allowedTypes;
    }
    
    @Value("${allowedMaxSize:10MB}")
    public void setAllowedMaxSize(String allowedMaxSize) {
        ImageUtil.allowedMaxSize = allowedMaxSize;
    }
    
    @Value("${url-prefix:http://localhost:3000/upload/}")
    public void setUrlPrefix(String urlPrefix) {
        ImageUtil.urlPrefix = urlPrefix;
    }

    /**
     * 上传图片 - 基础方法
     * @param image 图片资源
     * @param fileName 图片名称
     * @return 是否上传成功
     */
    public boolean imageUpload(MultipartFile image, String fileName) {
        if (image == null || image.isEmpty()) {
            return false;
        }
        
        // 定义上传文件保存路径
        String path = imagesPath;
        File filepath = new File(path, fileName);
        
        // 判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        
        try {
            // 写入文件
            image.transferTo(new File(path + File.separator + fileName));
            return true;
        } catch (IOException e) {
            throw new FileUploadException("文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 上传图片 - 完整方法（推荐）
     * @param image 图片文件
     * @param subDir 子目录（如：goods, avatar）
     * @return 文件访问URL
     */
    public String uploadImage(MultipartFile image, String subDir) {
        // 参数验证
        validateImage(image);
        
        // 生成唯一文件名
        String originalFilename = image.getOriginalFilename();
        String fileName = generateUniqueFileName(originalFilename);
        
        // 构建完整路径
        String fullPath = imagesPath + File.separator + subDir + File.separator + fileName;
        File destFile = new File(fullPath);
        
        // 确保目录存在
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        
        try {
            // 保存文件
            image.transferTo(destFile);
            
            // 返回访问URL
            return urlPrefix + subDir + "/" + fileName;
        } catch (IOException e) {
            throw new FileUploadException("文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 验证图片文件
     * @param image 图片文件
     */
    private void validateImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new FileUploadException("上传文件不能为空");
        }
        
        // 验证文件类型
        String originalFilename = image.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new FileUploadException("文件名格式不正确");
        }
        
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        List<String> allowedTypeList = Arrays.asList(allowedTypes.split(","));
        if (!allowedTypeList.contains(suffix.replace(".", ""))) {
            throw new FileUploadException("不支持的文件类型，允许的类型: " + allowedTypes);
        }
        
        // 验证文件大小
        long maxSize = parseMaxSize(allowedMaxSize);
        if (image.getSize() > maxSize) {
            throw new FileUploadException("文件大小超过限制，最大允许: " + allowedMaxSize);
        }
    }

    /**
     * 生成唯一文件名
     * @param originalFilename 原始文件名
     * @return 唯一文件名
     */
    private String generateUniqueFileName(String originalFilename) {
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        return UUID.randomUUID().toString().replace("-", "") + suffix;
    }

    /**
     * 解析最大文件大小
     * @param maxSize 字符串格式的大小（如：10MB）
     * @return 字节数
     */
    private long parseMaxSize(String maxSize) {
        if (maxSize.endsWith("MB")) {
            return Long.parseLong(maxSize.replace("MB", "")) * 1024 * 1024;
        } else if (maxSize.endsWith("KB")) {
            return Long.parseLong(maxSize.replace("KB", "")) * 1024;
        } else if (maxSize.endsWith("B")) {
            return Long.parseLong(maxSize.replace("B", ""));
        }
        return 10 * 1024 * 1024; // 默认10MB
    }

    /**
     * 删除文件
     * @param fileUrl 文件URL
     * @param subDir 子目录
     * @return 是否删除成功
     */
    public boolean deleteImage(String fileUrl, String subDir) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return false;
        }
        
        try {
            // 从URL中提取文件名
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            String fullPath = imagesPath + File.separator + subDir + File.separator + fileName;
            File file = new File(fullPath);
            
            if (file.exists() && file.isFile()) {
                return file.delete();
            }
            return false;
        } catch (Exception e) {
            throw new FileUploadException("文件删除失败: " + e.getMessage());
        }
    }

    /**
     * 获取URL前缀
     * @return URL前缀
     */
    public String getUrlPrefix() {
        return urlPrefix;
    }

    /**
     * 获取默认头像URL
     * @return 默认头像URL
     */
    public String getDefaultAvatarUrl() {
        return urlPrefix + "avatar/default-avatar.png";
    }
}