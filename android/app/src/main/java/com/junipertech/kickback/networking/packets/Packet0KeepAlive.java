package com.junipertech.kickback.networking.packets;

import com.junipertech.kickback.networking.ClientPacketType;

import org.json.JSONException;
import org.json.JSONObject;

public class Packet0KeepAlive extends Packet {

    private String contents = "Ping!";

    public Packet0KeepAlive() {
        type = ClientPacketType.PACKET_0_KEEP_ALIVE;
    }

    @Override
    protected String serialize() throws JSONException {
        JSONObject json = getStandardHeader();
        json.put("contents", contents);
        return json.toString();
    }

}
