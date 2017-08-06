package com.taotao.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisServicePro {

    @Autowired(required = false) //从Spring容器中去查找bean，找到就注入，找不到就忽略
    private ShardedJedisPool shardedJedisPool;

    private <T> T execute(Function<T, ShardedJedis> function) {
        // 定义集群连接池
        ShardedJedis shardedJedis = null;
        try {
            // 从连接池中获取到jedis分片对象
            shardedJedis = shardedJedisPool.getResource();
            return function.callback(shardedJedis);
        } finally {
            if (null != shardedJedis) {
                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                shardedJedis.close();
            }
        }
    }

    /**
     * 执行set操作
     *
     * @param key key值，唯一
     * @param value 存储的值
     * @return String
     */
    public String set(final String key, final String value) {
        return this.execute(new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis shardedJedis) {

                return shardedJedis.set(key, value);
            }
        });
    }

    /**
     * 执行get操作
     *
     * @param key 将要获得的key
     * @return String
     */
    public String get(final String key) {
        return this.execute(new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis shardedJedis) {

                return shardedJedis.get(key);
            }
        });
    }

    public String set(final String key, final String value, final Integer seconds) {
        return this.execute(new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis shardedJedis) {
                String str = shardedJedis.set(key, value);
                shardedJedis.expire(key, seconds);
                return str;
            }
        });
    }

    public Long del(final String key) {
        return this.execute(new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis shardedJedis) {
                return shardedJedis.del(key);
            }
        });
    }

    /**
     * 设置生存时间
     * @param key
     * @param redisTime
     * @return
     */
    public Long expire(final String key, final Integer redisTime) {

        return this.execute(new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis shardedJedis) {
                return shardedJedis.expire(key, redisTime);
            }
        });
    }

    /**
     * Hash结构的获取值
     * @param key Redis中的值的key值
     * @param field Hash结构中的字段
     * @return
     */
    public String hget(final String key, final String field) {

        return this.execute(new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis shardedJedis) {
                return shardedJedis.hget(key, field);
            }
        });
    }

    /**
     * Hash结构的设置值并且设置生存时间
     * @param key
     * @param field
     * @param value
     * @param seconds
     * @return
     */
    public Long hset(final String key, final String field, final String value, final Integer seconds) {

        return this.execute(new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis shardedJedis) {
                Long count = shardedJedis.hset(key, field, value);
                //设置生存时间
                shardedJedis.expire(key, seconds);
                return count;
            }
        });
    }

    /**
     * hsah结构的数据删除
     * @param key
     * @param field
     * @return
     */
    public Long hdel(final String key, final String... field) {

        return this.execute(new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis shardedJedis) {
                return shardedJedis.hdel(key, field);

            }
        });
    }

    public void hgetAll(String key) {
        return this.execute(new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis shardedJedis) {
                return shardedJedis.hgetAll()

            }
        });
    }
}
