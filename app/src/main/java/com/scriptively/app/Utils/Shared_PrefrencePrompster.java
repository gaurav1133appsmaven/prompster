package com.scriptively.app.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Shared_PrefrencePrompster {

    String PREF_NAME = "Promp";

    String Email = "Email";

    String Userid = "userid";

    String Username = "Name";
    String Password = "Password";

    String FirstName = "FirstName";
    String LastName = "LastName";
    String fbid = "FbId";
    String fbemail = "FbEmail";
    String tag = "TAGG";

String color = "Color";
String align="alignment";
String savecolor="SaveColor";
String scriptid="Script_id";


    private SharedPreferences pref = null;
    private static Shared_PrefrencePrompster preferences = null;

    static Context mContext = null;

    public static Shared_PrefrencePrompster getInstance(Context context) {

        if (preferences == null) {

            preferences = new Shared_PrefrencePrompster(context);
        }
        mContext = context;
        return preferences;
    }

    public void clearPreference() {
        pref.edit().clear().apply();
    }

    public Shared_PrefrencePrompster(Context context) {
        mContext = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public String getPREF_NAME() {
        return PREF_NAME;
    }

    public void setPREF_NAME(String PREF_NAME) {
        this.PREF_NAME = PREF_NAME;
    }

    public String getEmail() {
        return pref.getString(Email,"");
    }

    public void setEmail(String email) {
        pref.edit().putString(Email,email).commit();

    }

    public String getUserid() {
        return pref.getString(Userid,"");
    }

    public void setUserid(String userid) {
        pref.edit().putString(Userid,userid).commit();
    }

    public boolean checkSub(){
        return pref.getBoolean("sub",true);
    }

    public void setSub(boolean value)
    {
        pref.edit().putBoolean("sub",value);
    }

    public String getUsername() {
        return pref.getString(Username,"");
    }

    public void setUsername(String username) {
        pref.edit().putString(Username,username).commit();
    }

    public String getFirstName() {
        return pref.getString(FirstName,"");
    }

    public void setFirstName(String firstname) {
        pref.edit().putString(FirstName,firstname).commit();
    }

    public String getLastName() {
        return pref.getString(LastName,"");
    }

    public void setLastName(String lastname) {
        pref.edit().putString(LastName,lastname).commit();
    }

    public String getPassword() {
        return pref.getString(Password,"");
    }

    public void setPassword(String password) {
        pref.edit().putString(Password,password).commit();
    }

    public String getFbid() {
        return pref.getString(fbid,"");
    }

    public void setFbid(String fbidd) {
        pref.edit().putString(fbid,fbidd).commit();
    }

    public String getColor() {
        return pref.getString(color,"");
    }

    public void setColor(String colorr) {
        pref.edit().putString(color,colorr).commit();
    }


    public String getAlign() {
        return pref.getString(align,"");
    }

    public void setAlign(String alignment) {
        pref.edit().putString(align,alignment).commit();
    }


    public String getScriptid() {
        return pref.getString(scriptid,"");
    }

    public void setScriptid(String script_id) {
        pref.edit().putString(scriptid,script_id).commit();
    }


    public String getSavecolor() {
        return pref.getString(savecolor,"");
    }

    public void setSavecolor(String savecoloerr) {
        pref.edit().putString(savecolor,savecoloerr).commit();
    }



    public String getFbemail() {
        return pref.getString(fbemail,"");
    }

    public void setFbemail(String fbemaill) {
        pref.edit().putString(fbemail,fbemaill).commit();
    }

    public String getTag() {
        return pref.getString(tag,"");
    }

    public void setTag(String tAG) {
        pref.edit().putString(tag,tAG).commit();

    }


}
