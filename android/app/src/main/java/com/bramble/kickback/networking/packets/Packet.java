package com.bramble.kickback.networking.packets;

import com.bramble.kickback.networking.ClientPacketType;
import com.bramble.kickback.networking.SerializationException;

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

    protected JSONObject getStandardHeaderJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("packet_type", type.getValue());
        json.put("timestamp", now.getMillis() / 1000);
        return json;
    }

    public String serialize() throws JSONException {
        return getStandardHeaderJson().toString();
    }


}
