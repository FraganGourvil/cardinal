package com.ohooki.cardinal.server;

import com.ohooki.cardinal.Cardinal;
import com.ohooki.cardinal.common.actions.AbstractAction;
import com.ohooki.cardinal.server.actions.OpenServer;


public class CardinalServer
        extends Cardinal {
    public void enable() {
        OpenServer actionOpenServer = new OpenServer(this);

        getPacketsManager().registerAction("open", (AbstractAction) actionOpenServer);
    }

    public void disable() {
    }
}


