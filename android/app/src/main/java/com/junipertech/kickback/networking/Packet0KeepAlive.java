package com.junipertech.kickback.networking;

public class Packet0KeepAlive extends Packet {

    public Packet0KeepAlive() {
        type = PacketType.PACKET_0_KEEP_ALIVE;
        size = "Ping!".length();
    }

    public String serialize() {
        return "";
    }

}
