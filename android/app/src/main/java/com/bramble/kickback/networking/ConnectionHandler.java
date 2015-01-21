package com.bramble.kickback.networking;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ConnectionHandler {

    private final String serverAddress = "https://api.kickback.stevex86.com/";
    private final int port = 80;
    private final String baseURL = serverAddress + (port != 80 ? ":" + port : "");
    private final String userURL = baseURL + "user/";
    private final String loginURL = baseURL + "login";
    private final String kickbackURL = baseURL + "kickback/";
    private final String analyticsURL = baseURL + "analytics/";

    public String login(String username, String password) throws IOException {
        URL requestURL = new URL(loginURL);
        HttpsURLConnection connection = (HttpsURLConnection) requestURL.openConnection();
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");
        String urlParameters = "";
        urlParameters += "username=" + username;
        urlParameters += "&password=" + password;
        connection.setRequestProperty("Content-Length", "" + urlParameters.getBytes().length);
        connection.setUseCaches (false);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(urlParameters);
        writer.flush();
        writer.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Log.d("ConnectionHandler", response.toString());
            return response.toString();
        }
        else {
            throw new IOException();
        }
    }

}

