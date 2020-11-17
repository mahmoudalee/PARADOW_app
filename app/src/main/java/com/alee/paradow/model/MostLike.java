
package com.alee.paradow.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MostLike {

    @SerializedName("message")
    private List<Message> mMessage;

    public List<Message> getMessage() {
        return mMessage;
    }

    public void setMessage(List<Message> message) {
        mMessage = message;
    }


    public class Message {

        @SerializedName("categoryName")
        private String mCategoryName;
        @SerializedName("created_at")
        private String mCreatedAt;
        @SerializedName("description")
        private String mDescription;
        @SerializedName("favProduct")
        private String mFavProduct;
        @SerializedName("height")
        private Object mHeight;
        @SerializedName("id")
        private Long mId;
        @SerializedName("image")
        private String mImage;
        @SerializedName("imageOnWall")
        private String mImageOnWall;
        @SerializedName("no_of_color")
        private String mNoOfColor;
        @SerializedName("offer_id")
        private String mOfferId;
        @SerializedName("price")
        private String mPrice;
        @SerializedName("priceAfterOff")
        private String mPriceAfterOff;
        @SerializedName("rate")
        private String mRate;
        @SerializedName("state")
        private String mState;
        @SerializedName("title")
        private String mTitle;
        @SerializedName("updated_at")
        private String mUpdatedAt;
        @SerializedName("user_id")
        private String mUserId;
        @SerializedName("views")
        private String mViews;
        @SerializedName("width")
        private Object mWidth;
        private boolean like;

        public boolean isLike() {
            return like;
        }

        public void setLike(boolean like) {
            this.like = like;
        }

        public String getCategoryName() {
            return mCategoryName;
        }

        public void setCategoryName(String categoryName) {
            mCategoryName = categoryName;
        }

        public String getCreatedAt() {
            return mCreatedAt;
        }

        public void setCreatedAt(String createdAt) {
            mCreatedAt = createdAt;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }

        public String getFavProduct() {
            return mFavProduct;
        }

        public void setFavProduct(String favProduct) {
            mFavProduct = favProduct;
        }

        public Object getHeight() {
            return mHeight;
        }

        public void setHeight(Object height) {
            mHeight = height;
        }

        public Long getId() {
            return mId;
        }

        public void setId(Long id) {
            mId = id;
        }

        public String getImage() {
            return mImage;
        }

        public void setImage(String image) {
            mImage = image;
        }

        public String getImageOnWall() {
            return mImageOnWall;
        }

        public void setImageOnWall(String imageOnWall) {
            mImageOnWall = imageOnWall;
        }

        public String getNoOfColor() {
            return mNoOfColor;
        }

        public void setNoOfColor(String noOfColor) {
            mNoOfColor = noOfColor;
        }

        public String getOfferId() {
            return mOfferId;
        }

        public void setOfferId(String offerId) {
            mOfferId = offerId;
        }

        public String getPrice() {
            return mPrice;
        }

        public void setPrice(String price) {
            mPrice = price;
        }

        public String getPriceAfterOff() {
            return mPriceAfterOff;
        }

        public void setPriceAfterOff(String priceAfterOff) {
            mPriceAfterOff = priceAfterOff;
        }

        public String getRate() {
            return mRate;
        }

        public void setRate(String rate) {
            mRate = rate;
        }

        public String getState() {
            return mState;
        }

        public void setState(String state) {
            mState = state;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }

        public String getUpdatedAt() {
            return mUpdatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            mUpdatedAt = updatedAt;
        }

        public String getUserId() {
            return mUserId;
        }

        public void setUserId(String userId) {
            mUserId = userId;
        }

        public String getViews() {
            return mViews;
        }

        public void setViews(String views) {
            mViews = views;
        }

        public Object getWidth() {
            return mWidth;
        }

        public void setWidth(Object width) {
            mWidth = width;
        }

    }

}
