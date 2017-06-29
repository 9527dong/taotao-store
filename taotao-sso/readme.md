本系统是单点登录系统。
## 为什么要使用单点登录系统
之前实现的登录和注册是在同一个tomcat内部完成，我们现在的系统架构是每一个系统都是由一个团队进行维护，每个系统都是单独部署运行一个单独的tomcat，所以，不能将用户的登录信息保存到session中（多个tomcat的session是不能共享的），所以我们需要一个单独的系统来维护用户的登录信息。
## 使用到的表
`tb_user`：用户表
## 已实现功能
### 1. 请求数据是否可用

|  请求方法|GET|
| :------------ | :------------ |
|URL|http://sso.taotao.com/rest/user/{param}/{type}|
|参数说明|格式如：zhangsan/ 1，其中zhangsan是校验的数据，type为类型，可选参数1、2、3分别代表username、phone、email|
|示例|http://sso.taotao.com/rest/user/zhangsan/1|
| 返回值|http响应状态：<br>200：请求成功<br>400：请求参数不合法<br>True：数据可用，false：数据不可用|

### 2. 用户注册

| 请求方法  |  POST |
| :------------ | :------------ |
| URL  |  http://sso.taotao.com/user |
|  参数说明 |  username //用户名<br>password //密码<br>phone//手机号<br>email //邮箱|
| 示例  |  http://sso.taotao.com/user/register |
| 返回值  | {status: 400,msg: "注册失败. 请校验数据后请再提交数据.",data: null} |

### 3. 用户登录

| 请求方法  |  POST |
| :------------ | :------------ |
| URL  |  http://sso.taotao.com/user/login |
|  参数说明 |  u //用户名<br>p //密码|
| 示例  |  http://sso.taotao.com/user/login |
| 返回值  | {status: 200,msg: "OK",data: "fe5cb546aeb3ce1bf37abcb08a40493e"} //登录成功，返回ticket|

### 4. 通过token查询用户信息

| 请求方法  |  GET |
| :------------ | :------------ |
| URL  |  http://sso.taotao.com/user/{ticket} |
|  参数说明 |  ticket //用户登录凭证|
| 示例  |  http://sso.taotao.com/user/fe5cb546aeb3ce1bf37abcb08a40493e |
| 返回值  | {status: 200,<br>msg: "OK",<br>data: "{"id":1,"username":"zhangzhijun","phone":"15800807944", "email":"420840806@qq.com","created":1414119176000,"updated":1414119179000}"}|

## 知识点
注册时的数据校验使用的是[Hibernate Validator](http://hibernate.org/validator/)。
token 是"TOKEN_"+username+sysdate md5加密后的字符串。
将token作为redis中的key，user对象转换为json作为redis中的value。保存的时间为30分钟。

## 未完成功能
用户退出登录的功能