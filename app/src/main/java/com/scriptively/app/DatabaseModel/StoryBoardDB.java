package com.scriptively.app.DatabaseModel;



import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "storyboard")
public class StoryBoardDB {

//    @ColumnInfo(name = "creat_flag")
//    public boolean creat_flag;
//
//    @ColumnInfo(name = "update_flag")
//    public boolean update_flag;
//
//    @ColumnInfo(name = "created_at")
//    public String created_at;
//
//    @ColumnInfo(name = "Stroryboardmodified_at")
//    public String Stroryboardmodified_at;
//

    @ColumnInfo(name = "script_id")
    public String script_id;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "storyKey")
    public int storyKey;

    @ColumnInfo(name = "scriptKey")
    public int scriptKey;

    @ColumnInfo(name = "StoryboardId")
    public String StoryboardId;

    @ColumnInfo(name = "storyBoardinternetFlag")
    public int storyBoardinternetFlag;

    @ColumnInfo(name = "storyBoardeditFlag")
    public int storyBoardeditFlag;

    @ColumnInfo(name = "Storyboard_att_text")
    public String Storyboard_att_text;

    @ColumnInfo(name = "storyboard_name")
    public String storyboard_name;

    @ColumnInfo(name = "storyboardTextSize")
    public String storyboardTextSize;

    public StoryBoardDB(){}

//    public StoryBoardDB(boolean creat_flag, boolean update_flag, String created_at, String modified_at, String script_id, String id, String user_id, String storyboard_att_text, String storyboard_name, String storyboard_text) {
    public StoryBoardDB( String script_id, String id,String storyboard_att_text, String storyboard_name,String storyboardTextSize,
                         int storyBoardeditFlag, int  storyBoardinternetFlag,int scriptKey) {
//        this.creat_flag = creat_flag;
        this.scriptKey = scriptKey;
        this.storyBoardinternetFlag = storyBoardinternetFlag;
        this.script_id = script_id;
        this.StoryboardId = id;
        Storyboard_att_text = storyboard_att_text;
        this.storyboard_name = storyboard_name;
        this.storyboardTextSize = storyboardTextSize;
    }

//    public boolean isCreat_flag() {
//        return creat_flag;
//    }
//
//    public void setCreat_flag(boolean creat_flag) {
//        this.creat_flag = creat_flag;
//    }
//
//    public boolean isUpdate_flag() {
//        return update_flag;
//    }
//
//    public void setUpdate_flag(boolean update_flag) {
//        this.update_flag = update_flag;
//    }
//
    public int getScriptKey() {
        return scriptKey;
    }

    public void setScriptKey(int scriptKey) {
        this.scriptKey = scriptKey;
    }

    public int getStoryKey() {
        return storyKey;
    }

    public void setStoryKey(int storyKey) {
        this.storyKey = storyKey;
    }

    public String getScript_id() {
        return script_id;
    }

    public void setScript_id(String script_id) {
        this.script_id = script_id;
    }

    public String getStoryboardId() {
        return StoryboardId;
    }

    public void setStoryboardId(String id) {
        this.StoryboardId = id;
    }

    public int getStoryBoardinternetFlag() {
        return storyBoardinternetFlag;
    }

    public void setStoryBoardeditFlag(int storyBoardeditFlag) {
        this.storyBoardeditFlag = storyBoardeditFlag;
    }

    public int getStoryBoardeditFlag() {
        return storyBoardeditFlag;
    }

    public void setStoryBoardinternetFlag(int storyBoardinternetFlag) {
        this.storyBoardinternetFlag = storyBoardinternetFlag;
    }

    public String getStoryboard_att_text() {
        return Storyboard_att_text;
    }

    public void setStoryboard_att_text(String storyboard_att_text) {
        Storyboard_att_text = storyboard_att_text;
    }

    public String getStoryboard_name() {
        return storyboard_name;
    }

    public void setStoryboard_name(String storyboard_name) {
        this.storyboard_name = storyboard_name;
    }

    public String getStoryboardTextSize() {
        return storyboardTextSize;
    }

    public void setStoryboardTextSize(String storyboardTextSize) {
        this.storyboardTextSize = storyboardTextSize;
    }
}
