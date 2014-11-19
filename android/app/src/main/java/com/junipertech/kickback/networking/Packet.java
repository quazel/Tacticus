package com.junipertech.kickback.networking;

import org.joda.time.DateTime;

public abstract class Packet {

    // Include in Packet headers
    private PacketType type;
    private int size;
    private DateTime now = DateTime.now();

}
