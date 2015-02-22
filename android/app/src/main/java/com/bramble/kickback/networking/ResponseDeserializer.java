package com.bramble.kickback.networking;

import android.util.Log;

import com.bramble.kickback.models.Friend;
import com.bramble.kickback.models.RemoteUser;
import com.bramble.kickback.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResponseDeserializer {

    public static void deserializeLogin(String response) throws JSONException {
        JSONObject json = new JSONObject(response);
        User user = User.getUser();
        String name = json.getString("name");
        String nickname = json.getString("nickname");
        String email = json.getString("email");
        String phoneNumber = json.getString("phone_number");
        String sessionId = json.getString("session_id");

        JSONArray friendsJSON = json.getJSONArray("friends");
        ArrayList<Friend> friends = new ArrayList<Friend>();
        for (int i = 0; i < friendsJSON.length(); i++) {
            JSONObject friendJSON = friendsJSON.getJSONObject(i);
            String friendEmail = friendJSON.getString("email");
            String friendNickname = friendJSON.getString("nickname");
            String friendName = friendJSON.getString("name");
            String friendPhoneNumber = friendJSON.getString("phone_number");

            Friend friend = new Friend(friendNickname, friendName, friendPhoneNumber);
            friend.setOnline(false);
            friends.add(friend);
        }

        user.setName(name);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setSessionId(sessionId);
        user.setTemp(false);
        user.getFriends().clear();
        user.getFriends().addAll(friends);
    }

    public static void deserializePoll(String response) throws JSONException {
        User theUser = User.getUser();
        theUser.getFriends().clear();

        ArrayList<Friend> friends = new ArrayList<Friend>();
        ArrayList<Friend> onlineFriends = new ArrayList<Friend>();
        JSONObject jsonObject = new JSONObject(response);
        String sessionID = jsonObject.getString("session_id");
        int callMe = jsonObject.getInt("call_me");
        JSONArray friendsJSON = jsonObject.getJSONArray("friends");

        for (int i = 0; i < friendsJSON.length(); i++) {
            JSONObject friendJSON = friendsJSON.getJSONObject(i);
            String email = friendJSON.getString("email");
            String nickname = friendJSON.getString("nickname");
            String name = friendJSON.getString("name");
            String phoneNumber = friendJSON.getString("phone_number");
            boolean online = friendJSON.getBoolean("online");

            Friend friend = new Friend(nickname, name, phoneNumber);
            friend.setOnline(online);
            friends.add(friend);
            if (online) {
                onlineFriends.add(friend);
            }
        }

        theUser.setSessionId(sessionID);
        theUser.setCallMe(callMe);
        ArrayList<Friend> userFriends = theUser.getFriends();
        for (Friend friend : userFriends) {
            userFriends.remove(friend);
        }
        for (Friend friend : friends) {
            userFriends.add(friend);
        }
        ArrayList<Friend> userOnlineFriends = theUser.getOnlineFriends();
        for (Friend friend : userOnlineFriends) {
            userFriends.remove(friend);
        }
        for (Friend friend : onlineFriends) {
            userOnlineFriends.add(friend);
        }
        Log.d("Burgle1", userFriends.toString());
        Log.d("Burgle1", friends.toString());
    }

    public static RemoteUser deserializeSearchResults(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);

        String nickname = jsonObject.getString("nickname");
        String name = jsonObject.getString("name");
        String email = jsonObject.getString("email");
        String phoneNumber = jsonObject.getString("phone_number");
        String sex = jsonObject.getString("sex");
        boolean isFriend = jsonObject.getBoolean("friends_with");

        RemoteUser remoteUser = new RemoteUser(nickname, name, email, phoneNumber, sex, isFriend);
        remoteUser.verifyIsContact();
        return remoteUser;
    }

}
