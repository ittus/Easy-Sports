package com.gec.easysports.model;

/**
 * Created by ittus on 12/1/15.
 */
public class DashboardModel {
    private long mId;
    private String mImageURL;
    private String mTitle;
    private String mDescription;

    public DashboardModel() {
    }

    public DashboardModel(long id, String imageURL, String title, String description) {
        mId = id;
        mImageURL = imageURL;
        mTitle = title;
        mDescription = description;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String imageURL) {
        mImageURL = imageURL;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    @Override
    public String toString() {
        return mDescription;
    }
}
