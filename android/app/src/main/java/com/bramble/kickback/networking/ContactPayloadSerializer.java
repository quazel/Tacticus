package com.bramble.kickback.networking;

import com.bramble.kickback.models.Person;

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

    public String serialize() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (String phoneNumber : contacts.keySet()) {
            jsonArray.put(phoneNumber);
        }

        jsonObject.put("contacts", jsonArray);
        return jsonObject.toString();
    }

    public ArrayList<Person> deserialize(String jsonString) {
        return null;
    }

}
