package com.bramble.kickback.networking.packets;

import com.bramble.kickback.networking.ClientPacketType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Packet1Login extends Packet {

    private String username;
    private String password;
    private String token;

    public Packet1Login(String username, String password) {
        type = ClientPacketType.PACKET_1_LOGIN;
        this.username = username;
        this.password = password;
    }

    public Packet1Login(String token) {
        type = ClientPacketType.PACKET_1_LOGIN;
        this.token = token;
    }

    @Override
    public String serialize() throws JSONException {
        JSONObject json = getStandardHeaderJson();
        if (token != null) {
            JSONObject tokenJson = new JSONObject();
            tokenJson.put("token", token);
            json.put("contents", tokenJson);
            return json.toString();
        }
        else {
            JSONArray credentialsArray = new JSONArray();
            credentialsArray.put(username);
            credentialsArray.put(password);
            JSONObject credentialsObject = new JSONObject();
            credentialsObject.put("credentials", credentialsArray);
            json.put("contents", credentialsObject);
            return json.toString();
        }
    }

}
