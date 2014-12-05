package com.bramble.kickback.networking.packets;

import com.bramble.kickback.models.Kickback;
import com.bramble.kickback.networking.ClientPacketType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Packet5UpdateScheduleItem extends Packet {

    private Kickback kickback;
    private String operation;

    public Packet5UpdateScheduleItem(Kickback kickback, String operation) {
        type = ClientPacketType.PACKET_5_UPDATE_SCHEDULE_ITEM;
        this.kickback = kickback;
        this.operation = operation;
    }

    @Override
    public String serialize() throws JSONException {
        JSONObject json = getStandardHeaderJson();
        JSONObject kickbackJson = new JSONObject();
        kickbackJson.put("start", kickback.getStart());
        kickbackJson.put("stop", kickback.getStop());
        kickbackJson.put("location", kickback.getLocation());
        JSONArray array = new JSONArray();
        array.put(kickbackJson);
        array.put(operation);
        json.put("contents", array);
        return json.toString();
    }

}
