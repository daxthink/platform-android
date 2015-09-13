package com.ushahidi.android.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Deployment Site Configuration Entity
 *
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class Config {
    
    @SerializedName("id")
    private String mId;
    
    @SerializedName("url")
    private String mUrl;
    
    @SerializedName("name")
    private String mName;
    
    @SerializedName("description")
    private String mDescription;
    
    @SerializedName("email")
    private String mEmail;
    
    @SerializedName("timezone")
    private String mTimeZone;
    
    @SerializedName("language")
    private String mLanguage;
    
    @SerializedName("date_format")
    private String mDateFormat;
    
    @SerializedName("client_url")
    private String mClientUrl;

    @SerializedName("allowed_privileges")
    private List<Privilege> mAllowedPrivileges;
    
    @SerializedName("image_header")
    private String mImageHeader;
    
    @SerializedName("owner_name")
    private String mOwnerName;
    
    @SerializedName("site_name")
    private String mSiteName;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public String getDateFormat() {
        return mDateFormat;
    }

    public void setDateFormat(String dateFormat) {
        mDateFormat = dateFormat;
    }

    public String getClientUrl() {
        return mClientUrl;
    }

    public void setClientUrl(String clientUrl) {
        mClientUrl = clientUrl;
    }

    public String getImageHeader() {
        return mImageHeader;
    }

    public void setImageHeader(String mImageHeader) {
        mImageHeader = mImageHeader;
    }

    public String getOwnerName() {
        return mOwnerName;
    }

    public void setOwnerName(String ownerName) {
        mOwnerName = ownerName;
    }

    public String getSiteName() {
        return mSiteName;
    }

    public void setSiteName(String siteName) {
        mSiteName = siteName;
    }

    public List<Privilege> getAllowedPrivileges() {
        return mAllowedPrivileges;
    }

    public void setAllowedPrivileges(List<Privilege> allowedPrivileges) {
        this.mAllowedPrivileges = allowedPrivileges;
    }
}