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
    private final int port = 443;
    private final String baseURL = serverAddress + (port != 443 ? ":" + port : "");
    private final String userURL = baseURL + "user/";
    private final String loginURL = baseURL + "login";
    private final String registerURL = baseURL + "register";
    private final String checkCredentialsURL = baseURL + "verify_credential_uniqueness";
    private final String kickbackURL = baseURL + "kickback/";
    private final String analyticsURL = baseURL + "analytics/";

    public String login(String email, String password) throws IOException {
        URL requestURL = new URL(loginURL);
        HttpsURLConnection connection = (HttpsURLConnection) requestURL.openConnection();
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");
        String urlParameters = "";
        urlParameters += "email=" + email;
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
            Log.d("ConnectionHandler", "Received bad response from server.");
            throw new IOException("Received bad response from server.");
        }
    }

    public String register(String username, String password, String email, String phoneNumber,
                           String name, String birthdate, String sex) throws IOException
    {
        URL requestURL = new URL(registerURL);
        HttpsURLConnection connection = (HttpsURLConnection) requestURL.openConnection();
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");
        String urlParameters = "";
        urlParameters += "username=" + username;
        urlParameters += "&password=" + password;
        urlParameters += "&email=" + email;
        urlParameters += "&phone_number=" + phoneNumber;
        urlParameters += "&name=" + name;
        urlParameters += "&birthdate=" + birthdate;
        urlParameters += "&sex=" + sex;
        connection.setRequestProperty("Content-Length", "" + urlParameters.getBytes().length);
        connection.setUseCaches (false);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(urlParameters);
        writer.flush();
        writer.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200 || responseCode == 401) {
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
            throw new IOException("Received bad response from server.");
        }
    }

    public String checkCredentials(String username, String email, String phoneNumber) throws IOException {
        URL requestURL = new URL(checkCredentialsURL);
        HttpsURLConnection connection = (HttpsURLConnection) requestURL.openConnection();
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
        connection.setRequestProperty("Accept","*/*");
        String urlParameters = "";
        urlParameters += "username=" + username;
        urlParameters += "&email=" + email;
        urlParameters += "&phone_number=" + phoneNumber;
        connection.setRequestProperty("Content-Length", "" + urlParameters.getBytes().length);
        connection.setUseCaches (false);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(urlParameters);
        writer.flush();
        writer.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200 || responseCode == 401) {
            BufferedReader in;
            if (responseCode == 200) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();

            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                response.append("\n");
            }
            in.close();
            Log.d("ConnectionHandler", response.toString());
            return responseCode + ":" + response.toString().trim();
        }
        else {
            throw new IOException("Received bad response from server.");
        }
    }

}

