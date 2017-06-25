此模块是淘淘商城的静态资源系统。
## 配置方法
### 配置host
127.0.0.1 static.taotao.com
## 配置Nginx
在`nginx.conf`文件中配置：
```
server {
    listen       80;
    server_name  static.taotao.com;

    proxy_set_header X-Forwarded-Host $host;
    proxy_set_header X-Forwarded-Server $host;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

    location / {
      root  c:\\src\\taotao-static;
    }
}
```
