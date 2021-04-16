package com.ohooki.cardinal.common.redis;

import com.ohooki.cardinal.Cardinal;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import static com.ohooki.cardinal.utils.UtilsMethods.info;

public class Connector {
    private final Cardinal instance;
    public Thread reconnection;
    private JedisPool jedisPool;

    public Connector(Cardinal instance) {
        this.instance = instance;

        connect();

        this.reconnection = new Thread(() -> {

            try {

                while (true) {

                    try {
                        this.jedisPool.getResource().close();
                    } catch (Exception e) {

                        info("Error Redis connection, try to reconnect");
                        connect();

                    }


                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                return;
            }
        }, "Redis Connect");


        this.reconnection.start();
    }


    public void connect() {

        info("Connecting to redis...");

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxTotal(-1);
        jedisPoolConfig.setJmxEnabled(false);

        this.jedisPool = new JedisPool(jedisPoolConfig, (this.instance.getConfiguration()).redisIp, (this.instance.getConfiguration()).redisPort, 0);

        try {
            this.jedisPool.getResource().close();
        } catch (Exception e) {

            info("Can't connect to the database (Redis)");

            System.exit(8);
        }


        info("Connected to the database (Redis)");
    }


    public void disconnect() {
        this.reconnection.interrupt();
    }


    public JedisPool getJedisPool() {
        return this.jedisPool;
    }


    public Jedis getResource() {
        return this.jedisPool.getResource();
    }
}
