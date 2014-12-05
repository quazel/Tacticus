package com.bramble.kickback.networking.packets;

import com.bramble.kickback.networking.ClientPacketType;

import org.json.JSONException;
import org.json.JSONObject;

public class Packet3FetchFriends extends Packet{

    public Packet3FetchFriends() {
        type = ClientPacketType.PACKET_3_FETCH_FRIENDS;
    }

    @Override
    public String serialize() throws JSONException {
        JSONObject json = getStandardHeaderJson();
        return json.toString();
    }

}
