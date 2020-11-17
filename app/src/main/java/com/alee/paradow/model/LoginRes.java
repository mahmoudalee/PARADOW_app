
package com.alee.paradow.model;

import com.google.gson.annotations.SerializedName;


public class LoginRes {

    @SerializedName("message")
    private Message mMessage;

    public Message getMessage() {
        return mMessage;
    }

    public void setMessage(Message message) {
        mMessage = message;
    }


    public class Message {

        @SerializedName("remember_token")
        private String mRememberToken;
        @SerializedName("user data")
        private UserData mUserData;

        public String getRememberToken() {
            return mRememberToken;
        }

        public void setRememberToken(String rememberToken) {
            mRememberToken = rememberToken;
        }

        public UserData getUserData() {
            return mUserData;
        }

        public void setUserData(UserData userData) {
            mUserData = userData;
        }

    }
}