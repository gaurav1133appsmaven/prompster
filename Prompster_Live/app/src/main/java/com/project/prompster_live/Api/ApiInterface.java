package com.project.prompster_live.Api;


import com.project.prompster_live.Pojo.AddScript;
import com.project.prompster_live.Pojo.AddStoryBoard_pojo;
import com.project.prompster_live.Pojo.Add_Folder_pojo;
import com.project.prompster_live.Pojo.Delete_Story_board_pojo;
import com.project.prompster_live.Pojo.EditStoryPojo;
import com.project.prompster_live.Pojo.EditScript_Pojo;
import com.project.prompster_live.Pojo.Forgotpassword_Pojo;
import com.project.prompster_live.Pojo.LoginPojo;
import com.project.prompster_live.Pojo.Script;
import com.project.prompster_live.Pojo.ShowStoryBoard_pojo;
import com.project.prompster_live.Pojo.Show_Folder_pojo;
import com.project.prompster_live.Pojo.SignupPOJO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("user_signup")
    Call<SignupPOJO>signupdata(@Field("first_name") String First_name, @Field("last_name") String Last_name, @Field("email") String Email,
                                      @Field("password") String Password);


    @FormUrlEncoded
    @POST("user_login")
    Call<LoginPojo>loginData(@Field("email") String Email, @Field("password") String Password);

    @FormUrlEncoded
    @POST("forget_password")
    Call<Forgotpassword_Pojo>forgetpass(@Field("email") String Email);


    @FormUrlEncoded
    @POST("add_script")
    Call<AddScript>add_script(@Field("user_id") String id, @Field("scrTitle") String title, @Field("scrText") String text,
                              @Field("scrAttrText") String attr_text,@Field("scrEditTextSize") String size, @Field("scrPromptTextSize") String textsize, @Field("scrPromptSpeed") String prospedd, @Field("textMargin") String textmargin, @Field("marker") String markermarker, @Field("markerX") String markerx, @Field("uuid") String uuid);


    @FormUrlEncoded
    @POST("show_scripts")
    Call<Script>all_script(@Field("user_id") String userid);

    @FormUrlEncoded
    @POST("show_story_board")
    Call<ShowStoryBoard_pojo>showStoryBoard(@Field("user_id") String userid, @Field("script_id") String scrpt_id);

    @FormUrlEncoded
    @POST("add_story_board")
    Call<AddStoryBoard_pojo>addStoryBoard(@Field("user_id") String userid, @Field("script_id") String scrpt_id, @Field("storyboardName") String storyboardName, @Field("storyboardText") String storyboardText, @Field("storyboardAttrText") String storyboardAttrText);

    @FormUrlEncoded
    @POST("delete_story_board")
    Call<Delete_Story_board_pojo>deleteStoryBoard(@Field("user_id") String userid, @Field("script_id") String scrpt_id, @Field("story_board_id") String story_board_id);


    @FormUrlEncoded
    @POST("add_folders")
    Call<Add_Folder_pojo>add_folder(@Field("user_id") String userid, @Field("folder_name") String folder_name);

    @FormUrlEncoded
    @POST("show_folders")
    Call<Show_Folder_pojo>show_folder(@Field("user_id") String userid);

    @FormUrlEncoded
    @POST("edit_story_board")
    Call<EditStoryPojo>edit_story_board(@Field("user_id") String userid,@Field("script_id") String scrpt_id,@Field("story_board_id") String story_id,@Field("storyboardName") String story_name,@Field("storyboardText") String storytext,@Field("storyboardAttrText") String storyboardtext);


    @FormUrlEncoded
    @POST("edit_script")
    Call<EditScript_Pojo>edit_script(@Field("user_id") String userid, @Field("script_id") String scrpt_id, @Field("story_board_id") String story_id, @Field("scrTitle") String scrptTitle, @Field("scrText") String scrpttext, @Field("scrAttrText") String scrptAttrtext, @Field("scrEditTextSize") String scrTesxtsize, @Field("scrPromptTextSize") String scrpropsize, @Field("scrPromptSpeed") String scrPropspeed, @Field("textMargin") String textMargin, @Field("marker") String matrker, @Field("markerX") String markerx, @Field("uuid") String uuid);

}

