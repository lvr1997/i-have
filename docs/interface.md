# API 接口文档

## 1. 用户地址管理接口

### 1.1 添加收货地址
- **接口路径**: `POST /address/add`
- **功能描述**: 添加用户收货地址
- **认证要求**: 需要用户登录
- **请求参数**:
  - `receiverName` (String): 收货人姓名
  - `receiverState` (String): 收货人省份
  - `receiverCity` (String): 收货人城市
  - `receiverDistrict` (String): 收货人区域
  - `receiverAddress` (String): 详细地址
  - `receiverMobile` (String): 手机号码
  - `receiverPhone` (String): 固定电话
  - `receiverZip` (String): 邮政编码
  - `addressName` (String): 地址标签
- **返回结果**: JSONResult 添加成功

### 1.2 设置默认地址
- **接口路径**: `GET /address/setDefault`
- **功能描述**: 设置默认收货地址
- **认证要求**: 需要用户登录
- **请求参数**:
  - `id` (Integer): 地址ID
  - `userId` (String): 用户ID
- **返回结果**: JSONResult 设置成功

### 1.3 获取地址详情
- **接口路径**: `GET /address/getAddress`
- **功能描述**: 根据ID获取地址详情
- **认证要求**: 需要用户登录
- **请求参数**:
  - `id` (Integer): 地址ID
- **返回结果**: JSONResult 包含地址信息

### 1.4 更新地址
- **接口路径**: `POST /address/updateAddress`
- **功能描述**: 更新收货地址信息
- **认证要求**: 需要用户登录
- **请求参数**:
  - `id` (Integer): 地址ID
  - 其他地址字段同添加接口
- **返回结果**: JSONResult 更新成功

### 1.5 删除地址
- **接口路径**: `POST /address/deleteAddress`
- **功能描述**: 删除收货地址
- **认证要求**: 需要用户登录
- **请求参数**:
  - `id` (Integer): 地址ID
  - `uid` (String): 用户ID
- **返回结果**: JSONResult 删除成功

## 2. 管理员认证接口

### 2.1 管理员登录
- **接口路径**: `POST /admin/login`
- **功能描述**: 管理员登录验证
- **请求参数**:
  - `username` (String): 用户名
  - `password` (String): 密码
- **返回结果**: 重定向到主页面或返回登录页面

### 2.2 管理员退出
- **接口路径**: `GET /admin/logout`
- **功能描述**: 管理员退出登录
- **返回结果**: 重定向到登录页面

## 3. 验证码接口

### 3.1 获取验证码
- **接口路径**: `GET /captcha`
- **功能描述**: 获取图片验证码
- **返回结果**: Map 包含base64编码的图片验证码

## 4. 分类管理接口

### 4.1 分类列表
- **接口路径**: `GET /category/list`
- **功能描述**: 获取分类列表
- **认证要求**: 需要管理员登录
- **请求参数**:
  - `keyword` (String, 可选): 搜索关键词
- **返回结果**: 分类列表页面

### 4.2 新增分类
- **接口路径**: `GET /category/add` (进入页面)
- **接口路径**: `POST /category/add` (提交数据)
- **功能描述**: 新增商品分类
- **认证要求**: 需要管理员登录
- **请求参数**: Catelog对象
- **返回结果**: 重定向到列表页面

### 4.3 编辑分类
- **接口路径**: `GET /category/{id}` (进入编辑页面)
- **接口路径**: `POST /category/{id}` (提交编辑)
- **功能描述**: 编辑商品分类
- **认证要求**: 需要管理员登录
- **请求参数**: Catelog对象
- **返回结果**: 重定向到列表页面

### 4.4 删除分类
- **接口路径**: `GET /category/delete/{id}`
- **功能描述**: 删除商品分类
- **认证要求**: 需要管理员登录
- **返回结果**: 重定向到列表页面

### 4.5 更新分类状态
- **接口路径**: `GET /category/updateStatus`
- **功能描述**: 更新分类状态
- **认证要求**: 需要管理员登录
- **请求参数**:
  - `id` (Integer): 分类ID
  - `status` (Byte): 状态值
- **返回结果**: 重定向到列表页面

## 5. 前台用户接口

### 5.1 用户登录
- **接口路径**: `POST /front/user/login`
- **功能描述**: 用户手机号登录
- **请求参数**:
  - `phone` (String): 手机号
  - `password` (String): 密码
- **返回结果**: JSONResult 包含token

### 5.2 用户注册
- **接口路径**: `POST /front/user/register`
- **功能描述**: 用户注册
- **请求参数**:
  - `user` (SysUser): 用户信息
  - `checkcode` (String): 验证码
- **返回结果**: JSONResult 注册结果

### 5.3 忘记密码
- **接口路径**: `POST /front/user/forget`
- **功能描述**: 忘记密码重置
- **请求参数**:
  - `phone_number` (String): 手机号
  - `password` (String): 新密码
  - `captchaCode` (String): 验证码
- **返回结果**: JSONResult 重置结果

### 5.4 检查手机号
- **接口路径**: `POST /front/user/checkPhone`
- **功能描述**: 检查手机号是否已注册
- **请求参数**:
  - `phone` (String): 手机号
- **返回结果**: JSONResult 检查结果

### 5.5 验证验证码
- **接口路径**: `GET /front/user/checkCode`
- **功能描述**: 验证验证码
- **请求参数**:
  - `checkcode` (String): 验证码
- **返回结果**: JSONResult 验证结果

### 5.6 用户主页
- **接口路径**: `GET /front/user/index`
- **功能描述**: 获取用户主页信息
- **认证要求**: 需要用户登录
- **请求参数**:
  - `user` (String): 用户ID
- **返回结果**: JSONResult 用户信息和商品列表

### 5.7 用户退出
- **接口路径**: `POST /front/user/logout`
- **功能描述**: 用户退出登录
- **认证要求**: 需要用户登录
- **请求参数**: SysUser对象
- **返回结果**: JSONResult 退出结果

### 5.8 获取用户信息
- **接口路径**: `GET /front/user/info`
- **功能描述**: 获取当前用户信息
- **认证要求**: 需要用户登录
- **返回结果**: JSONResult 用户信息

### 5.9 更新用户信息
- **接口路径**: `POST /front/user/save_userinfo`
- **功能描述**: 保存用户信息修改
- **认证要求**: 需要用户登录
- **请求参数**: SysUser对象
- **返回结果**: JSONResult 更新结果

### 5.10 上传头像
- **接口路径**: `POST /front/user/avatar_upload`
- **功能描述**: 上传用户头像
- **认证要求**: 需要用户登录
- **请求参数**:
  - `avatar` (MultipartFile): 头像文件
- **返回结果**: JSONResult 上传结果和URL

### 5.11 我的收藏
- **接口路径**: `GET /front/user/want`
- **功能描述**: 获取用户收藏的商品
- **认证要求**: 需要用户登录
- **请求参数**:
  - `userId` (String): 用户ID
- **返回结果**: JSONResult 收藏商品列表

### 5.12 我的发布
- **接口路径**: `GET /front/user/published`
- **功能描述**: 获取用户发布的留言
- **认证要求**: 需要用户登录
- **请求参数**:
  - `userId` (String): 用户ID
- **返回结果**: JSONResult 留言列表

### 5.13 收到的留言
- **接口路径**: `GET /front/user/received`
- **功能描述**: 获取用户收到的留言
- **认证要求**: 需要用户登录
- **请求参数**:
  - `userId` (String): 用户ID
- **返回结果**: JSONResult 留言列表

### 5.14 我的收货地址
- **接口路径**: `GET /front/user/address`
- **功能描述**: 获取用户收货地址列表
- **认证要求**: 需要用户登录
- **请求参数**:
  - `userId` (String): 用户ID
- **返回结果**: JSONResult 地址列表

### 5.15 购买商品
- **接口路径**: `POST /front/user/buy-good/{id}`
- **功能描述**: 购买闲置商品
- **认证要求**: 需要用户登录
- **请求参数**:
  - `id` (Integer): 商品ID
  - `userId` (String): 用户ID
- **返回结果**: JSONResult 商品、图片和地址信息

### 5.16 我的购买订单
- **接口路径**: `POST /front/user/order_buy`
- **功能描述**: 获取用户购买订单
- **认证要求**: 需要用户登录
- **请求参数**: SysUser对象
- **返回结果**: JSONResult 订单列表

### 5.17 我的出售订单
- **接口路径**: `POST /front/user/published_order`
- **功能描述**: 获取用户出售订单
- **认证要求**: 需要用户登录
- **请求参数**: SysUser对象
- **返回结果**: JSONResult 订单列表

### 5.18 收藏/取消收藏
- **接口路径**: `GET /front/user/collect`
- **功能描述**: 收藏或取消收藏商品
- **认证要求**: 需要用户登录
- **请求参数**:
  - `userId` (String): 用户ID
  - `gid` (Integer): 商品ID
- **返回结果**: JSONResult 操作结果

### 5.19 发布留言
- **接口路径**: `POST /front/user/comment`
- **功能描述**: 对商品发布留言
- **认证要求**: 需要用户登录
- **请求参数**:
  - `userId` (String): 用户ID
  - `gid` (Integer): 商品ID
  - `content` (String): 留言内容
- **返回结果**: JSONResult 发布结果

### 5.20 搜索商品
- **接口路径**: `POST /front/user/search`
- **功能描述**: 搜索商品
- **请求参数**:
  - `keywords` (String): 搜索关键词
- **返回结果**: JSONResult 商品列表

### 5.21 用户反馈
- **接口路径**: `POST /front/user/feedback`
- **功能描述**: 提交用户反馈
- **认证要求**: 需要用户登录
- **请求参数**:
  - `userId` (String): 用户ID
  - `content` (String): 反馈内容
- **返回结果**: JSONResult 提交结果

## 6. 商品管理接口

### 6.1 商品列表（后台）
- **接口路径**: `GET /good/listForAdmin`
- **功能描述**: 获取商品列表（后台管理）
- **认证要求**: 无需认证
- **请求参数**:
  - `keyword` (String, 可选): 搜索关键词
- **返回结果**: 商品列表页面

### 6.2 商品列表（前台）
- **接口路径**: `GET /good/list`
- **功能描述**: 获取商品列表（前台）
- **认证要求**: 无需认证
- **请求参数**:
  - `keyword` (String, 可选): 搜索关键词
- **返回结果**: JSONResult 商品列表

### 6.3 商品详情
- **接口路径**: `GET /good/detail`
- **功能描述**: 获取商品详细信息
- **认证要求**: 无需认证
- **请求参数**:
  - `goodId` (Integer): 商品ID
  - `userId` (String): 用户ID（可选）
- **返回结果**: JSONResult 商品详情、卖家信息、分类信息

## 7. 首页接口

### 7.1 首页数据
- **接口路径**: `GET /goods/index`
- **功能描述**: 获取首页数据
- **认证要求**: 无需认证
- **返回结果**: JSONResult 商品列表和分类列表

### 7.2 分类商品
- **接口路径**: `GET /goods/catelog/{cid}`
- **功能描述**: 获取指定分类的商品
- **认证要求**: 无需认证
- **请求参数**:
  - `cid` (Integer): 分类ID
- **返回结果**: JSONResult 商品列表

## 8. 订单管理接口

### 8.1 订单支付
- **接口路径**: `POST /order/payment`
- **功能描述**: 订单支付准备
- **认证要求**: 需要用户登录
- **请求参数**:
  - `gid` (Integer): 商品ID
  - `aid` (Integer): 地址ID
- **返回结果**: JSONResult 支付信息

### 8.2 支付结果
- **接口路径**: `POST /order/payresult`
- **功能描述**: 处理支付结果
- **认证要求**: 需要用户登录
- **请求参数**:
  - `gid` (Integer): 商品ID
  - `aid` (Integer): 地址ID
  - `userId` (String): 用户ID
  - `payid` (String): 支付ID
- **返回结果**: JSONResult 订单信息

## 9. 商品发布接口

### 9.1 上传商品图片
- **接口路径**: `POST /publish/upload`
- **功能描述**: 上传商品图片
- **认证要求**: 需要用户登录
- **请求参数**:
  - `userId` (String): 用户ID
  - `fileName` (MultipartFile): 图片文件
- **返回结果**: JSONResult 上传结果和图片URL

### 9.2 删除图片
- **接口路径**: `POST /publish/delete_image`
- **功能描述**: 删除上传的图片
- **认证要求**: 需要用户登录
- **请求参数**:
  - `fileName` (String): 图片名称
- **返回结果**: JSONResult 删除结果

### 9.3 发布商品
- **接口路径**: `POST /publish/complete`
- **功能描述**: 完成商品发布
- **认证要求**: 需要用户登录
- **请求参数**:
  - `userId` (String): 用户ID
  - `goods` (Goods): 商品信息
  - `goodImages` (String): 商品图片URL列表
- **返回结果**: JSONResult 发布结果

### 9.4 删除商品
- **接口路径**: `POST /publish/delete_good`
- **功能描述**: 删除已发布的商品
- **认证要求**: 需要用户登录
- **请求参数**:
  - `gid` (Integer): 商品ID
- **返回结果**: JSONResult 删除结果

### 9.5 擦亮商品
- **接口路径**: `POST /publish/reflash`
- **功能描述**: 擦亮商品（刷新发布时间）
- **认证要求**: 需要用户登录
- **请求参数**:
  - `gid` (Integer): 商品ID
- **返回结果**: JSONResult 擦亮结果

### 9.6 举报商品
- **接口路径**: `POST /publish/report`
- **功能描述**: 举报违规商品
- **认证要求**: 需要用户登录
- **请求参数**:
  - `userId` (String): 用户ID
  - `gid` (Integer): 商品ID
  - `good_title` (String): 商品标题
  - `description` (String): 举报描述
- **返回结果**: JSONResult 举报结果

## 10. 角色管理接口（后台）

### 10.1 角色列表
- **接口路径**: `GET /role/list`
- **功能描述**: 获取角色列表
- **认证要求**: 需要管理员登录
- **返回结果**: 角色列表页面

### 10.2 编辑角色
- **接口路径**: `GET /role/edit` (进入编辑页面)
- **接口路径**: `POST /role/edit` (提交编辑)
- **功能描述**: 新增或编辑角色
- **认证要求**: 需要管理员登录
- **请求参数**: Roles对象
- **返回结果**: 重定向到列表页面

### 10.3 删除角色
- **接口路径**: `GET /role/delete/{id}`
- **功能描述**: 删除角色
- **认证要求**: 需要管理员登录
- **返回结果**: 重定向到列表页面

## 11. 搜索接口

### 11.1 搜索用户
- **接口路径**: `GET /search/searchuser`
- **功能描述**: 搜索用户
- **认证要求**: 无需认证
- **请求参数**:
  - `username` (String): 用户名或手机号
- **返回结果**: JSONResult 用户列表

### 11.2 搜索商品
- **接口路径**: `GET /search/searchgood`
- **功能描述**: 搜索商品
- **认证要求**: 无需认证
- **请求参数**:
  - `keyword` (String): 搜索关键词
- **返回结果**: JSONResult 商品列表

### 11.3 搜索分类
- **接口路径**: `GET /search/searchcatelog`
- **功能描述**: 搜索商品分类
- **认证要求**: 无需认证
- **请求参数**:
  - `keyword` (String): 搜索关键词
- **返回结果**: JSONResult 分类列表

### 11.4 搜索举报
- **接口路径**: `GET /search/searchreport`
- **功能描述**: 搜索举报信息
- **认证要求**: 需要用户登录
- **请求参数**:
  - `keyword` (String): 搜索关键词
- **返回结果**: JSONResult 举报列表

### 11.5 搜索评论
- **接口路径**: `GET /search/searchcomments`
- **功能描述**: 搜索评论
- **认证要求**: 需要用户登录
- **请求参数**:
  - `keyword` (String): 搜索关键词
- **返回结果**: JSONResult 评论列表

### 11.6 搜索订单
- **接口路径**: `GET /search/searchorder`
- **功能描述**: 搜索订单
- **认证要求**: 需要用户登录
- **请求参数**:
  - `keyword` (String): 搜索关键词
- **返回结果**: JSONResult 订单列表

## 12. 用户管理接口（后台）

### 12.1 用户列表
- **接口路径**: `GET /user/list`
- **功能描述**: 获取用户列表
- **认证要求**: 需要管理员登录
- **返回结果**: 用户列表页面

### 12.2 编辑用户
- **接口路径**: `GET /user/edit` (进入编辑页面)
- **接口路径**: `POST /user/update` (提交编辑)
- **功能描述**: 新增或编辑用户
- **认证要求**: 需要管理员登录
- **请求参数**: SysUser对象
- **返回结果**: 重定向到列表页面

### 12.3 删除用户
- **接口路径**: `GET /user/delete/{userId}`
- **功能描述**: 删除用户
- **认证要求**: 需要管理员登录
- **返回结果**: 重定向到列表页面

### 12.4 更新用户状态
- **接口路径**: `GET /user/updateStatus`
- **功能描述**: 更新用户状态
- **认证要求**: 需要管理员登录
- **请求参数**:
  - `id` (String): 用户ID
  - `status` (Integer): 状态值
- **返回结果**: 重定向到列表页面

## 认证说明

### 认证注解说明
- `@PassToken`: 无需认证，可直接访问
- `@UserLoginToken`: 需要用户登录认证
- `@AdminToken`: 需要管理员登录认证

### Token使用
用户登录成功后会返回JWT token，后续请求需要在请求头中携带token进行认证。