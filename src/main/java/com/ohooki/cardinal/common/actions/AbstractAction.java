package com.ohooki.cardinal.common.actions;

import com.ohooki.cardinal.Cardinal;

import java.util.HashMap;

public abstract class AbstractAction {

    protected Cardinal instance;

    protected HashMap<String, String> options;

    public void setInstance(Cardinal instance) {
        this.instance = instance;
    }

    public void setOptions(HashMap<String, String> options) {
        this.options = options;
    }

    public Cardinal getInstance() {
        return this.instance;
    }

    public HashMap<String, String> getOptions() {
        return this.options;
    }

    public AbstractAction(Cardinal instance) {
        this.instance = instance;
    }

    public abstract void handle(String requestUUID);
}
