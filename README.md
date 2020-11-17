## 雨巷教育

## 版本

![版本](./image/001.png)

* Spring Boot          ==> 2.2.11.RELEASE
* Spring Cloud         ==> Hoxton.SR5
* Spring Cloud Alibaba ==> 2.2.0.RELEASE
* aliyun-spring-boot-dependencies ==> 1.0.0

## 模块
* **common 通用模块**
    * common-util `通用包`
    * service-base `服务基础模块，所有服务都需引入`
* **infrastructure 基础设施**
    * api-gateway `网关服务`
* **service 服务模块**
    * service-edu           `资源信息服务`
        * 功能：课程操作，讲师操作
    * service-ucenter       `用户中心服务` port=9600
        * 功能：用户注册，用户登录，颁发Token，Token信息解码
    * service-sms           `短信息服务` port=9610
        * 功能：手机验证码发送，邮件验证码发送
    * service-vod           `视频点播服务`
        * 功能：阿里云VOD视频点播
    * service-oss           `对象存储服务`
        * 功能：阿里云OSS对象存储
    * service-ams           `广告服务`
        * 功能：广告推荐
    * service-trade         `订单服务`
        * 功能：查询订单，购买课程
    * service-mms           `通信服务`
        * 功能：聊天，聊天室
    * service-statistice    `统计服务`
        * 功能：统计用户注册，统计日访问量......
    
    
## 已开发功能

service-ucenter：用户注册，用户登录，颁发Token，Token信息解码
service-sms：手机验证码发送，邮件验证码发送

## 未开发公能

