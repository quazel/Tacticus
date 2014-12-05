package com.bramble.kickback.networking.packets;

import com.bramble.kickback.models.Kickback;
import com.bramble.kickback.networking.ClientPacketType;

import org.json.JSONException;
import org.json.JSONObject;

public class Packet4AddScheduleItem extends Packet {

    private Kickback kickback;

    public Packet4AddScheduleItem(Kickback kickback) {
        type = ClientPacketType.PACKET_4_ADD_SCHEDULE_ITEM;
        this.kickback = kickback;
    }

    @Override
    public String serialize() throws JSONException {
        JSONObject json = getStandardHeaderJson();
        JSONObject kickbackJson = new JSONObject();
        kickbackJson.put("start", kickback.getStart());
        kickbackJson.put("stop", kickback.getStop());
        kickbackJson.put("location", kickback.getLocation());
        json.put("contents", kickbackJson);
        return json.toString();
    }

}
