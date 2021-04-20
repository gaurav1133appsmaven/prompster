package com.project.prompster_live.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginBehavior;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.project.prompster_live.Api.ApiClient;
import com.project.prompster_live.Api.ApiInterface;
import com.project.prompster_live.Pojo.SignupPOJO;
import com.project.prompster_live.R;
import com.project.prompster_live.Utils.Shared_PrefrencePrompster;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

public class SignUp_Screen extends AppCompatActivity {
    RelativeLayout rl_getstarted, rl_google, rl_facebook;
    TextView et_frstname, et_lastname, et_email, et_pass,tv_getttt,tv_terms,tv_conditions, tv_privacy_policy;

    String frstname, lastname, mail, pass, fbid, move, gg_firstname, gglastname, ggemail, ggId, first_name, last_name, email, fb_id;
    Dialog dialog, success;
    TextView tv_ok;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static String regularExpresionOfEmailId =
            "^(?!.{51})([A-Za-z0-9])+([A-Za-z0-9._-])+@([A-Za-z0-9._-])+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    boolean iscolor = true;
    Boolean isSocial;
    Button btn;
    Shared_PrefrencePrompster shared_prefrencePrompster;
    GoogleSignInClient mGoogleSignInClient;
    CallbackManager callbackManager;
    RelativeLayout iv_back;
    String tag = "0";
    ACProgressFlower dialog_progress;
ProgressDialog progressDialo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__screen);

        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(getApplicationContext());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(SignUp_Screen.this, gso);
        FacebookSdk.sdkInitialize(this);
        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(getApplicationContext());


        init();


        et_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                frstname = et_frstname.getText().toString();
                lastname = et_lastname.getText().toString();
                mail = et_email.getText().toString();
                pass = et_pass.getText().toString();
//                    Toast.makeText(SignUp_Screen.this, "innnnn", Toast.LENGTH_SHORT).show();
                if(!frstname.equals("")&&!lastname.equals("")&&!mail.equals("")&&!pass.equals("")){
//                        Toast.makeText(SignUp_Screen.this, "if", Toast.LENGTH_SHORT).show();

                    tv_getttt. setBackground(getResources().getDrawable(R.drawable.gmail_button));
                }
                else{
//                        Toast.makeText(SignUp_Screen.this, "else", Toast.LENGTH_SHORT).show();

                    tv_getttt. setBackground(getResources().getDrawable(R.drawable.login_button));
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
                    frstname = et_frstname.getText().toString();
                    lastname = et_lastname.getText().toString();
                    mail = et_email.getText().toString();
                    pass = et_pass.getText().toString();
//                    Toast.makeText(SignUp_Screen.this, "innnnn", Toast.LENGTH_SHORT).show();
                    if(!frstname.equals("")&&!lastname.equals("")&&!mail.equals("")&&!pass.equals("")){
//                        Toast.makeText(SignUp_Screen.this, "if", Toast.LENGTH_SHORT).show();

                        tv_getttt. setBackground(getResources().getDrawable(R.drawable.gmail_button));
                    }
                    else{
//                        Toast.makeText(SignUp_Screen.this, "else", Toast.LENGTH_SHORT).show();

                        tv_getttt. setBackground(getResources().getDrawable(R.drawable.login_button));
                    }
                }
                return false;
            }
        });




    }



    private void init() {

        iv_back = findViewById(R.id.iv_back);
        et_frstname = findViewById(R.id.et_frstname);
        et_lastname = findViewById(R.id.et_lastname);
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        rl_google = findViewById(R.id.rl_google);
        rl_facebook = findViewById(R.id.rl_facebook);
        rl_getstarted = findViewById(R.id.rl_getstarted);
        tv_getttt=findViewById(R.id.tv_getttt);
        tv_conditions=findViewById(R.id.tv_conditions);
        tv_terms=findViewById(R.id.tv_terms);
        tv_privacy_policy=findViewById(R.id.tv_privacy_policy);


        rl_getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation();


            }
        });

        tv_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://scriptively.com/tou"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        tv_conditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://scriptively.com/tou"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        tv_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://scriptively.com/privacy"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = new Intent(SignUp_Screen.this, Login_Screen.class);
                startActivity(signInIntent);


            }
        });

        rl_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 100);
            }
        });

        rl_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackManager = CallbackManager.Factory.create();

                // Set permissions
                LoginManager.getInstance().logInWithReadPermissions(SignUp_Screen.this, Arrays.asList("email", "public_profile"));

                LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_ONLY).registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
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

//                                        et_frstname.setText(first_name);
//                                        et_lastname.setText(last_name);
//                                        et_email.setText(email);
//
                                         callSignupapi_fb(first_name, last_name, email,"Facebook",fb_id);


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

            }
        });
    }

    private void callSignupapi_fb(String frstname, String lastname, String email, String facebook, String fb_id) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        retrofit2.Call<SignupPOJO> call = apiService.signupdata(frstname, lastname, email, "00000000",facebook,fb_id);
        call.enqueue(new retrofit2.Callback<SignupPOJO>() {
            @Override
            public void onResponse(Call<SignupPOJO> call, Response<SignupPOJO> response) {
                Log.e("signin url", call.request().toString());

                if (response.isSuccessful()) {

                    Log.w("response", new Gson().toJson(response));
                    if (response.body().getSuccess().toString().equals("1")) {
                        Log.e("successfull response", new Gson().toJson(response));


                        dialog = new Dialog(SignUp_Screen.this);


                        dialog.setContentView(R.layout.signup_successfully);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        tv_ok = dialog.findViewById(R.id.tv_ok);


                        tv_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();

                                Intent intent = new Intent(SignUp_Screen.this, Navigation.class);
                                startActivity(intent);
                                finish();

                            }
                        });
                        dialog.show();

                        shared_prefrencePrompster.setUserid(String.valueOf(response.body().getData().getId()));
                        shared_prefrencePrompster.setFirstName(String.valueOf(response.body().getData().getFirstName()));

                        shared_prefrencePrompster.setLastName(response.body().getData().getLastName().toString());

                        shared_prefrencePrompster.setEmail(response.body().getData().getEmail().toString());

                        shared_prefrencePrompster.setPassword(response.body().getData().getPassword().toString());


                    } else {


                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(SignUp_Screen.this, SweetAlertDialog.ERROR_TYPE);

                        sweetAlertDialog.setTitleText(response.body().getMessage().toString());

                        sweetAlertDialog.show();
                        Button btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
                        btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                    }
                }

            }

            @Override
            public void onFailure(Call<SignupPOJO> call, Throwable t) {
//                dialogprogress.dismiss();
                Log.e("Filuresignup", String.valueOf(t));
//                Toast.makeText(SignUp_Screen.this, "Server Not Found", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void validation() {


        if (et_frstname.getText().toString().trim().isEmpty()) {
//            rl_getstarted.setBackgroundColor(getResources().getColor(R.color.grey));
//            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(SignUp_Screen.this, SweetAlertDialog.ERROR_TYPE);
//
//            sweetAlertDialog.setTitleText("First name is not empty");
//
//            sweetAlertDialog.show();
//            btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
//            btn.setBackgroundColor(getResources().getColor(R.color.app_color));


            dialog = new Dialog(SignUp_Screen.this);


            dialog.setContentView(R.layout.first_name);
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


        } else if (et_lastname.getText().toString().trim().isEmpty()) {
//            rl_getstarted.setBackgroundColor(getResources().getColor(R.color.grey));
//            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(SignUp_Screen.this, SweetAlertDialog.ERROR_TYPE);
//
//            sweetAlertDialog.setTitleText("Last name is not empty");
//
//            sweetAlertDialog.show();
//            btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
//            btn.setBackgroundColor(getResources().getColor(R.color.app_color));


            dialog = new Dialog(SignUp_Screen.this);


            dialog.setContentView(R.layout.last_name);
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


        } else if (et_email.getText().toString().trim().isEmpty()) {
//            rl_getstarted.setBackgroundColor(getResources().getColor(R.color.grey));
//            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(SignUp_Screen.this, SweetAlertDialog.ERROR_TYPE);
//
//            sweetAlertDialog.setTitleText("Email is not empty");
//
//            sweetAlertDialog.show();
//            btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
//            btn.setBackgroundColor(getResources().getColor(R.color.app_color));


            dialog = new Dialog(SignUp_Screen.this);


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


        } else if (!et_email.getText().toString().trim().matches(emailPattern)) {
//            rl_getstarted.setBackgroundColor(getResources().getColor(R.color.grey));
//
//            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(SignUp_Screen.this, SweetAlertDialog.ERROR_TYPE);
//
//            sweetAlertDialog.setTitleText("Enter valid email");
//
//            sweetAlertDialog.show();
//            btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
//            btn.setBackgroundColor(getResources().getColor(R.color.app_color));


            dialog = new Dialog(SignUp_Screen.this);


            dialog.setContentView(R.layout.enter_valid_mail);
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


        } else if (et_pass.getText().toString().trim().length() < 8) {
//            rl_getstarted.setBackgroundColor(getResources().getColor(R.color.grey));
            dialog = new Dialog(SignUp_Screen.this);


            dialog.setContentView(R.layout.pass_dialog);
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

        }  else {



//                            Intent  signup_inIntent=new Intent(SignUp_Screen.this,Script_Toolbar.class);
//                            startActivity(signup_inIntent);
//                            finish();

            frstname = et_frstname.getText().toString();
            lastname = et_lastname.getText().toString();
            mail = et_email.getText().toString();
            pass = et_pass.getText().toString();

            dialog_progress= new ACProgressFlower.Builder(this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .text("Please wait...")
                    .fadeColor(Color.DKGRAY).build();
            dialog_progress.show();

            callSignupapi(frstname, lastname, mail, pass,"Normal","");
        }


    }

    private void registerAccount(String frstname, String lastname, String mail, String pass) {
        if (!TextUtils.isEmpty(frstname) && !TextUtils.isEmpty(lastname) && !TextUtils.isEmpty(mail) && !TextUtils.isEmpty(pass)) {

            rl_getstarted.setBackgroundColor(getResources().getColor(R.color.app_color));// set here your backgournd to button
            // save data in database

        } else {

        }
    }


    private void callSignupapi( String frstname, String lastname, String mail, String pass, String type, String unique_id) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        retrofit2.Call<SignupPOJO> call = apiService.signupdata(frstname, lastname, mail, pass,type,unique_id);
        call.enqueue(new retrofit2.Callback<SignupPOJO>() {
            @Override
            public void onResponse(Call<SignupPOJO> call, Response<SignupPOJO> response) {
                Log.e("signin url", call.request().toString());

                if (response.isSuccessful()) {

                    Log.w("response", new Gson().toJson(response));
                    if (response.body().getSuccess().toString().equals("1")) {
                        Log.e("successfull response", new Gson().toJson(response));

                        dialog_progress.dismiss();
                        dialog = new Dialog(SignUp_Screen.this);


                        dialog.setContentView(R.layout.signup_successfully);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        tv_ok = dialog.findViewById(R.id.tv_ok);


                        tv_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();

                                Intent intent = new Intent(SignUp_Screen.this, Navigation.class);
                                startActivity(intent);
                                finish();

                            }
                        });
                        dialog.show();

                        shared_prefrencePrompster.setUserid(String.valueOf(response.body().getData().getId()));
                        shared_prefrencePrompster.setFirstName(String.valueOf(response.body().getData().getFirstName()));

                        shared_prefrencePrompster.setLastName(response.body().getData().getLastName().toString());

                        shared_prefrencePrompster.setEmail(response.body().getData().getEmail().toString());

                        shared_prefrencePrompster.setPassword(response.body().getData().getPassword().toString());


                    } else {
                        dialog_progress.dismiss();

                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(SignUp_Screen.this, SweetAlertDialog.ERROR_TYPE);

                        sweetAlertDialog.setTitleText(response.body().getMessage().toString());

                        sweetAlertDialog.show();
                        Button btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
                        btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                    }
                }

            }

            @Override
            public void onFailure(Call<SignupPOJO> call, Throwable t) {
//                dialogprogress.dismiss();
                Log.e("Filuresignup", String.valueOf(t));
//                dialog = new Dialog(SignUp_Screen.this);
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
//                Toast.makeText(SignUp_Screen.this, "Server Not Found", Toast.LENGTH_SHORT).show();
            }
        });
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

                gg_firstname = account.getGivenName();
                gglastname = account.getFamilyName();
                ggemail = account.getEmail();
                ggId = account.getId();

//                et_frstname.setText(gg_firstname);
//                et_lastname.setText(gg_firstname);
//                et_email.setText(ggemail);


                callSignupapi_google(gg_firstname, gglastname, ggemail,"Google",ggId);







                // Signed in successfully, show authenticated UI.
            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.e("googlesignin", "signInResult:failed code=" + e.getStatusCode());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void callSignupapi_google(String gg_firstname, String gglastname, String ggemail, String google, String ggId) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        retrofit2.Call<SignupPOJO> call = apiService.signupdata(gg_firstname, gglastname, ggemail, "00000000", google, ggId);
        call.enqueue(new retrofit2.Callback<SignupPOJO>() {
            @Override
            public void onResponse(Call<SignupPOJO> call, Response<SignupPOJO> response) {
                Log.e("signin url", call.request().toString());

                if (response.isSuccessful()) {

                    Log.w("response", new Gson().toJson(response));
                    if (response.body().getSuccess().toString().equals("1")) {
                        Log.e("successfull response", new Gson().toJson(response));


                        dialog = new Dialog(SignUp_Screen.this);


                        dialog.setContentView(R.layout.signup_successfully);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        tv_ok = dialog.findViewById(R.id.tv_ok);


                        tv_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();

                                Intent intent = new Intent(SignUp_Screen.this, Navigation.class);
                                startActivity(intent);
                                finish();

                            }
                        });
                        dialog.show();

                        shared_prefrencePrompster.setUserid(String.valueOf(response.body().getData().getId()));
                        shared_prefrencePrompster.setFirstName(String.valueOf(response.body().getData().getFirstName()));

                        shared_prefrencePrompster.setLastName(response.body().getData().getLastName().toString());

                        shared_prefrencePrompster.setEmail(response.body().getData().getEmail().toString());

                        shared_prefrencePrompster.setPassword(response.body().getData().getPassword().toString());


                    } else {


                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(SignUp_Screen.this, SweetAlertDialog.ERROR_TYPE);

                        sweetAlertDialog.setTitleText(response.body().getMessage().toString());

                        sweetAlertDialog.show();
                        Button btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
                        btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                    }
                }

            }

            @Override
            public void onFailure(Call<SignupPOJO> call, Throwable t) {
//                dialogprogress.dismiss();
                Log.e("Filuresignup", String.valueOf(t));
//                dialog = new Dialog(SignUp_Screen.this);
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
//                Toast.makeText(SignUp_Screen.this, "Server Not Found", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
