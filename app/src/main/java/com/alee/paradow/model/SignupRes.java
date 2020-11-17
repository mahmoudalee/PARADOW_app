
package com.alee.paradow.model;

import com.google.gson.annotations.SerializedName;

public class SignupRes {

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

        public String getRememberToken() {
            return mRememberToken;
        }

        public void setRememberToken(String rememberToken) {
            mRememberToken = rememberToken;
        }

    }
}
