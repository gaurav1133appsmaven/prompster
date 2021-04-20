package com.project.prompster_live.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.project.prompster_live.DatabaseModel.FolderDB;
import com.project.prompster_live.DatabaseModel.ScriptDB;

import java.util.List;

@Dao
public interface FolderDao {

    @Query("SELECT * FROM folderdb order by name")
    List<FolderDB> loadAllFolders();

    @Insert
    long insertFolder(FolderDB folder);

    @Update
    void updateFolder(FolderDB folder);

    @Query("DELETE FROM FolderDB Where folder_key Like :updateKey")
    int delete(int updateKey);

    @Query("SELECT * FROM folderdb ORDER BY folder_key DESC  LIMIT 1")
    FolderDB lastPrimaryKey();

    @Query("SELECT * FROM folderdb WHERE editFlag Like 0")
    List<FolderDB> editFolderBased();

    @Query("SELECT * FROM folderdb WHERE internet_flag Like 0")
    List<FolderDB> internetBased();

    @Query("UPDATE folderdb SET internet_flag = :value, folder_Id = :folderId where folder_key Like :updateKey")
    int updateFolderInternetFlag(int value, int folderId,int updateKey);


    @Query("UPDATE folderdb SET name = :value, editFlag = :editValue where folder_key Like :updateKey")
    int updateFolderTitle(String value, int editValue,int updateKey);



}
