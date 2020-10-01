package com.project.prompster_live.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.project.prompster_live.Activity.Login_Screen;
import com.project.prompster_live.Activity.Navigation;
import com.project.prompster_live.Activity.Script_Toolbar;
import com.project.prompster_live.Api.ApiClient;
import com.project.prompster_live.Api.ApiInterface;
import com.project.prompster_live.Fragments.Fragment_Board;
import com.project.prompster_live.Interface.Recycler_item_click;
import com.project.prompster_live.Pojo.AddScript;
import com.project.prompster_live.Pojo.Delete_Story_board_pojo;
import com.project.prompster_live.Pojo.EditStoryPojo;
import com.project.prompster_live.Pojo.Script_Pojo;
import com.project.prompster_live.Pojo.ShowStoryBoard_Datum;
import com.project.prompster_live.Pojo.ShowStoryBoard_pojo;
import com.project.prompster_live.Pojo.Storyboard;
import com.project.prompster_live.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class StoryBoard_Adapter extends RecyclerView.Adapter<StoryBoard_Adapter.ViewHolder> {
    Context context;
    List<ShowStoryBoard_Datum> showstoryboardlist = new ArrayList<>();
    String title_text, description_text, user_id, script_id, storyboard_id, title, desc;
    private Recycler_item_click mlistener;
    TextView tv_cancel, tv_delete, tv_cancell, tv_okay;
    Dialog dialog, dialogg;
TextView tv_ok;
    public StoryBoard_Adapter(Context fragment_board, List<ShowStoryBoard_Datum> storyboard_list) {

        this.context = fragment_board;
        this.showstoryboardlist = storyboard_list;
        this.mlistener = mlistener;

    }


    @NonNull
    @Override
    public StoryBoard_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_board, parent, false);


        // set the view's size, margins, paddings and layout parameters
        return new StoryBoard_Adapter.ViewHolder(v, mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull final StoryBoard_Adapter.ViewHolder holder, final int position) {

        try {
            title_text = showstoryboardlist.get(position).getStoryboardName().toString();
            description_text = showstoryboardlist.get(position).getStoryboardText().toString();
            holder.tv_intro.setText(title_text);
            holder.tv_write_text.setText(description_text);
        } catch (IndexOutOfBoundsException e) {

        }


        holder.tv_intro.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {

                    title = holder.tv_intro.getText().toString();
                    desc = holder.tv_write_text.getText().toString();
                    user_id = showstoryboardlist.get(position).getUserId().toString();
                    script_id = showstoryboardlist.get(position).getScriptId().toString();
                    storyboard_id = showstoryboardlist.get(position).getId().toString();
                    holder.rel_storyboard_bottom.setVisibility(View.GONE);

                    editStoryBoardapi(position, user_id, script_id, storyboard_id, title, desc, holder);

                }
                return false;
            }
        });
        holder.tv_write_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {

                    title = holder.tv_intro.getText().toString();
                    desc = holder.tv_write_text.getText().toString();
                    user_id = showstoryboardlist.get(position).getUserId().toString();
                    script_id = showstoryboardlist.get(position).getScriptId().toString();
                    storyboard_id = showstoryboardlist.get(position).getId().toString();
                    holder.rel_storyboard_bottom.setVisibility(View.GONE);

                    editStoryBoardapi(position, user_id, script_id, storyboard_id, title, desc, holder);

                }
                return false;
            }
        });


//        holder.tv_intro.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                holder.rel_storyboard_bottom.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                holder.rel_storyboard_bottom.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
////                holder.rel_storyboard_bottom.setVisibility(View.GONE);
//            }
//        });
//
//        holder.tv_write_text.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                holder.rel_storyboard_bottom.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                holder.rel_storyboard_bottom.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
////                holder.rel_storyboard_bottom.setVisibility(View.GONE);
//            }
//        });


//        holder.rel_storyboard_bottom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                title = holder.tv_intro.getText().toString();
//                desc = holder.tv_write_text.getText().toString();
//                user_id = showstoryboardlist.get(position).getUserId().toString();
//                script_id = showstoryboardlist.get(position).getScriptId().toString();
//                storyboard_id =showstoryboardlist.get(position).getId().toString();
//                holder.rel_storyboard_bottom.setVisibility(View.GONE);
//
//                editStoryBoardapi(position,user_id,script_id,storyboard_id,title,desc,holder);
//            }
//        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(context);


                dialog.setContentView(R.layout.delete_storyboard_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                tv_delete = dialog.findViewById(R.id.tv_delete);
                tv_cancel = dialog.findViewById(R.id.tv_cancel);


                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();


                tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
//                        Toast.makeText(context, "hellooo", Toast.LENGTH_SHORT).show();
                        dialog = new Dialog(context);


                        dialog.setContentView(R.layout.delete_or_not);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        tv_okay = dialog.findViewById(R.id.tv_okay);
                        tv_cancell = dialog.findViewById(R.id.tv_cancell);

                        tv_okay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                user_id = showstoryboardlist.get(position).getUserId().toString();
                                script_id = showstoryboardlist.get(position).getScriptId().toString();
                                storyboard_id = showstoryboardlist.get(position).getId().toString();
                                holder.rel_storyboard_bottom.setVisibility(View.GONE);
                                deleteStoryBoardapi(position, user_id, script_id, storyboard_id);

                            }
                        });

                        tv_cancell.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });


            }
        });
    }

    private void editStoryBoardapi(final int position, final String user_id, final String script_id, String storyboard_id, String title, String desc, final ViewHolder holder) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        retrofit2.Call<EditStoryPojo> call = apiService.edit_story_board(user_id, script_id, storyboard_id, title, desc, "");
        call.enqueue(new retrofit2.Callback<EditStoryPojo>() {
            @Override
            public void onResponse(Call<EditStoryPojo> call, Response<EditStoryPojo> response) {

                Log.e("Edit StoryBoard url", call.request().toString());

                if (response.isSuccessful()) {

                    Log.w("Edit StoryBoard response", new Gson().toJson(response));
                    if (response.body().getSuccess().toString().equals("1")) {
                        Log.e("Edit StoryBoard response", new Gson().toJson(response));

                        showstoryBoardApi(user_id, script_id);
//                        notifyDataSetChanged();


                    } else {


                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);

                        sweetAlertDialog.setTitleText(response.body().getMessage().toString());

                        sweetAlertDialog.show();
                        Button btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
                        btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

                    }

                }
            }

            @Override
            public void onFailure(Call<EditStoryPojo> call, Throwable t) {

                Log.e("errror", String.valueOf(t));

//                dialog = new Dialog(getApplicationContext());
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


    private void showstoryBoardApi(String userid, String script_id) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        retrofit2.Call<ShowStoryBoard_pojo> call = apiService.showStoryBoard(userid, script_id);
        call.enqueue(new retrofit2.Callback<ShowStoryBoard_pojo>() {
            @Override
            public void onResponse(Call<ShowStoryBoard_pojo> call, Response<ShowStoryBoard_pojo> response) {
                try {


                    if (response.isSuccessful()) {

                        Log.w("response", new Gson().toJson(response));
                        if (response.body().getSuccess().toString().equals("1")) {
//                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            showstoryboardlist.clear();
                            showstoryboardlist.addAll(response.body().getData());
                            notifyDataSetChanged();


                        } else {

                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.ERROR_TYPE);

                            sweetAlertDialog.setTitleText(response.body().getMessage().toString());

                            sweetAlertDialog.show();
                            Button btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
                            btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
//                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ShowStoryBoard_pojo> call, Throwable t) {

                Log.e("Filure", String.valueOf(t));
//                Toast.makeText(getApplicationContext(), "Error in api", Toast.LENGTH_SHORT).show();

                dialog = new Dialog(getApplicationContext());


                dialog.setContentView(R.layout.error_api);
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
        });


    }


    private void deleteStoryBoardapi(final int positionn, String user_id, String script_id, String storyboard_id) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        retrofit2.Call<Delete_Story_board_pojo> call = apiService.deleteStoryBoard(user_id, script_id, storyboard_id);
        call.enqueue(new retrofit2.Callback<Delete_Story_board_pojo>() {
            @Override
            public void onResponse(Call<Delete_Story_board_pojo> call, Response<Delete_Story_board_pojo> response) {

                Log.e("Add Script url", call.request().toString());

                if (response.isSuccessful()) {

                    Log.w("Add Script response", new Gson().toJson(response));
                    if (response.body().getSuccess().toString().equals("1")) {
                        Log.e("Add Script response", new Gson().toJson(response));
                        showstoryboardlist.remove(positionn);
                        notifyDataSetChanged();


                    } else {


                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);

                        sweetAlertDialog.setTitleText(response.body().getMessage().toString());

                        sweetAlertDialog.show();
                        Button btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
                        btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

                    }

                }
            }

            @Override
            public void onFailure(Call<Delete_Story_board_pojo> call, Throwable t) {

                Log.e("errror", String.valueOf(t));

            }
        });


    }


    @Override
    public int getItemCount() {
        return showstoryboardlist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout rl_introo, rel_storyboard_bottom;
        EditText tv_write_text, tv_intro;
        private Recycler_item_click mlistener;
        ImageView view;
        TextView tv_title;

        public ViewHolder(@NonNull View itemView, Recycler_item_click mlistener) {
            super(itemView);
            this.mlistener = mlistener;
            rl_introo = itemView.findViewById(R.id.rl_introo);
            rel_storyboard_bottom = itemView.findViewById(R.id.rel_storyboard_bottom);
            tv_intro = itemView.findViewById(R.id.tv_intro);
            tv_write_text = itemView.findViewById(R.id.tv_write_text);
            view = itemView.findViewById(R.id.view);

            tv_intro.setHorizontallyScrolling(false);
            tv_intro.setRawInputType(InputType.TYPE_CLASS_TEXT);
            tv_intro.setImeOptions(EditorInfo.IME_ACTION_DONE);

            tv_write_text.setHorizontallyScrolling(false);
            tv_write_text.setRawInputType(InputType.TYPE_CLASS_TEXT);
            tv_write_text.setImeOptions(EditorInfo.IME_ACTION_DONE);
            tv_title = itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            try {
                mlistener.onClick(view, getAdapterPosition());
            } catch (NullPointerException e) {

            }


        }
    }

}