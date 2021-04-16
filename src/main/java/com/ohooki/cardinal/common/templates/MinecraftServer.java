package com.ohooki.cardinal.common.templates;

public abstract class MinecraftServer {
    protected String ip;
    protected int port;

    public String getIp() {
        return this.ip;
    }

    public int getPort() {
        return this.port;
    }

    public MinecraftServer(String ip, int port) {
        this.ip = ip;

        this.port = port;
    }
}