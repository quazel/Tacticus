package com.bramble.kickback.networking;

import com.bramble.kickback.models.Friend;
import com.bramble.kickback.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResponseDeserializer {

    public static void deserializePoll(String response) throws JSONException {
        User theUser = User.getUser();
        theUser.getFriends().clear();

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
            theUser.addFriend(friend);
        }
    }

}
