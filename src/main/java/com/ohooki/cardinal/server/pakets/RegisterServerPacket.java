package com.ohooki.cardinal.server.pakets;

import com.ohooki.cardinal.common.packets.Packet;

public class RegisterServerPacket extends Packet {

    public RegisterServerPacket(String ip, int port) {

        this.from = "server";

        this.action = "register";

        this.target = "proxy";

        this.options.put("ip", ip);
        this.options.put("port", port + "");

    }

}
