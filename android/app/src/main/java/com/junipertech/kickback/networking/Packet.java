package com.junipertech.kickback.networking;

import org.joda.time.DateTime;

public abstract class Packet {

    // Include in Packet headers
    protected PacketType type;
    protected int size;
    private DateTime now = DateTime.now();

    public String serialize() {
        return "{ }";
    }

}
