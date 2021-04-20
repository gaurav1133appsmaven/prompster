package com.project.prompster_live.Api;


import com.project.prompster_live.Pojo.AddScript;
import com.project.prompster_live.Pojo.AddStoryBoard_pojo;
import com.project.prompster_live.Pojo.Add_Folder_pojo;
import com.project.prompster_live.Pojo.CheckSub;
import com.project.prompster_live.Pojo.Delete_Story_board_pojo;
import com.project.prompster_live.Pojo.EditStoryPojo;
import com.project.prompster_live.Pojo.EditScript_Pojo;
import com.project.prompster_live.Pojo.Forgotpassword_Pojo;
import com.project.prompster_live.Pojo.InAppProduct;
import com.project.prompster_live.Pojo.LoginPojo;
import com.project.prompster_live.Pojo.MovePojo;
import com.project.prompster_live.Pojo.Script;
import com.project.prompster_live.Pojo.ScriptUnderFolder;
import com.project.prompster_live.Pojo.ShowStoryBoard_pojo;
import com.project.prompster_live.Pojo.Show_Folder_pojo;
import com.project.prompster_live.Pojo.SignupPOJO;
import com.project.prompster_live.Pojo.SingleFolderDetailPojo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("user_signup")
    Call<SignupPOJO>signupdata(@Field("first_name") String First_name, @Field("last_name") String Last_name, @Field("email") String Email,
                                      @Field("password") String Password, @Field("type") String type, @Field("unique_id") String unique_id );


    @FormUrlEncoded
    @POST("user_login")
    Call<LoginPojo>loginData(@Field("email") String Email, @Field("password") String Password, @Field("type") String type, @Field("unique_id") String unique_id );

    @FormUrlEncoded
    @POST("forget_password")
    Call<Forgotpassword_Pojo>forgetpass(@Field("email") String Email);

    @POST("add_script")
    Call<AddScript>add_script(@Body RequestBody body);

    @FormUrlEncoded
    @POST("show_scripts")
    Call<Script>all_script(@Field("user_id") String userid,@Field("scrMirror") boolean scrMirror, @Field("scrInvert") boolean scrInvert);

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
    @POST("add_script_under_folder")
    Call<MovePojo>addScript_Folder(@Field("user_id") String userid,@Field("script_id") String script_id,@Field("folder_id") String folder_id);

    @FormUrlEncoded
    @POST("delete_script")
    Call<ResponseBody>DeleteScript(@Field("user_id") String userid, @Field("script_id") String script_id);

    @FormUrlEncoded
    @POST("delete_folder")
    Call<ResponseBody>DeleteFolder(@Field("user_id") String userid, @Field("folder_id") String folder_id);

    @FormUrlEncoded
    @POST("delete_relation")
    Call<ResponseBody>DeleteRelation(@Field("user_id") int userid, @Field("folder_id") int folder_id, @Field("relation_id") int relation_id);

    @FormUrlEncoded
    @POST("edit_folder")
    Call<ResponseBody>editFolder(@Field("user_id") String userid, @Field("folder_id") String folder_id, @Field("folder_name") String folder_name);

    @FormUrlEncoded
    @POST("show_single_folder_data")
    Call<SingleFolderDetailPojo>showSingleFolderData(@Field("user_id") String userid, @Field("folder_id") String folder_id);

//    @FormUrlEncoded
    @POST("edit_script")
    Call<EditScript_Pojo>edit_script(@Body RequestBody body);

//    http://3.22.110.46/promster/index.php/add_media
    @Multipart
    @POST("add_media")
    Call<ResponseBody> uploadVideo(@Part("user_id") RequestBody user_id,
                        @Part("script_id") RequestBody script_id,
                        @Part("recUrlStr") RequestBody recUrlStr,
                        @Part MultipartBody.Part file);

    @GET("definitions?limit=200&includeRelated=false&useCanonical=false&includeTags=false&api_key=iu3u43eaowjq42uwdd19e1eygpcp71s8pbhc3kf513mccwna2")
    Call<ResponseBody>dictionary();


    @GET("relatedWords?useCanonical=false&relationshipTypes=synonym&limitPerRelationshipType=10&api_key=iu3u43eaowjq42uwdd19e1eygpcp71s8pbhc3kf513mccwna2")
    Call<ResponseBody>synonyms();

    @POST("show-subscriptions")
    Call<InAppProduct>getProducts(@Body RequestBody body);

    @FormUrlEncoded
    @POST("add_orders")
    Call<InAppProduct>saveProduct(@Field("user_id") String userId,
                                  @Field("device_type")String device_type,
                                  @Field("product_id")String product_id);
    @FormUrlEncoded
    @POST("check_payment_status")
    Call<CheckSub>checkSub(@Field("user_id") String userId);
}

