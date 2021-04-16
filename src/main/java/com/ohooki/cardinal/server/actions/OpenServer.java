
package com.ohooki.cardinal.server.actions;


import com.google.gson.Gson;
import com.ohooki.cardinal.Cardinal;
import com.ohooki.cardinal.common.actions.AbstractAction;
import com.ohooki.cardinal.common.templates.Hub;
import com.ohooki.cardinal.common.templates.MinecraftServer;
import com.ohooki.cardinal.server.pakets.RegisterServerPacket;
import com.ohooki.cardinal.utils.LinuxBridge;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import redis.clients.jedis.Jedis;

import static com.ohooki.cardinal.utils.UtilsMethods.info;

public class OpenServer extends AbstractAction {

    public static int port = 25565;

    public OpenServer(Cardinal instance) {

        super(instance);

        if (port - 1 > 24000) {

            port--;

        }

    }

    public void handle(String requestUUID) {

        Gson gson = new Gson();

        String template = this.options.get("template");

        String containerName = template + "" + port;

        LinuxBridge linux = this.instance.getLinuxBridge();

        //linux.cp("/cardinal/templates/" + template, "/cardinal/tmp/" + this.uuid, true);

        linux.dockerRun(containerName, port);

        (new Thread(() -> {

            SocketAddress sockaddr = new InetSocketAddress("127.0.0.1", port);

            boolean launched = false;

            info("Trying to connect -> " + "127.0.0.1:" + port);

            while (!launched) {

                launched = true;

                Socket socket = new Socket();

                try {

                    socket.connect(sockaddr, 1000);

                } catch (IOException stex) {

                    launched = false;

                } finally {

                    try {

                        socket.close();

                        Thread.sleep(1000L);

                    } catch (IOException | InterruptedException iOException) {

                    }

                }
            }

            info("Connection etablished -> " + "127.0.0.1:" + port);

            this.instance.getRedisSubscriber().send(requestUUID, gson.toJson(new RegisterServerPacket("127.0.0.1", port)));

        })).start();


        MinecraftServer serverTemplate = null;

        if("Hub".equalsIgnoreCase(template)) {

            serverTemplate = new Hub("127.0.0.1", port);

        }

        Jedis jedis = this.instance.getConnector().getJedisPool().getResource();
        jedis.hset("servers", requestUUID, gson.toJson(serverTemplate));

    }

}
