
package com.alee.paradow.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryRes {

    @SerializedName("message")
    private List<Message> mMessage;

    public List<Message> getMessage() {
        return mMessage;
    }

    public void setMessage(List<Message> message) {
        mMessage = message;
    }


    public static class Message implements Parcelable {

        @SerializedName("data")
        private List<Datum> mData;
        @SerializedName("name")
        private String mName;
        @SerializedName("image")
        private String mImage;

        protected Message(Parcel in) {
            mName = in.readString();
            mImage = in.readString();
        }

        public static final Creator<Message> CREATOR = new Creator<Message>() {
            @Override
            public Message createFromParcel(Parcel in) {
                return new Message(in);
            }

            @Override
            public Message[] newArray(int size) {
                return new Message[size];
            }
        };

        public String getImage() {
            return mImage;
        }

        public void setImage(String mImage) {
            this.mImage = mImage;
        }

        public List<Datum> getData() {
            return mData;
        }

        public void setData(List<Datum> data) {
            mData = data;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mName);
            dest.writeString(mImage);
        }
    }

}
