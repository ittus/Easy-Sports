package com.gec.easysports.model;

/**
 * Created by ittus on 12/1/15.
 */
public class DashboardModel {
    public static final String TAG_NAME = "name";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_LOCATION = "location";
    public static final String TAG_NUM_OF_PLAYER = "number_of_player";
    public static final String TAG_USER_EMAIL = "user_email";
    public static final String TAG_ADDRESS = "address";
    public static final String TAG_TIME = "time";
    public static final String TAG_IMAGE_URL = "image_url";
    public static final String TAG_CATEGORY = "category";
    public static final String TAG_DATE = "date";

    private long mId;
    private String mImageURL;
    private String mDescription;
    private String location;
    private String userEmail;
    private String address;
    private String time;
    private String name;
    private String date;
    private String numPlayer;
    private String category;

    public DashboardModel() {
    }


    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }




    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getmImageURL() {
        return mImageURL;
    }

    public void setmImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumPlayer() {
        return numPlayer;
    }

    public void setNumPlayer(String numPlayer) {
        this.numPlayer = numPlayer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return mDescription;
    }
}
