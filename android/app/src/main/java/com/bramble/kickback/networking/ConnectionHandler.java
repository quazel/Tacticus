package com.bramble.kickback.networking;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class ConnectionHandler {

    private final String serverAddress = "https://kickback-server.appspot.com/";
    private final int port = 443;
    private final String baseURL = serverAddress + (port != 443 ? ":" + port : "");
    private final String userURL = baseURL + "user/";
    private final String loginURL = baseURL + "login";
    private final String logoutURL = baseURL + "logout";
    private final String registerURL = baseURL + "register";
    private final String checkCredentialsURL = baseURL + "verify_credential_uniqueness";
    private final String pingURL = baseURL + "ping";
    private final String startKickbackURL = baseURL + "kickback";
    private final String pollURL = baseURL + "poll";
    private final String goOfflineURL = baseURL + "go_offline";
    private final String searchURL = baseURL + "search";
    private final String addFriendURL = baseURL + "add_friend";
    private final String removeFriendURL = baseURL + "remove_friend";
    private final String checkContactsURL = baseURL + "check_contacts";
    private final String analyticsURL = baseURL + "analytics/";

    private HttpsURLConnection buildGetRequest(String url, HashMap<String, String> params) throws IOException {
        int count = 0;
        StringBuilder urlParametersBuilder = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (count > 0) {
                urlParametersBuilder.append("&");
            }
            urlParametersBuilder.append(key).append("=").append(value);
            count++;
        }

        url = url + urlParametersBuilder.toString();
        URL requestURL = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) requestURL.openConnection();
        connection.setDoInput(true);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");
        connection.setUseCaches(false);

        return connection;
    }

    private HttpsURLConnection buildPostRequest(String url, HashMap<String, String> params) throws IOException {
        URL requestURL = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) requestURL.openConnection();
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("charset", "utf-8");

        StringBuilder urlParametersBuilder = new StringBuilder();
        int count = 0;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (count > 0) {
                urlParametersBuilder.append("&");
            }
            urlParametersBuilder.append(key).append("=").append(value);
            count++;
        }

        String urlParameters = urlParametersBuilder.toString();
        connection.setRequestProperty("Content-Length", "" + urlParameters.getBytes().length);
        connection.setUseCaches (false);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(urlParameters);
        writer.flush();
        writer.close();

        return connection;
    }

    private String buildResponse(HttpsURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        if (responseCode == 200 || responseCode == 401) {
            BufferedReader in;
            if (responseCode > 400) {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            else {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            response.append(responseCode);
            response.append(":");

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

    public String login(String email, String password) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        HttpsURLConnection connection = buildPostRequest(loginURL, params);
        return buildResponse(connection);
    }

    public String logout(String sessionId) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("session_id", sessionId);

        HttpsURLConnection connection = buildPostRequest(logoutURL, params);
        return buildResponse(connection);
    }

    public String register(String nickname, String password, String email, String phoneNumber,
                           String name, String birthdate, String sex) throws IOException
    {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("nickname", nickname);
        params.put("password", password);
        params.put("email", email);
        params.put("phone_number", phoneNumber);
        params.put("name", name);
        params.put("birthdate", birthdate);
        params.put("sex", sex);

        HttpsURLConnection connection = buildPostRequest(registerURL, params);
        return buildResponse(connection);
    }

    public String checkCredentials(String email, String phoneNumber) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("phone_number", phoneNumber);

        HttpsURLConnection connection = buildPostRequest(checkCredentialsURL, params);
        return buildResponse(connection);
    }

    public String ping(String sessionId) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("session_id", sessionId);

        HttpsURLConnection connection = buildPostRequest(pingURL, params);
        return buildResponse(connection);
    }

    public String startKickingBack(String sessionId) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("session_id", sessionId);

        HttpsURLConnection connection = buildPostRequest(startKickbackURL, params);
        return buildResponse(connection);
    }

    public String poll(String sessionId) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("session_id", sessionId);

        HttpsURLConnection connection = buildPostRequest(pollURL, params);
        return buildResponse(connection);
    }

    public String goOffline(String sessionId) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("session_id", sessionId);

        HttpsURLConnection connection = buildPostRequest(goOfflineURL, params);
        return buildResponse(connection);
    }

    public String searchForPerson(String sessionId, String phoneNumber) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("session_id", sessionId);
        params.put("phone_number", phoneNumber);

        HttpsURLConnection connection = buildPostRequest(searchURL, params);
        return buildResponse(connection);
    }

    public String addFriend(String sessionId, String phoneNumber) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("session_id", sessionId);
        params.put("phone_number", phoneNumber);

        HttpsURLConnection connection = buildPostRequest(addFriendURL, params);
        return buildResponse(connection);
    }

    public String removeFriend(String sessionId, String phoneNumber) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("session_id", sessionId);
        params.put("phone_number", phoneNumber);

        HttpsURLConnection connection = buildPostRequest(removeFriendURL, params);
        return buildResponse(connection);
    }

    public String checkContacts(String sessionId, String payload) throws IOException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("session_id", sessionId);
        params.put("contacts", payload);

        HttpsURLConnection connection = buildPostRequest(checkContactsURL, params);
        return buildResponse(connection);
    }

}

