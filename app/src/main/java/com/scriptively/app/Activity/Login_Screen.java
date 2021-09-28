package com.scriptively.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.scriptively.app.Api.ApiClient;
import com.scriptively.app.Api.ApiInterface;
import com.scriptively.app.Pojo.LoginPojo;

import com.scriptively.app.R;
import com.scriptively.app.Utils.Shared_PrefrencePrompster;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;


import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import cn.pedant.SweetAlert.SweetAlertDialog;


import retrofit2.Call;

import retrofit2.Response;

public class Login_Screen extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = "11";
    private static Context contextCompat;
    RelativeLayout rl_sign;
    TextView tv_forgotpassword;
    SweetAlertDialog alertDialog;
    EditText et_email, et_pass;
    String FACEBOOK_PERMISSION[] = {"email", "public_profile"};
    Integer user_id;
    String emaill, password, shared_email, shared_pass, shareduserid, sharedusername, EMAIL, USERNAME, fb_id, shared_fbid, shared_lastname, first_name, last_name,
            email, ggPass, id,gmailId;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Dialog dialog;
    TextView tv_ok,tv_login;
    RelativeLayout rl_ok;
    Shared_PrefrencePrompster shared_prefrencePrompster;
    CallbackManager callbackManager;
    GoogleSignInClient mGoogleSignInClient;
    RelativeLayout fbSignin, googlesignin;
    Button btn;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    boolean iscolor = true;
    RelativeLayout iv_back;
    Profile profile;
    private ProfileTracker mProfileTracker;
    private ProgressDialog pDialog;
    ACProgressFlower dialog_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__screen);

        printHashKey();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(Login_Screen.this, gso);
        FacebookSdk.sdkInitialize(this);

        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(getApplicationContext());
        dialog_progress = new  ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait...")

                .fadeColor(Color.DKGRAY).build();
        dialog_progress.setCancelable(false);
        //  user_id = Integer.valueOf(shared_prefrencePrompster.getUserid());
        EMAIL = shared_prefrencePrompster.getEmail().toString();
        USERNAME = shared_prefrencePrompster.getUsername().toString();

        init();

        et_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                emaill = et_email.getText().toString();
                password = et_pass.getText().toString();

//                    Toast.makeText(SignUp_Screen.this, "innnnn", Toast.LENGTH_SHORT).show();
                if(!emaill.equals("")&&!password.equals("")){
//                        Toast.makeText(SignUp_Screen.this, "if", Toast.LENGTH_SHORT).show();

                    tv_login. setBackground(getResources().getDrawable(R.drawable.gmail_button));
                }
                else{
//                        Toast.makeText(SignUp_Screen.this, "else", Toast.LENGTH_SHORT).show();

                    tv_login. setBackground(getResources().getDrawable(R.drawable.login_button));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE){
                    emaill = et_email.getText().toString();
                    password = et_pass.getText().toString();

//                    Toast.makeText(SignUp_Screen.this, "innnnn", Toast.LENGTH_SHORT).show();
                    if(!emaill.equals("")&&!password.equals("")){
//                        Toast.makeText(SignUp_Screen.this, "if", Toast.LENGTH_SHORT).show();

                        tv_login. setBackground(getResources().getDrawable(R.drawable.gmail_button));
                    }
                    else{
//                        Toast.makeText(SignUp_Screen.this, "else", Toast.LENGTH_SHORT).show();

                        tv_login. setBackground(getResources().getDrawable(R.drawable.login_button));
                    }
                }
                return false;
            }
        });

    }

    public void printHashKey() {
            try {
                PackageInfo info = getPackageManager().getPackageInfo(
                        "com.scriptively.app",
                        PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
            } catch (PackageManager.NameNotFoundException e) {

            } catch (NoSuchAlgorithmException e) {

            }
    }

    private void init() {

        checkinternetconnectivitty();
        googlesignin = findViewById(R.id.googlesignin);
        tv_login=findViewById(R.id.tv_login);
        fbSignin = findViewById(R.id.fbSigninn);
        rl_sign = findViewById(R.id.rl_sign);
        tv_forgotpassword = findViewById(R.id.tv_forgotpassword);
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        iv_back=findViewById(R.id.iv_back);

        fbSignin.setOnClickListener(this);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent  intent=new Intent(Login_Screen.this,Welcome.class);
              startActivity(intent);
              finish();

            }
        });

        tv_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rl_getstarted = new Intent(Login_Screen.this, Reset_Password.class);
                startActivity(rl_getstarted);
                finish();

            }
        });
        rl_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Login_Screen.this, "hello", Toast.LENGTH_SHORT).show();

                validation();


            }
        });

        googlesignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 100);
            }
        });


    }


    private void validation() {


        if (et_email.getText().toString().isEmpty()) {
            dialog = new Dialog(Login_Screen.this);


            dialog.setContentView(R.layout.email_empty);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            tv_ok = dialog.findViewById(R.id.tv_ok);



            tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();

                }
            });
            dialog.show();


        } else if (et_pass.getText().toString().trim().isEmpty()) {
            dialog = new Dialog(Login_Screen.this);


            dialog.setContentView(R.layout.password_not_empty);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            tv_ok = dialog.findViewById(R.id.tv_ok);


            tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();

                }
            });
            dialog.show();


        } else {
            emaill = et_email.getText().toString();
            password = et_pass.getText().toString();

//            Intent  intent=new Intent(Login_Screen.this,SignUp_Screen.class);
//            startActivity(intent);
//            Toast.makeText(Login_Screen.this, "hllllll", Toast.LENGTH_SHORT).show();


            dialog_progress.show();

            callLoginapi(emaill, password,"Normal","");
        }
    }

    private void callLoginapi( String email, String password,String type,String uiID) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        retrofit2.Call<LoginPojo> call = apiService.loginData(email, password,type,uiID);
        call.enqueue(new retrofit2.Callback<LoginPojo>() {
            @Override
            public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {

                Log.e("Login url", call.request().toString());

                if (response.isSuccessful()) {

                    Log.w("Login response", new Gson().toJson(response));
                    if (response.body().getSuccess().toString().equals("1")) {
                        Log.e("Login response", new Gson().toJson(response));

                        shared_prefrencePrompster.setUserid(response.body().getData().getUserId().toString());
                        shared_prefrencePrompster.setEmail(response.body().getData().getEmail());
                        shared_prefrencePrompster.setFirstName(response.body().getData().getFirstName());
                        shared_prefrencePrompster.setLastName(response.body().getData().getLastName());

                        dialog_progress.dismiss();

                        dialog = new Dialog(Login_Screen.this);

                        dialog.setContentView(R.layout.logins_successfully);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        tv_ok = dialog.findViewById(R.id.tv_ok);

                        tv_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(Login_Screen.this, Navigation.class);
                                startActivity(intent);
                                finish();
                                finishAffinity();

                            }
                        });
                        if (! isFinishing()) {

                            dialog.show();

                        }


                    }
                    else {


                        dialog_progress.dismiss();
                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Login_Screen.this, SweetAlertDialog.ERROR_TYPE);

                        sweetAlertDialog.setTitleText(response.body().getMessage().toString());

                        sweetAlertDialog.show();

                        Button btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
                        btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

                    }

                }
            }

            @Override
            public void onFailure(Call<LoginPojo> call, Throwable t) {
                dialog_progress.dismiss();
                Log.e("errror", String.valueOf(t));
//                dialog = new Dialog(Login_Screen.this);
//
//
//                dialog.setContentView(R.layout.error_api);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                tv_ok = dialog.findViewById(R.id.tv_ok);
//
//
//                tv_ok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        dialog.dismiss();
//
//
//                    }
//                });
//                dialog.show();

            }
        });
    }


    private void checkinternetconnectivitty() {
        if (CheckNetwork.isInternetAvailable(Login_Screen.this)) //returns true if internet available
        {


//            setfont();
            //do something. loadwebview.
        } else {
            try {
                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Please Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                        dialog.dismiss();

                    }
                });
                alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                        dialog.dismiss();

                    }
                });
                alertDialog.show();
            } catch (Exception e) {
                Log.e(String.valueOf(this), "Show Dialog: " + e.getMessage());
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fbSigninn:

                callbackManager = CallbackManager.Factory.create();

                // Set permissions
                LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_ONLY).logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));

                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {


                    @Override
                    public void onSuccess(LoginResult loginResult) {

                            if (Profile.getCurrentProfile() == null) {
                                mProfileTracker = new ProfileTracker() {
                                    @Override
                                    protected void onCurrentProfileChanged(Profile profile_old, Profile profile_new) {
                                        // profile2 is the new profile
                                        profile = profile_new;
                                        mProfileTracker.stopTracking();
                                    }
                                };
                                mProfileTracker.startTracking();
                            } else {
                                profile = Profile.getCurrentProfile();
                            }


                            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("response", "onCompleted: " + response.toString());
                                JSONObject json = response.getJSONObject();

                                try {
                                    if (json != null) {
                                        first_name = json.getString("first_name");
                                        last_name = json.getString("last_name");
                                        email = json.getString("email");
                                        fb_id = json.getString("id");

//                                        et_email.setText(email);

                                        callLoginapi(email, "00000000","Facebook",fb_id);
//
//                                        try {
//                                            Loginfb(fb_id,email);
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }



                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,first_name,last_name,link,email,picture");
                        request.setParameters(parameters);
                        request.executeAsync();
//                        Intent intent = new Intent(getApplicationContext(), Navigation.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                        finish();
                }

                    @Override
                    public void onCancel() {
                        Log.e("fblogin", "onCancel: ");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.e("fblogin", "onError: " + exception.getMessage());
                    }
                });
                break;
            }

//                LoginManager.getInstance().logOut();
//                LoginManager.getInstance().logInWithReadPermissions(Login_Screen.this, Arrays.asList(FACEBOOK_PERMISSION));

        }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (callbackManager != null)
            callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                first_name = account.getGivenName();
                last_name = account.getFamilyName();
                email = account.getEmail();
                gmailId = account.getId();
//                et_email.setText(email);
                callLoginapi(email, "00000000","Google",gmailId);


                // Signed in successfully, show authenticated UI.
            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.e("googlesignin", "signInResult:failed code=" + e.getStatusCode());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}





