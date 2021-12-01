package com.scriptively.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scriptively.app.Api.ApiClient;
import com.scriptively.app.Api.ApiInterface;
import com.scriptively.app.Pojo.Forgotpassword_Pojo;
import com.scriptively.app.R;
import com.google.gson.Gson;
import com.scriptively.app.Utils.Shared_PrefrencePrompster;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Response;

public class Reset_Password extends AppCompatActivity {
ImageView image;
    EditText et_resetpassword;
TextView tv_resetpassword;
    ACProgressFlower dialog_progress;
    String userEmail,Email;
    Dialog dialog;
    TextView tv_ok;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    RelativeLayout rl_Submitbttn,rl_backtologin;
    Shared_PrefrencePrompster shared_prefrencePrompster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset__password);
        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(this);
        Email = shared_prefrencePrompster.getEmail().toString();
        init();


        et_resetpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                userEmail = et_resetpassword.getText().toString().trim();

//                    Toast.makeText(SignUp_Screen.this, "innnnn", Toast.LENGTH_SHORT).show();
                if(!userEmail.equals("")){
//                        Toast.makeText(SignUp_Screen.this, "if", Toast.LENGTH_SHORT).show();

                    tv_resetpassword. setBackground(getResources().getDrawable(R.drawable.gmail_button));
                }
                else{
//                        Toast.makeText(SignUp_Screen.this, "else", Toast.LENGTH_SHORT).show();

                    tv_resetpassword. setBackground(getResources().getDrawable(R.drawable.login_button));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_resetpassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_DONE){
                    userEmail = et_resetpassword.getText().toString().trim();

//                    Toast.makeText(SignUp_Screen.this, "innnnn", Toast.LENGTH_SHORT).show();
                    if(!userEmail.equals("")){
//                        Toast.makeText(SignUp_Screen.this, "if", Toast.LENGTH_SHORT).show();

                        tv_resetpassword. setBackground(getResources().getDrawable(R.drawable.gmail_button));
                    }
                    else{
//                        Toast.makeText(SignUp_Screen.this, "else", Toast.LENGTH_SHORT).show();

                        tv_resetpassword. setBackground(getResources().getDrawable(R.drawable.login_button));
                    }
                }
                return false;
            }
        });
    }

    private void init() {
        tv_resetpassword=findViewById(R.id.tv_resetpassword);
        image=findViewById(R.id.image);
        et_resetpassword=findViewById(R.id.et_resetpassword);

rl_backtologin=findViewById(R.id.rl_signin);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login=new Intent(Reset_Password.this,Login_Screen.class);
                startActivity(login);
                finish();
            }
        });
        
        rl_backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodvalidate();
            }
        });
    }

    private void methodvalidate() {

        userEmail = et_resetpassword.getText().toString().trim();

        if (et_resetpassword.getText().toString().trim().length() <= 0) {
            et_resetpassword.setError("Enter Email");

        }
        else if (userEmail.matches(emailPattern) == false) {


            et_resetpassword.setError("Enter Valid Email");

        }
//        else if (!userEmail.equals(Email)){
//            dialog = new Dialog(Reset_Password.this);
//
//
//            dialog.setContentView(R.layout.enter_valid_mail);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            tv_ok = dialog.findViewById(R.id.tv_ok);
//
//
//            tv_ok.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    dialog.dismiss();
//
//                }
//            });
//            dialog.show();
//
//        }
            else{
            dialog_progress= new ACProgressFlower.Builder(this)
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .text("Please wait...")
                    .fadeColor(Color.DKGRAY).build();
            dialog_progress.show();
            call_FrorgtpasswordApi();


        }
    }

    private void call_FrorgtpasswordApi() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        retrofit2.Call<Forgotpassword_Pojo> call = apiService.forgetpass(userEmail);
        call.enqueue(new retrofit2.Callback<Forgotpassword_Pojo>() {
            @Override
            public void onResponse(Call<Forgotpassword_Pojo> call, Response<Forgotpassword_Pojo> response) {
                try {


                    if (response.isSuccessful()) {

                        Log.w("response", new Gson().toJson(response));
                        if (response.body().getSuccess().toString().equals("1")) {
//                            Toast.makeText(Reset_Password.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        String Email = response.body().getData().getEmail();
                            dialog_progress.dismiss();

                            dialog = new Dialog(Reset_Password.this);


                            dialog.setContentView(R.layout.reset_pssword);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            tv_ok = dialog.findViewById(R.id.tv_ok);


                            tv_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialog.dismiss();
                                    Intent loginintent = new Intent(Reset_Password.this, Login_Screen.class);
                                    startActivity(loginintent);
                                    finish();
                                }
                            });
                            dialog.show();




                        } else {
                            dialog_progress.dismiss();
                            dialog = new Dialog(Reset_Password.this);


                            dialog.setContentView(R.layout.email_is_not_database);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            tv_ok = dialog.findViewById(R.id.tv_ok);


                            tv_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialog.dismiss();

                                }
                            });
                            dialog.show();
                        }
                    }
                }catch (Exception e){

                }}

            @Override
            public void onFailure(Call<Forgotpassword_Pojo> call, Throwable t) {

                Log.e("Filure", String.valueOf(t));
//                Toast.makeText(Reset_Password.this, "Error in api", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

