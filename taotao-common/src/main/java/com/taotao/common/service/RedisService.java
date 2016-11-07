package com.taotao.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {
	
	@Autowired(required = false) //从Spring容器中去查找bean，找到就注入，找不到就忽略
	private ShardedJedisPool shardedJedisPool;
	
	/**
	 * 执行set操作
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, String value){

        // 定义集群连接池
        ShardedJedis shardedJedis = null;
        try {
            // 从连接池中获取到jedis分片对象
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.set(key, value);
        } finally {
            if (null != shardedJedis) {
                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                shardedJedis.close();
            }
        }
	}
	
	/**
	 * 执行get操作
	 * @param key
	 * @return
	 */
	public String get(String key){

        // 定义集群连接池
        ShardedJedis shardedJedis = null;
        try {
            // 从连接池中获取到jedis分片对象
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis.get(key);
        } finally {
            if (null != shardedJedis) {
                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                shardedJedis.close();
            }
        }
	}
	
	public String set(String key, String value, Integer seconds){

        // 定义集群连接池
        ShardedJedis shardedJedis = null;
        try {
            // 从连接池中获取到jedis分片对象
            shardedJedis = shardedJedisPool.getResource();
            String str = shardedJedis.set(key, value);
            shardedJedis.expire(key, seconds);
            return str;
        } finally {
            if (null != shardedJedis) {
                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                shardedJedis.close();
            }
        }
	}
}
