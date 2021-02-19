## 智能图书管理系统-web后台管理

后端代码Java语言编写
~~~
    后端项目基于SpringBoot,SpringSecurity+Spring Data JPA,Spring Data ElasticSearch,RabbitMQ Mysql进行开发，
    tip：运行项目前确保rabbitmq和es以及msql的环境有开启
~~~


全局搜索：ElasticSearch，数据的同步利用RabbitMQ消息中间件来同步mysql数据到es

**学习记录**

关于SpringData ElasticSearch： 
 
 1. 添加图书数据mq生产消息，在这中间jpa的审计@CreationTimestamp跟手动Instant.now()序列化得到的结果不一样，导致一直消费队列监听消息之后导致异常
   
   解决方案：生产消息的时候先将实体类转成json数据，然后消息队列监听消息之后在反序列化回来
   
   猜测：可以跟es的client一样，重写RabbitTemplate，设置里面关于时间戳的序列化方法
   
 2. es保存数据之后通过搜索想拿到结果，发现Instant的反序列化错误，重写ElasticsearchOperations，设置entityMapper映射，处理时间转化问题
 
关于POI操作excel
 1. 项目中的批量导入图书数据涉及图书的价格，excel文件将所有的列统一设置成text文本类型，代码读取数值之后在去做类型转化，不然易出现奇奇怪怪问题。

关于爬取别人图书馆数据
 1. 爬取的是深圳图书馆数据，注意爬取频率，易出现接口防刷情况，接口在BookController中


前端仓库   
 https://github.com/PBOVE/library-webfront
 
演示地址
https://library.tibis.top/
