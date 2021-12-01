package com.scriptively.app.DatabaseModel;



import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scriptdata")
public class ScriptDB {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "scriptDBkey")
    public int key;

    @ColumnInfo(name = "scriptId")
    public String scriptId;

    @ColumnInfo(name = "internetFlag")
    public int internetFlag;

    @ColumnInfo(name = "editFlag")
    public int editFlag;
//
//    @ColumnInfo(name = "scr_camera_recording")
//    public boolean scr_camera_recording;
//
//    @ColumnInfo(name = "manual_scrolling")
//    public boolean manual_scrolling;
//
//    @ColumnInfo(name = "show_me_full_screen")
//    public boolean show_me_full_screen;
//
//    @ColumnInfo(name = "show_me_thumnail")
//    public boolean show_me_thumnail;
//
//    @ColumnInfo(name = "voice_record_me")
//    public boolean voice_record_me;
//
    @ColumnInfo(name = "getScrPromptTextSize")
    public String getScrPromptTextSize;
//
//    @ColumnInfo(name = "markerX")
//    public float markerX;
//
//    @ColumnInfo(name = "text_margin")
//    public float text_margin;

    @ColumnInfo(name = "scr_txt")
    public String scr_txt;

    @ColumnInfo(name = "scriptDescription")
    public String scriptDescription;

    @ColumnInfo(name = "Scripttitle")
    public String Scripttitle;

    @ColumnInfo(name = "ScriptcreatedAt")
    public String ScriptcreatedAt;

//    @ColumnInfo(name = "modifiedDate")
//    public String modifiedDate;
//
//    @ColumnInfo(name = "updatedDate")
//    public String updatedDate;

    @ColumnInfo(name = "scriptEditTextSize")
    public String scriptEditTextSize;

    @ColumnInfo(name = "promptSpeed")
    public String promptSpeed;

    @ColumnInfo(name = "scriptAlignment")
    public String scriptAlignment;

    @ColumnInfo(name = "scriptColor")
    public String scriptColor;

    @ColumnInfo(name = "scriptuser_Id")
    public String scriptuser_Id;

    @ColumnInfo(name = "finalScrText")
    public String finalScrText;

    @ColumnInfo(name = "scriptTextSize")
    public String scriptTextSize;

    @ColumnInfo(name = "promptTextSize")
    public String promptTextSize;

//    @Embedded
//    StoryBoardDB storyBoardDB;

    public ScriptDB(){

    }

//    public ScriptDB(boolean creat_flag, boolean marker, boolean scr_camera_recording, boolean manual_scrolling, boolean show_me_full_screen, boolean show_me_thumnail, boolean voice_record_me, boolean update_flag, float markerX, float text_margin, String att_text, String text, String title, String created_at, String modified_date, String updated_date, int id, int edit_text_size, int prompt_speed, String prompt_text, String size, int user_id) {
    public ScriptDB(String text, String title, String created_at, String id, String edit_text_size, String alignment, String color, String user_id
            ,String scr_txt, int internet_flag, int editFlag, String getScrPromptTextSize,String finalScrText, String scriptTextSize,String promptTextSize,String prompt_speed) {
        this.internetFlag = internet_flag;
//        this.marker = marker;
//        this.scr_camera_recording = scr_camera_recording;
//        this.manual_scrolling = manual_scrolling;
//        this.show_me_full_screen = show_me_full_screen;
//        this.show_me_thumnail = show_me_thumnail;
//        this.voice_record_me = voice_record_me;
//        this.Scriptupdateflag = update_flag;
//        this.markerX = markerX;
        this.getScrPromptTextSize = getScrPromptTextSize;
        this.scr_txt = scr_txt;
        this.scriptDescription = text;
        this.Scripttitle = title;
        this.ScriptcreatedAt = created_at;
        this.editFlag = editFlag;
//        this.updatedDate = updated_date;
        this.scriptId = id;
        this.scriptEditTextSize = edit_text_size;
        this.promptSpeed = prompt_speed;
        this.scriptAlignment = alignment;
        this.scriptColor = color;
        this.scriptuser_Id = user_id;
        this.finalScrText = finalScrText;
        this.scriptTextSize = scriptTextSize;
        this.promptTextSize=promptTextSize;
    }

    public int getInternetFlag() {
        return internetFlag;
    }

    public void setInternetFlag(int internetFlag) {
        this.internetFlag = internetFlag;
    }

//    public boolean isMarker() {
//        return marker;
//    }
//
//    public void setMarker(boolean marker) {
//        this.marker = marker;
//    }
//
//    public boolean isScr_camera_recording() {
//        return scr_camera_recording;
//    }
//
//    public void setScr_camera_recording(boolean scr_camera_recording) {
//        this.scr_camera_recording = scr_camera_recording;
//    }
//
//    public boolean isManual_scrolling() {
//        return manual_scrolling;
//    }
//
//    public void setManual_scrolling(boolean manual_scrolling) {
//        this.manual_scrolling = manual_scrolling;
//    }
//
//    public boolean isShow_me_full_screen() {
//        return show_me_full_screen;
//    }
//
//    public void setShow_me_full_screen(boolean show_me_full_screen) {
//        this.show_me_full_screen = show_me_full_screen;
//    }
//
//    public boolean isShow_me_thumnail() {
//        return show_me_thumnail;
//    }
//
//    public void setShow_me_thumnail(boolean show_me_thumnail) {
//        this.show_me_thumnail = show_me_thumnail;
//    }
//
//    public boolean isVoice_record_me() {
//        return voice_record_me;
//    }
//
//    public void setVoice_record_me(boolean voice_record_me) {
//        this.voice_record_me = voice_record_me;
//    }
//
//    public boolean isUpdateflag() {
//        return Scriptupdateflag;
//    }
//
//    public void setUpdateflag(boolean updateflag) {
//        this.Scriptupdateflag = updateflag;
//    }
//
//    public float getMarkerX() {
//        return markerX;
//    }
//
//    public void setMarkerX(float markerX) {
//        this.markerX = markerX;
//    }
//
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getScr_txt() {
        return scr_txt;
    }

    public void setScr_txt(String scr_txt) {
        this.scr_txt = scr_txt;
    }

    public String getScriptDescription() {
        return scriptDescription;
    }

    public void setScriptDescription(String text) {
        this.scriptDescription = text;
    }

    public String getScripttitle() {
        return Scripttitle;
    }

    public void setScripttitle(String title) {
        this.Scripttitle = title;
    }

    public String getScriptcreatedAt() {
        return ScriptcreatedAt;
    }

    public void setScriptcreatedAt(String createdAt) {
        this.ScriptcreatedAt = createdAt;
    }

    public String getScriptTextSize() {
        return scriptTextSize;
    }

    public void setScriptTextSize(String scriptTextSize) {
        this.scriptTextSize = scriptTextSize;
    }

    public String getPrmoptTextSize() {
        return promptTextSize;
    }

    public void setPromptTextSize(String promptTextSize) {
        this.promptTextSize = promptTextSize;
    }

    //    public String getModifiedDate() {
//        return modifiedDate;
//    }
//
//    public void setModifiedDate(String modifiedDate) {
//        this.modifiedDate = modifiedDate;
//    }
//
    public String getGetScrPromptTextSize() {
        return getScrPromptTextSize;
    }

    public void setGetScrPromptTextSize(String getScrPromptTextSize) {
        this.getScrPromptTextSize = getScrPromptTextSize;
    }

    public String getScriptEditTextSize() {
        return scriptEditTextSize;
    }

    public void setScriptEditTextSize(String editTextSize) {
        this.scriptEditTextSize = editTextSize;
    }

    public int getEditFlag() {
        return editFlag;
    }

    public void setScriptId(int editFlag) {
        this.editFlag = editFlag;
    }

    public String getScriptAlignment() {
        return scriptAlignment;
    }

    public void setScriptAlignment(String promptText) {
        this.scriptAlignment = promptText;
    }

    public String getScriptuser_Id() {
        return scriptuser_Id;
    }

    public void setScriptuser_Id(String userId) {
        this.scriptuser_Id = userId;
    }

    public String get_id() {
        return scriptId;
    }

    public void set_id(String _id) {
        this.scriptId = _id;
    }

    public String getScriptColor() {
        return scriptColor;
    }

    public void setScriptColor(String size) {
        this.scriptColor = size;
    }

    public String getFinalScrText() {
        return finalScrText;
    }

    public void setFinalScrText(String finalScrText) {
        this.finalScrText = finalScrText;
    }


    public String getPromptSpeed() {
        return promptSpeed;
    }

    public void setPromptSpeed(String promptSpeed) {
        this.promptSpeed = promptSpeed;
    }

//    public StoryBoardDB getStoryBoardDB() {
//        return storyBoardDB;
//    }
//
//    public void setStoryBoardDB(StoryBoardDB storyBoardDB) {
//        this.storyBoardDB = storyBoardDB;
//    }
}
