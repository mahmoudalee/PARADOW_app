
package com.alee.paradow.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavRes {

    @SerializedName("message")
    private Message mMessage;

    public Message getMessage() {
        return mMessage;
    }

    public void setMessage(Message message) {
        mMessage = message;
    }

    public class Message {
        @SerializedName("User Data")
        private String mUserData;
        @SerializedName("User Image")
        private String mUserImage;
        @SerializedName("User Favourite")
        private String mUserFavourite;
        @SerializedName("User Fav")
        private List<UserFav> mUserFav;

        public List<UserFav> getUserFav() {
            return mUserFav;
        }

        public void setUserFav(List<UserFav> userFav) {
            mUserFav = userFav;
        }

        public String getUserData() {
            return mUserData;
        }

        public void setUserData(String mUserData) {
            this.mUserData = mUserData;
        }

        public String getUserImage() {
            return mUserImage;
        }

        public void setUserImage(String mUserImage) {
            this.mUserImage = mUserImage;
        }

        public String getUserFavourite() {
            return mUserFavourite;
        }

        public void setUserFavourite(String mUserFavourite) {
            this.mUserFavourite = mUserFavourite;
        }

    }


}
