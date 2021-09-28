package com.scriptively.app.DatabaseModel;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "folderdb")
public class FolderDB {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "folder_key")
    public int folder_key;


    @ColumnInfo(name = "folder_Id")
    public String folder_Id;

    @ColumnInfo(name = "internet_flag")
    public int internet_flag;

    @ColumnInfo(name = "editFlag")
    public int editFlag;


    @ColumnInfo(name = "userId")
    public String userId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "createdAt")
    public String foldercreatedAt;

    @ColumnInfo(name = "modified_at")
    public String modified_at;

    public FolderDB(){

    }

//    public FolderDB(boolean created_flag, boolean update_flag, String folder_id, String user_id, String name, String created_at, String modified_at) {
    public FolderDB(String folder_id, String user_id, String name, String created_at,int internet_flag,int editFlag) {
        this.folder_Id = folder_id;
        this.userId = user_id;
        this.name = name;
        this.foldercreatedAt = created_at;
        this.internet_flag = internet_flag;
        this.editFlag = editFlag;
    }

    public int getInternet_flag() {
        return internet_flag;
    }

    public void setInternet_flag(int internet_flag) {
        this.internet_flag = internet_flag;
    }

    public int getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(int editFlag) {
        this.editFlag = editFlag;
    }

    public String  getFolder_Id() {
        return folder_Id;
    }

    public void setFolder_Id(String id) {
        this.folder_Id = id;
    }

    public int getFolder_key() {
        return folder_key;
    }

    public void setFolder_key(int folder_key) {
        this.folder_key = folder_key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String user_id) {
        this.userId = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoldercreatedAt() {
        return foldercreatedAt;
    }

    public void setFoldercreatedAt(String created_at) {
        this.foldercreatedAt = created_at;
    }

//    public String getModified_at() {
//        return modified_at;
//    }
//
//    public void setModified_at(String modified_at) {
//        this.modified_at = modified_at;
//    }
}
