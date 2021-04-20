package com.project.prompster_live.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowStoryBoard_Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("script_id")
    @Expose
    private String scriptId;
    @SerializedName("storyboardName")
    @Expose
    private String storyboardName;
    @SerializedName("storyboardText")
    @Expose
    private String storyboardText;
    @SerializedName("storyboardAttrText")
    @Expose
    private String storyboardAttrText;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("modified_at")
    @Expose
    private Object modifiedAt;

    public int primarykey;
    public int primarykeyStory;

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

    public String getScriptId() {
        return scriptId;
    }

    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
    }

    public String getStoryboardName() {
        return storyboardName;
    }

    public void setStoryboardName(String storyboardName) {
        this.storyboardName = storyboardName;
    }

    public String getStoryboardText() {
        return storyboardText;
    }

    public void setStoryboardText(String storyboardText) {
        this.storyboardText = storyboardText;
    }

    public String getStoryboardAttrText() {
        return storyboardAttrText;
    }

    public void setStoryboardAttrText(String storyboardAttrText) {
        this.storyboardAttrText = storyboardAttrText;
    }

    public int getPrimarykey() {
        return primarykey;
    }

    public void setPrimarykey(int primarykey) {
        this.primarykey = primarykey;
    }

    public int getPrimarykeyStory() {
        return primarykeyStory;
    }

    public void setPrimarykeyStory(int primarykeyStory) {
        this.primarykeyStory = primarykeyStory;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Object modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

}
