package com.scriptively.app.Database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.scriptively.app.DatabaseModel.StoryBoardDB;

import java.util.List;

@Dao
public interface StoryBoardDao {

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    List<User> loadAllByIds(int[] userIds);


    @Query("SELECT * FROM storyboard WHERE scriptKey Like :scriptKey")
    List<StoryBoardDB> getAll(int scriptKey);

//    @Query("SELECT * FROM storyboard where ")
//    List<StoryBoardDB> loadAddStoryBoard();

    @Query("DELETE FROM storyboard Where storyKey Like :updateKey and scriptKey Like :scriptKey")
    int delete(int updateKey, int scriptKey);

    @Insert
    long insertStoryBoard(StoryBoardDB story);

    @Insert
    List<Long> insertAllStoryBoard(List<StoryBoardDB> story);


    @Query("SELECT * FROM storyboard WHERE storyBoardinternetFlag Like 0")
    List<StoryBoardDB> internetBased();

    @Query("SELECT * FROM storyboard WHERE storyBoardeditFlag Like 0")
    List<StoryBoardDB> editBased();

    @Query("UPDATE storyboard SET storyBoardinternetFlag = :value where storyKey Like :updateKey")
    int updateInternetFlag(int value,int updateKey);


    @Query("UPDATE storyboard SET storyboard_name = :value, Storyboard_att_text = :value_desc ,storyboardeditFlag = :editValue where storyKey Like :updateKey")
    int updateStoryBoard(String value, String value_desc ,int editValue,int updateKey);





}
