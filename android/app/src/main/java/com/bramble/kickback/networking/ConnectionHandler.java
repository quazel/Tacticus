package com.bramble.kickback.networking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.bramble.kickback.models.Kickback;
import com.bramble.kickback.models.Friend;
import com.bramble.kickback.models.Person;
import com.bramble.kickback.models.User;

public class ConnectionHandler {

    private final String serverAddress = "";
    private final int port = 80;
    private final String baseURL = serverAddress + (port != 80 ? ":" + port : "");
    private final String userURL = baseURL + "user/";
    private final String loginURL = baseURL + "login/";
    private final String kickbackURL = baseURL + "kickback/";
    private final String analyticsURL = baseURL + "analytics/";

    public ArrayList<Friend> getFriends() throws IOException, JSONException {
        URL requestURL = new URL(userURL+"/fetch_friends");
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();

        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return new Deserializer().deserializeFriends(response.toString());
        }
        else {
            throw new IOException();
        }
    }

    public ArrayList<Kickback> getKickbacks() throws IOException, JSONException {
        URL requestURL = new URL(userURL+"fetch_kickbacks");
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();

        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if(responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return new Deserializer().deserializeKickbacks(response.toString());
        }
        else {
            throw new IOException();
        }
    }


}
