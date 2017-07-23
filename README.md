# taotao-store
淘淘网上商城是一个综合性的B2C平台，类似京东商城、天猫商城。会员可以在商城浏览商品、下订单，以及参加各种活动。商家可以在入住淘淘商城，在该平台上开店出售自己的商品，并且得到淘淘商城提供的可靠的服务。管理员、运营可以在平台后台管理系统中管理商品、订单、会员等。客服可以在后台管理系统中处理用户的询问以及投诉。
## taotao-common
存放一些通用的代码
## taotao-manage
该系统是后台管理系统，可以对商品进行一些增删改查的操作。该项目为Maven聚合项目，

## RabbitMQ
启动RabbitMQ管理工具：

`rabbitmq-plugins enable rabbitmq_management`

## 配置hosts
```
#淘淘商城
127.0.0.1 manage.taotao.com
127.0.0.1 image.taotao.com
127.0.0.1 www.taotao.com
127.0.0.1 sso.taotao.com
127.0.0.1 static.taotao.com
127.0.0.1 order.taotao.com
127.0.0.1 solr.taotao.com
127.0.0.1 search.taotao.com
127.0.0.1 cart.taotao.com
127.0.0.1 ssoquery.taotao.com
```