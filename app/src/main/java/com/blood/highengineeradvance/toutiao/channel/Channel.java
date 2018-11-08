package com.blood.highengineeradvance.toutiao.channel;


public class Channel {

    public String name;

    public int type;

    public Channel(String name, int type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
