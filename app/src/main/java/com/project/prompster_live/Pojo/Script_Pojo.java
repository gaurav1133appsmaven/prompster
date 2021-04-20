package com.project.prompster_live.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Script_Pojo {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("scrTitle")
    @Expose
    public String scrTitle;
    @SerializedName("scrText")
    @Expose
    public String scrText;
    @SerializedName("scrAttrText")
    @Expose
    public String scrAttrText;
    @SerializedName("scrEditTextSize")
    @Expose
    public String scrEditTextSize;
    @SerializedName("scrPromptTextSize")
    @Expose
    public String scrPromptTextSize;
    @SerializedName("scrPromptSpeed")
    @Expose
    public String scrPromptSpeed;
    @SerializedName("textMargin")
    @Expose
    public String textMargin;
    @SerializedName("marker")
    @Expose
    public String marker;
    @SerializedName("markerX")
    @Expose
    public String markerX;
    @SerializedName("scrModifiedDate")
    @Expose
    public String scrModifiedDate;
    @SerializedName("scrUpdateDate")
    @Expose
    public String scrUpdateDate;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("uuid")
    @Expose
    public String uuid;
    @SerializedName("scrManualScrolling")
    @Expose
    public String scrManualScrolling;
    @SerializedName("scrCameraRecordMe")
    @Expose
    public String scrCameraRecordMe;
    @SerializedName("scrShowMeFullScreen")
    @Expose
    public String scrShowMeFullScreen;
    @SerializedName("scrShowMeThumbnail")
    @Expose
    public String scrShowMeThumbnail;
    @SerializedName("scrVoiceRecordMe")
    @Expose
    public String scrVoiceRecordMe;
    @SerializedName("scrColor")
    @Expose
    public String scrColor;
    @SerializedName("scrAlignment")
    @Expose
    public String scrAlignment;

    public int primarykey;

    @SerializedName("storyboard")
    @Expose
    private List<ShowStoryBoard_Datum> storyboard = null;

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

    public String getScrModifiedDate() {
        return scrModifiedDate;
    }

    public void setScrModifiedDate(String  scrModifiedDate) {
        this.scrModifiedDate = scrModifiedDate;
    }

    public String getScrUpdateDate() {
        return scrUpdateDate;
    }

    public void setScrUpdateDate(String scrUpdateDate) {
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

    public String getScrManualScrolling() {
        return scrManualScrolling;
    }

    public void setScrManualScrolling(String scrManualScrolling) {
        this.scrManualScrolling = scrManualScrolling;
    }

    public String getScrCameraRecordMe() {
        return scrCameraRecordMe;
    }

    public void setScrCameraRecordMe(String scrCameraRecordMe) {
        this.scrCameraRecordMe = scrCameraRecordMe;
    }

    public String getScrShowMeFullScreen() {
        return scrShowMeFullScreen;
    }

    public void setScrShowMeFullScreen(String scrShowMeFullScreen) {
        this.scrShowMeFullScreen = scrShowMeFullScreen;
    }

    public String getScrShowMeThumbnail() {
        return scrShowMeThumbnail;
    }

    public void setScrShowMeThumbnail(String scrShowMeThumbnail) {
        this.scrShowMeThumbnail = scrShowMeThumbnail;
    }

    public String getScrVoiceRecordMe() {
        return scrVoiceRecordMe;
    }

    public void setScrVoiceRecordMe(String scrVoiceRecordMe) {
        this.scrVoiceRecordMe = scrVoiceRecordMe;
    }

    public int getPrimarykey() {
        return primarykey;
    }

    public void setPrimarykey(int primarykey) {
        this.primarykey = primarykey;
    }

    public String getScrColor() {
        return scrColor;
    }

    public void setScrColor(String scrColor) {
        this.scrColor = scrColor;
    }

    public String getScrAlignment() {
        return scrAlignment;
    }

    public void setScrAlignment(String scrAlignment) {
        this.scrAlignment = scrAlignment;
    }

    public List<ShowStoryBoard_Datum> getStoryboard() {
        return storyboard;
    }

    public void setStoryboard(List<ShowStoryBoard_Datum> storyboard) {
        this.storyboard = storyboard;
    }

}
