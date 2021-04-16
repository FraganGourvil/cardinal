package com.ohooki.cardinal.common.packets;


import java.util.HashMap;

public class Packet {

    protected String from;
    protected String action;
    protected String target;

    protected HashMap<String, String> options = new HashMap<>();

    public void setFrom(String from) {
        this.from = from;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getFrom() {
        return this.from;
    }

    public String getAction() {
        return this.action;
    }

    public String getTarget() {
        return this.target;
    }

    public HashMap<String, String> getOptions() {
        return this.options;
    }

    public String toString() {
        return "Packet{from='" + this.from + '\'' + ", action='" + this.action + '\'' + ", target='" + this.target + '\'' + ", options=" + this.options + '}';
    }

    public void setOptions(HashMap<String, String> options) {
        this.options = options;
    }

}
