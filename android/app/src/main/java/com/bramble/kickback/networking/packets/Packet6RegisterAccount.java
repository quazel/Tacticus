package com.bramble.kickback.networking.packets;

import org.json.JSONException;
import org.json.JSONObject;

public class Packet6RegisterAccount extends Packet {

    private String username;
    private String password;
    private String email;

    public Packet6RegisterAccount(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public String serialize() throws JSONException {
        JSONObject json = getStandardHeaderJson();
        JSONObject credentialsObject = new JSONObject();
        credentialsObject.put("username", username);
        credentialsObject.put("password", password);
        credentialsObject.put("email", email);
        json.put("contents", credentialsObject);
        return json.toString();
    }

}
