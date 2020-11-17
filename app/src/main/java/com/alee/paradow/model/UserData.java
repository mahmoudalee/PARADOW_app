
package com.alee.paradow.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UserData implements Parcelable {

    @SerializedName("city")
    private Object mCity;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("creditNo")
    private Object mCreditNo;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("favProduct")
    private String mFavProduct;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("phone")
    private Object mPhone;
    @SerializedName("photo")
    private String mPhoto;
    @SerializedName("repassword")
    private String mRepassword;
    @SerializedName("role")
    private String mRole;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("visitor")
    private String mVisitor;

    public UserData() {
    }

    public UserData(Parcel in) {
        mCreatedAt = in.readString();
        mEmail = in.readString();
        mFavProduct = in.readString();
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readLong();
        }
        mName = in.readString();
        mPhoto = in.readString();
        mRepassword = in.readString();
        mRole = in.readString();
        mUpdatedAt = in.readString();
        mVisitor = in.readString();
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    public Object getCity() {
        return mCity;
    }

    public void setCity(Object city) {
        mCity = city;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public Object getCreditNo() {
        return mCreditNo;
    }

    public void setCreditNo(Object creditNo) {
        mCreditNo = creditNo;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFavProduct() {
        return mFavProduct;
    }

    public void setFavProduct(String favProduct) {
        mFavProduct = favProduct;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Object getPhone() {
        return mPhone;
    }

    public void setPhone(Object phone) {
        mPhone = phone;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public String getRepassword() {
        return mRepassword;
    }

    public void setRepassword(String repassword) {
        mRepassword = repassword;
    }

    public String getRole() {
        return mRole;
    }

    public void setRole(String role) {
        mRole = role;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String getVisitor() {
        return mVisitor;
    }

    public void setVisitor(String visitor) {
        mVisitor = visitor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCreatedAt);
        dest.writeString(mEmail);
        dest.writeString(mFavProduct);
        if (mId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mId);
        }
        dest.writeString(mName);
        dest.writeString(mPhoto);
        dest.writeString(mRepassword);
        dest.writeString(mRole);
        dest.writeString(mUpdatedAt);
        dest.writeString(mVisitor);
    }
}
