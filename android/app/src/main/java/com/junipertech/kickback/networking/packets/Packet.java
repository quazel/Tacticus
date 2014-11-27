package com.junipertech.kickback.networking.packets;

import com.junipertech.kickback.networking.ClientPacketType;
import com.junipertech.kickback.networking.SerializationException;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Packet {

    // Include in Packet headers
    protected ClientPacketType type;
    private DateTime now = DateTime.now();

    public byte[] getBytes() throws SerializationException {
        try {
            return serialize().getBytes();
        }
        catch(JSONException ex) {
            throw new SerializationException();
        }
    }

    protected JSONObject getStandardHeader() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("packet_type", type);
        json.put("timestamp", now.getMillis() / 1000);
        return json;
    }

    protected String serialize() throws JSONException {
        return getStandardHeader().toString();
    }


}
