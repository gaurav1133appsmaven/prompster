package com.scriptively.app.DatabaseModel;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "folder_data")
public class FolderDataDb {

    @ColumnInfo(name = "createFlag")
    public boolean createFlag;

    @PrimaryKey
    @ColumnInfo(name = "index")
    public int index;

    @ColumnInfo(name = "folderName")
    public String folderName;


    public FolderDataDb(){

    }

    public FolderDataDb(boolean create_flag, int index, String folder_name) {
        this.createFlag = create_flag;
        this.index = index;
        this.folderName = folder_name;

    }

    public boolean isCreate_flag() {
        return createFlag;
    }

    public void setCreate_flag(boolean create_flag) {
        this.createFlag = create_flag;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFolder_name() {
        return folderName;
    }

    public void setFolder_name(String folder_name) {
        this.folderName = folder_name;
    }

}
