package com.scriptively.app.Database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.scriptively.app.DatabaseModel.ScriptDB;

import java.util.List;

@Dao
public interface ScriptDBDao {

    @Query("SELECT * FROM SCRIPTDATA order by Scripttitle")
    List<ScriptDB> loadAddData();

    @Insert
    long insertScript(ScriptDB script);

//    @Update
//    void updateScript(ScriptDB scriptDB);

    @Query("DELETE FROM scriptdata Where scriptDBkey Like :updateKey")
    int delete(int updateKey);

    @Query("SELECT * FROM SCRIPTDATA WHERE internetFlag Like 0")
    List<ScriptDB> internetBased();

    @Query("SELECT * FROM SCRIPTDATA WHERE editFlag Like 0")
    List<ScriptDB> editBased();

    @Query("UPDATE SCRIPTDATA SET internetFlag = :value , scriptId = :scriptId where scriptDBkey Like :updateKey")
    int update(int value,int updateKey,String scriptId);

    @Query("UPDATE SCRIPTDATA SET Scripttitle = :value, editFlag = :editValue where scriptDBkey Like :updateKey")
    int updateTitle(String value, int editValue,int updateKey);

    @Query("UPDATE SCRIPTDATA SET Scripttitle = :value, editFlag = :value_desc ,scriptDescription = :scriptDescription," +
            " scriptAlignment = :scriptAlignment, scriptColor = :scriptColor, scriptTextSize = :scriptTextSize,promptTextSize = :promptTextSize where scriptDBkey Like :updateKey")
    int updateScript(String value, int value_desc ,String scriptDescription, String scriptAlignment,
                     String scriptColor, String scriptTextSize,String promptTextSize, int updateKey);

    @Query("SELECT * FROM SCRIPTDATA ORDER BY scriptDBkey DESC  LIMIT 1")
    ScriptDB lastPrimaryKey();


//    @Query("SELECT * FROM PERSON WHERE id = :id")
//    Person loadPersonById(int id);

}
