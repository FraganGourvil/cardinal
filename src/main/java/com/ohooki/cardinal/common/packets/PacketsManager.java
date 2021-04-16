package com.ohooki.cardinal.common.packets;

import com.ohooki.cardinal.Cardinal;
import com.ohooki.cardinal.common.actions.AbstractAction;

import java.util.HashMap;

public class PacketsManager {
    private Cardinal instance;
    private HashMap<String, AbstractAction> actions;

    public Cardinal getInstance() {
        return this.instance;
    }

    public HashMap<String, AbstractAction> getActions() {
        return this.actions;
    }

    public PacketsManager(Cardinal instance) {
        this.instance = instance;

        this.actions = new HashMap<>();
    }


    public void process(String uuid, Packet packet) {

        String callable = packet.getAction();

        AbstractAction action = this.actions.get(callable);

        action.setOptions(packet.getOptions());

        action.handle(uuid);
    }


    public void registerAction(String alias, AbstractAction action) {
        this.actions.put(alias, action);
    }
}


