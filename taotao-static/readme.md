此模块是淘淘商城的静态资源系统。
## 一、使用静态资源系统的好处
对用户而言，如果项目中多个模块使用到公用的静态资源，只需要加载一次即可
## 二、配置方法
### 配置host
127.0.0.1 static.taotao.com
### 配置Nginx
在`nginx.conf`文件中配置：
```
server {
    listen       80;
    server_name  static.taotao.com;

    proxy_set_header X-Forwarded-Host $host;
    proxy_set_header X-Forwarded-Server $host;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

    location / {
      #替换成自己的实际路径
      root  c:\\src\\taotao-static;
    }
}
```
## 三、使用方法
1. 在需要使用静态资源的jsp页面，添加以下代码
 ```java
<%
	String staticUrl = "http://static.taotao.com";
	request.setAttribute("staticUrl", staticUrl);
%>
```
2. 再用到静态资源的地方使用以下格式来进行调用：
```html
href="${staticUrl}/css/regist.personal.css"
```
## 四、哪些模块使用了该模块
taotao-sso
taotao-web（未完成）