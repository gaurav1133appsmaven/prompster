package com.scriptively.app.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.scriptively.app.DatabaseModel.FolderDB;
import com.scriptively.app.DatabaseModel.FolderDataDb;

import java.util.List;

@Dao
public interface FolderDataDao {

    @Query("SELECT * FROM folder_data")
    List<FolderDataDb> loadAllFolderData();

    @Insert
    void insertFolder(FolderDB folder);

    @Update
    void updateFolder(FolderDB folder);

    @Delete
    void delete(FolderDB folder);

}
