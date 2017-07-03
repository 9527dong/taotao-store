## 通过token进行了2次查询
一次是在拦截器中查询，一次是在Controller查询，存在性能和资源浪费问题。

如何将拦截器中的数据传递到Controller？

方案：
1. 将User对象放置到request对象中
2. 使用ThreadLocal实现:进入tomcat和产生响应前，都处于同一个线程中

