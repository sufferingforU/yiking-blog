# yiking-blog
 博客系统（包含客户端与博客管理系统）
系统介绍：该博客系统除了实现一般的博客系统的所有功能后还实现了文章查重的功能。在前端我使用的是vue框架使用axios与后端进行交互，界面设计使用的是element-ui。在后端我使用了springboot+SSM+mysql进行开发，引入springsecurity对角色进行认证和授权，并且引入redis缓存对其进行了优化，引入了sentinel进行流量管理。
