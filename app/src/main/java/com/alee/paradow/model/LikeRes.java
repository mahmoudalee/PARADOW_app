
package com.alee.paradow.model;

import com.google.gson.annotations.SerializedName;

public class LikeRes {

    @SerializedName("message")
    private String mMessage;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

}
