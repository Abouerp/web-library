## 智能图书管理系统-web后台管理

后端代码Java语言编写
~~~
    后端项目基于SpringBoot,SpringSecurity+Spring Data JPA,Spring Data ElasticSearch,RabbitMQ Mysql进行开发，
    tip：运行项目前确保rabbitmq和es以及msql的环境有开启
~~~

 - SpringBoot 2.2.5
 - ElasticSearch 6.8.6
 - MYSQL 5.6
 - JDK 1.8
 - Rabbit MQ


全局搜索：ElasticSearch，数据的同步利用RabbitMQ消息中间件来同步mysql数据到es

登陆流程：3个请求
- GET 请求  /api/user/me 获得token
- POST 请求 /api/user/login  header带上  X-XSRF-TOKEN：token
- GET 请求 /api/user/me  重新获得token信息
- 请求资源重新设置 header中 X-XSRF-TOKEN的值

**学习记录**

关于SpringData ElasticSearch： 
 
 -  添加图书数据mq生产消息，在这中间jpa的审计@CreationTimestamp跟手动Instant.now()序列化得到的结果不一样，导致一直消费队列监听消息之后导致异常
   
   解决方案：生产消息的时候先将实体类转成json数据，然后消息队列监听消息之后在反序列化回来
   
   猜测：可以跟es的client一样，重写RabbitTemplate，设置里面关于时间戳的序列化方法
   
 -  es保存数据之后通过搜索想拿到结果，发现Instant的反序列化错误，重写ElasticsearchOperations，设置entityMapper映射，处理时间转化问题
 
关于POI操作excel
 -  项目中的批量导入图书数据涉及图书的价格，excel文件将所有的列统一设置成text文本类型，代码读取数值之后在去做类型转化，不然易出现奇奇怪怪问题。

关于爬取别人图书馆数据
 -  爬取的是深圳图书馆数据，注意爬取频率，易出现接口防刷情况，接口在BookController中

关于日志
 -  日志利用spring aop切面操作，拦截控制器前的一切请求，拦截前记录时间，操作完之后重新获取时间得到操作的时间，以及记录操作是否成功 


前端仓库   
 https://github.com/PBOVE/library-webfront
 
演示地址
https://library.tibis.top/

已有功能：
用户管理 角色管理 日志管理 全局搜索 问题管理 图书分类管理 图书管理 图书详细管理

待实现功能：
搜索首页热搜问题

小程序后端仓库
https://github.com/Abouerp/applet-library