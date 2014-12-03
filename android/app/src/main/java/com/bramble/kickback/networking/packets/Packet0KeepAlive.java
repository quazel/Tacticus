package com.bramble.kickback.networking.packets;

import com.bramble.kickback.networking.ClientPacketType;

import org.json.JSONException;
import org.json.JSONObject;

public class Packet0KeepAlive extends Packet {

    private String contents = "Ping!";

    public Packet0KeepAlive() {
        type = ClientPacketType.PACKET_0_KEEP_ALIVE;
    }

    @Override
    public String serialize() throws JSONException {
        JSONObject json = getStandardHeaderJson();
        json.put("contents", contents);
        return json.toString();
    }

}
