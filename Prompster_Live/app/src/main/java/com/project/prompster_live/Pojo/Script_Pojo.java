package com.project.prompster_live.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Script_Pojo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("scrTitle")
    @Expose
    private String scrTitle;
    @SerializedName("scrText")
    @Expose
    private String scrText;
    @SerializedName("scrAttrText")
    @Expose
    private String scrAttrText;
    @SerializedName("scrEditTextSize")
    @Expose
    private String scrEditTextSize;
    @SerializedName("scrPromptTextSize")
    @Expose
    private String scrPromptTextSize;
    @SerializedName("scrPromptSpeed")
    @Expose
    private String scrPromptSpeed;
    @SerializedName("textMargin")
    @Expose
    private String textMargin;
    @SerializedName("marker")
    @Expose
    private String marker;
    @SerializedName("markerX")
    @Expose
    private String markerX;
    @SerializedName("scrModifiedDate")
    @Expose
    private Object scrModifiedDate;
    @SerializedName("scrUpdateDate")
    @Expose
    private Object scrUpdateDate;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("storyboard")
    @Expose
    private List<Storyboard> storyboard = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getScrTitle() {
        return scrTitle;
    }

    public void setScrTitle(String scrTitle) {
        this.scrTitle = scrTitle;
    }

    public String getScrText() {
        return scrText;
    }

    public void setScrText(String scrText) {
        this.scrText = scrText;
    }

    public String getScrAttrText() {
        return scrAttrText;
    }

    public void setScrAttrText(String scrAttrText) {
        this.scrAttrText = scrAttrText;
    }

    public String getScrEditTextSize() {
        return scrEditTextSize;
    }

    public void setScrEditTextSize(String scrEditTextSize) {
        this.scrEditTextSize = scrEditTextSize;
    }

    public String getScrPromptTextSize() {
        return scrPromptTextSize;
    }

    public void setScrPromptTextSize(String scrPromptTextSize) {
        this.scrPromptTextSize = scrPromptTextSize;
    }

    public String getScrPromptSpeed() {
        return scrPromptSpeed;
    }

    public void setScrPromptSpeed(String scrPromptSpeed) {
        this.scrPromptSpeed = scrPromptSpeed;
    }

    public String getTextMargin() {
        return textMargin;
    }

    public void setTextMargin(String textMargin) {
        this.textMargin = textMargin;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getMarkerX() {
        return markerX;
    }

    public void setMarkerX(String markerX) {
        this.markerX = markerX;
    }

    public Object getScrModifiedDate() {
        return scrModifiedDate;
    }

    public void setScrModifiedDate(Object scrModifiedDate) {
        this.scrModifiedDate = scrModifiedDate;
    }

    public Object getScrUpdateDate() {
        return scrUpdateDate;
    }

    public void setScrUpdateDate(Object scrUpdateDate) {
        this.scrUpdateDate = scrUpdateDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Storyboard> getStoryboard() {
        return storyboard;
    }

    public void setStoryboard(List<Storyboard> storyboard) {
        this.storyboard = storyboard;
    }

}
