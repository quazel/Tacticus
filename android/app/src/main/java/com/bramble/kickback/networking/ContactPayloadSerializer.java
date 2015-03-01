package com.bramble.kickback.networking;

import com.bramble.kickback.models.Person;
import com.bramble.kickback.models.RemoteUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactPayloadSerializer {

    private HashMap<String, Person> contacts;

    public ContactPayloadSerializer(HashMap<String, Person> contacts) {
        this.contacts = contacts;
    }

    public String serializePayload() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (String phoneNumber : contacts.keySet()) {
            jsonArray.put(phoneNumber);
        }

        jsonObject.put("contacts", jsonArray);
        return jsonObject.toString();
    }

    public ArrayList<RemoteUser> deserializeResponse(String jsonString) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonString);
        ArrayList<RemoteUser> people = new ArrayList<RemoteUser>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name = jsonObject.getString("name");
            String nickname = jsonObject.getString("nickname");
            String email = jsonObject.getString("email");
            String phoneNumber = jsonObject.getString("phone_number");
            String sex = jsonObject.getString("sex");

            RemoteUser remoteUser = new RemoteUser(nickname, name, email, phoneNumber, sex, false, true);
            people.add(remoteUser);
        }

        return people;
    }

}
