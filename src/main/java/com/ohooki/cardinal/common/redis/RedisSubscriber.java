package com.ohooki.cardinal.common.redis;

import com.google.gson.Gson;
import com.ohooki.cardinal.Cardinal;
import com.ohooki.cardinal.common.packets.Packet;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import static com.ohooki.cardinal.utils.UtilsMethods.info;

public class RedisSubscriber extends JedisPubSub {
    private final Cardinal instance;
    private boolean continueLoop;

    public RedisSubscriber(Cardinal instance) {
        this.instance = instance;

        this.continueLoop = true;

        (new Thread(() -> {
            while (this.continueLoop) {
                Jedis jedis = this.instance.getConnector().getJedisPool().getResource();


                try {
                    jedis.psubscribe(this, "*");
                } catch (Exception e) {
                    e.printStackTrace();
                }


                System.out.println("Disconnected from Redis");


                jedis.close();
            }
        })).start();

        info("Connected from Redis");

        while (!isSubscribed()) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }

        info("PubSub suscribed");

    }


    public void send(String channel, String packet) {

        Jedis jedis = this.instance.getConnector().getJedisPool().getResource();
        jedis.publish(channel, packet);
        jedis.close();

    }


    public void onPMessage(String pattern, String channel, String message) {

        Gson gson = new Gson();

        Packet packet = gson.fromJson(message, Packet.class);

        if (this.instance instanceof com.ohooki.cardinal.server.CardinalServer && packet.getFrom().equals("client")) {
            this.instance.getPacketsManager().process(channel, packet);
        }


        if (this.instance instanceof com.ohooki.cardinal.client.CardinalClient && packet.getFrom().equals("server")) {
            this.instance.getPacketsManager().process(channel, packet);
        }

    }
}