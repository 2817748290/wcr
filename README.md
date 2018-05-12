# 智慧教室说明文档
> 创建时间：2017年11月4日13:50:56 
>
> 最后更新： 2017年11月4日14:39:49

## 项目说明

***

#### 一、项目构架

1.  服务端：spring boot + mybatis + mysql + logback

    > logback（日志框架） 不提倡使用System.out打印
    
        private Logger logger =  LoggerFactory.getLogger(this.getClass());
        logger.info("占位 {} 自动填充 {} 可多个", a , b);
    
2.  前端：vue.js + bootstrap + axios

    > vue.js（目录 /wcr-vue 详情查看 /wcr-vue/README.md）
    
    > axios（vue.js官方推荐ajax框架）
    
3.  移动端：React Native

    > 仓库地址：[http://10.10.3.113:3000/chopin/PanHub.git]
 
4.  权限控制：spring security + jwt token
    
    > token值：登录成功后在返回，位于header.refresh
    
    > 访问需要权限的url，需在请求的header里带上 token:token值
    
    > token快过期的时候，会在返回头内header.refresh的值提供新的token值

5.  api文档：swagger
    
    > 访问地址：http://localhost/doc

#### 二、返回示例
    接口返回数据 全部使用Message类封装
    
>   {"status":1（状态码）,"message":"返回成功","result":数据}
    
#### 三、工具类包

>* CommonUtil.java
>
>>      getProjectFilePath() : 获取项目资源文件储存路径
>>
>>      getIpAddr() ：获取访问者IP地址
>
>* UploadUtil.java
>
>>      singleFileUpload() : 上传文件
>
>* DownloadUtil.java
>
>>      downUploadFile() : 下载UploadUtil的上传文件
>>
>>      downFile() : 通用下载类
