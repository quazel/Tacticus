package com.bramble.kickback.networking;

import com.bramble.kickback.models.Friend;
import com.bramble.kickback.models.RemoteUser;
import com.bramble.kickback.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResponseDeserializer {

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
        theUser.getFriends().clear();
        theUser.getFriends().addAll(friends);
        theUser.getOnlineFriends().clear();
        theUser.getOnlineFriends().addAll(onlineFriends);
    }

    public static RemoteUser deserializeSearchResults(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);

        String nickname = jsonObject.getString("nickname");
        String name = jsonObject.getString("name");
        String email = jsonObject.getString("email");
        String phoneNumber = jsonObject.getString("phone_number");
        String sex = jsonObject.getString("sex");
        boolean isFriend = jsonObject.getBoolean("friends_with");

        return new RemoteUser(nickname, name, email, phoneNumber, sex, isFriend);
    }

}
