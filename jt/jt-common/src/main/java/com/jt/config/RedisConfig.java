package com.jt.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tomcat.jni.Pool;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
	@Value("${redis.nodes}")
	private String nodes;

	// redis 集群
	@Bean
	public JedisCluster jedisCluster() {
		Set<HostAndPort> nodes = getNode();
		return new JedisCluster(nodes);
	}

	private Set<HostAndPort> getNode() {
		Set<HostAndPort> set = new HashSet<>();
		String[] hapNode = nodes.split(",");
		for (String string : hapNode) {
			String host = string.split(":")[0];
			String ports = string.split(":")[1];
			int port = Integer.parseInt(ports);
			HostAndPort hapAndPort = new HostAndPort(host, port);
			set.add(hapAndPort);
		}
		return set;
	}
//  哨兵
//	@Value("${redis.masterName}")
//	private String masterName;

//	@Bean
//	public Jedis jedis(JedisSentinelPool jedisSentinelPool) {
//		return jedisSentinelPool.getResource();
//	}
//
//	@Bean//("jedisSentinelPool")
//	public JedisSentinelPool jedisSentinelPool() {
//		Set<String> sentinels = new HashSet<>();
//		sentinels.add(nodes);
//		return new JedisSentinelPool(masterName, sentinels);
//	}
//  分片
//	@Bean
//	public ShardedJedis jedis() {
//		List<JedisShardInfo> shards = getList();
//		return new ShardedJedis(shards);
//	}
//
//	private List<JedisShardInfo> getList() {
//		String[] nodeArray = nodes.split(",");
//		List<JedisShardInfo> list = new ArrayList<>();
//		for (String node : nodeArray) {
//			String ip = node.split(":")[0];
//			String prot = node.split(":")[1];
//			JedisShardInfo jedisShardInfo = new JedisShardInfo(ip, prot);
//			list.add(jedisShardInfo);
//		}
//		return list;
//	}

}
