package com.ohooki.cardinal;

import com.ohooki.cardinal.common.packets.PacketsManager;
import com.ohooki.cardinal.common.redis.Connector;
import com.ohooki.cardinal.common.redis.RedisSubscriber;
import com.ohooki.cardinal.utils.LinuxBridge;

import java.util.UUID;

public abstract class Cardinal {
    protected UUID uuid;
    private Connector connector;
    private Configuration configuration;

    public UUID getUuid() {
        return this.uuid;
    }

    private RedisSubscriber redisSubscriber;
    private PacketsManager packetsManager;
    private LinuxBridge linuxBridge;

    public Connector getConnector() {
        return this.connector;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    public RedisSubscriber getRedisSubscriber() {
        return this.redisSubscriber;
    }

    public PacketsManager getPacketsManager() {
        return this.packetsManager;
    }

    public LinuxBridge getLinuxBridge() {
        return this.linuxBridge;
    }

    public Cardinal() {
        System.out.println("Cardinal version 1.0.0");

        System.out.println("----------------------------------------");

        this.uuid = UUID.randomUUID();

        this.configuration = new Configuration(this);

        this.configuration.load("config.json");

        this.connector = new Connector(this);

        this.redisSubscriber = new RedisSubscriber(this);

        this.packetsManager = new PacketsManager(this);

        this.linuxBridge = new LinuxBridge();

        enable();
    }

    public abstract void enable();

    public abstract void disable();
}
