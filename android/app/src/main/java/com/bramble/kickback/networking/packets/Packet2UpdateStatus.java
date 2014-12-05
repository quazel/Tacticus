package com.bramble.kickback.networking.packets;

import com.bramble.kickback.networking.ClientPacketType;

import org.json.JSONException;
import org.json.JSONObject;

public class Packet2UpdateStatus extends Packet {

    private boolean online;

    public Packet2UpdateStatus(boolean online) {
        type = ClientPacketType.PACKET_2_UPDATE_STATUS;
        this.online = online;
    }

    @Override
    public String serialize() throws JSONException {
        JSONObject json = getStandardHeaderJson();
        json.put("contents", online);
        return json.toString();
    }

}
