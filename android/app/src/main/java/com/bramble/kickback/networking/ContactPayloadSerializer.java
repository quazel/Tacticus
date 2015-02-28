package com.bramble.kickback.networking;

import com.bramble.kickback.models.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContactPayloadSerializer {

    private ArrayList<Person> contacts;

    public ContactPayloadSerializer(ArrayList<Person> contacts) {
        this.contacts = contacts;
    }

    public String serialize() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Person contact : contacts) {
            jsonArray.put(contact.getPhoneNumber());
        }

        jsonObject.put("contacts", jsonArray);
        return jsonObject.toString();
    }

    public ArrayList<Person> deserialize() {
        return null;
    }

}
