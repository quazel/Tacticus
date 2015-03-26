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

    public static void deserializeStartKickback(String response) throws JSONException {
        User theUser = User.getUser();
        theUser.getOnlineFriends().clear();
        JSONObject jsonObject = new JSONObject(response);
        String sessionID = jsonObject.getString("session_id");
        int callMe = jsonObject.getInt("call_me");
        JSONArray friendsJSON = jsonObject.getJSONArray("friends");

        for (int i = 0; i < friendsJSON.length(); i++) {
            JSONObject friendJSON = friendsJSON.getJSONObject(i);
            String phoneNumber = friendJSON.getString("phone_number");
            boolean online = friendJSON.getBoolean("online");
            Friend friend = theUser.getFriend(phoneNumber);
            if (friend != null) {
                friend.setOnline(online);
                if (friend.isOnline()) {
                    theUser.getOnlineFriends().add(friend);
                }
            }
            else {
                Log.d("Kickback", "MISSING FRIEND!!!!!");
            }
        }

        theUser.setSessionId(sessionID);
        theUser.setCallMe(callMe);
    }

    public static void deserializePoll(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);
        User theUser = User.getUser();
        String session_id = jsonObject.getString("session_id");
        int call_me = jsonObject.getInt("call_me");
        theUser.setSessionId(session_id);
        theUser.setCallMe(call_me);
        JSONArray notifications = jsonObject.getJSONArray("notifications");
        for (int i = 0; i < notifications.length(); i++) {
            JSONObject notification = notifications.getJSONObject(i);
            int action = notification.getInt("action");
            JSONObject instigator = notification.getJSONObject("instigator");
            String name = instigator.getString("name");
            String nickname = instigator.getString("nickname");
            String phoneNumber = instigator.getString("phone_number");
            String online = instigator.getString("online");

            switch (action) {
                case 0:
                    Friend friend = User.getUser().getFriend(phoneNumber);
                    friend.setOnline(true);
                    User.getUser().getOnlineFriends().add(friend);
                    break;
                case 1:
                    Friend friend1 = User.getUser().getFriend(phoneNumber);
                    User.getUser().getOnlineFriends().remove(friend1);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    Log.d("Kickback", "Unknown notification encountered.");
            }
        }
    }

    public static RemoteUser deserializeSearchResults(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(result);

        String nickname = jsonObject.getString("nickname");
        String name = jsonObject.getString("name");
        String email = jsonObject.getString("email");
        String phoneNumber = jsonObject.getString("phone_number");
        String sex = jsonObject.getString("sex");
        boolean isFriend = jsonObject.getBoolean("friends_with");

        RemoteUser remoteUser = new RemoteUser(nickname, name, email, phoneNumber, sex, isFriend, false);
        return remoteUser;
    }

}
