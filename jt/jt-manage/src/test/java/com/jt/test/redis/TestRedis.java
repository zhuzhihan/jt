package com.jt.test.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Transaction;

public class TestRedis {
	@Test
	public void test01() {
		Jedis jedis = new Jedis("192.168.153.129", 6379);
		jedis.set("1904", "1904版redis学习");
		String result = jedis.get("1904");

		System.out.println(result);
		jedis.expire("1904", 100);

		jedis.setex("1903", 100, "zhuzhihan");

		jedis.set("1904", "1904班马上毕业了!");
		jedis.del("1904");
		jedis.setnx("1904", "年薪50万");
		result = jedis.get("1904");
		System.out.println("获取修改之后的值:" + result);

		jedis.set("1904A", "你好!!!", "NX", "EX", 100);
		String result2 = jedis.get("1904A");
		System.out.println(result2);
	}

	@Test
	public void test02() {
		Jedis jedis = new Jedis("192.168.153.129", 6379);
		jedis.hset("person", "id", "1");
		jedis.hset("person", "name", "zhuzhihan");
		jedis.hset("person", "age", "22");

		Map<String, String> result = jedis.hgetAll("person");
		System.out.println(result);
	}

	@Test
	public void test03() {
		Jedis jedis = new Jedis("192.168.153.129", 6379);
		jedis.lpush("list", "1", "2", "3", "4");
		System.out.println(jedis.rpop("list"));
	}

	@Test
	public void test04() {
		Jedis jedis = new Jedis("192.168.153.129", 6379);
		Transaction transaction = jedis.multi();
		try {
			transaction.set("aa", "aaa");
			transaction.set("bb", "bbb");
			transaction.set("vv", "nnn");
			// int a = 1/0;
			transaction.exec();
			// int a = 1/0;
		} catch (Exception e) {
			transaction.discard();
		}
	}

	/**
	 * redis分片操作
	 */
	@Test
	public void test05() {
		String host = "192.168.153.129";
		ArrayList<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo(host, 6379));
		shards.add(new JedisShardInfo(host, 6380));
		shards.add(new JedisShardInfo(host, 6381));
		ShardedJedis jedis = new ShardedJedis(shards);
		jedis.set("1904", "你好,我好,大家好!!!");
		System.out.println(jedis.get("1904"));
	}

	/**
	 * 整合哨兵
	 */
	@Test
	public void test06() {
		Set<String> sentinels = new HashSet<>();
		sentinels.add("192.168.153.129:26379");
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = pool.getResource();
		jedis.set("啦啦啦啦啦啦!", "哈哈哈哈哈哈哈哈哈!!");
		System.out.println(jedis.get("啦啦啦啦啦啦!"));
	}

	/**
	 * 整合redis集群
	 */
	@Test
	public void test07() {
		Set<HostAndPort> node = new HashSet<>();
		node.add(new HostAndPort("192.168.153.129", 7000));
		node.add(new HostAndPort("192.168.153.129", 7001));
		node.add(new HostAndPort("192.168.153.129", 7002));
		node.add(new HostAndPort("192.168.153.129", 7003));
		node.add(new HostAndPort("192.168.153.129", 7004));
		node.add(new HostAndPort("192.168.153.129", 7005));
		JedisCluster jedisCluster = new JedisCluster(node);

		jedisCluster.set("1904", "redis集群搭建完成!!!");
		System.out.println(jedisCluster.get("1904"));
	}
}
