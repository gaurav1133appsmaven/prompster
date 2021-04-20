package com.project.prompster_live.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.FileAction;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.DriveScopes;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.project.prompster_live.Adapter.FilesAdapter;
import com.project.prompster_live.Adapter.GoogleDriveFilesAdapter;
import com.project.prompster_live.Interface.Recycler_item_click;
import com.project.prompster_live.R;
import com.project.prompster_live.Utils.DriveServiceHelper;
import com.project.prompster_live.Utils.Shared_PrefrencePrompster;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GoogleDriveFiles extends AppCompatActivity {

    java.io.InputStream fileInputStream;
    java.io.File file;
    TextView log_off, tv_reset, tv_title_main;
    Shared_PrefrencePrompster shared_prefrencePrompster;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private String mPath, title, description, userId;
    private static final int REQUEST_CODE_SIGN_IN = 100;
    private static final int REQUEST_CODE_OPEN_DOCUMENT = 2;
    private static final String TAG = "GoogleDrive";

    private GoogleSignInClient client;
    private DriveServiceHelper mDriveServiceHelper;
    private GoogleDriveFilesAdapter googleDriveFilesAdapter;
    RecyclerView recyclerView;
    List<String> mFiles = new ArrayList<>();
    Recycler_item_click mListener;
    Drive googleDriveService;
    FileList files;
    String title_tag;
    Intent intent;
    List<File> file_list = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_box);
        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(this);
        userId = shared_prefrencePrompster.getUserid().toString();

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        intent = getIntent();
        title_tag = intent.getStringExtra("drive");
        log_off = findViewById(R.id.log_off_dropbox);
        tv_title_main = findViewById(R.id.tv_title_main);
        tv_reset = findViewById(R.id.tv_reset);
        //init picaso client
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_dropbox);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (title_tag.equals("1")) {
            tv_title_main.setText("Google Drive");
        }

        mListener = new Recycler_item_click() {
            @Override
            public void onClick(View view, int position, String type) {
                String fileId = file_list.get(position).get("id").toString();
                Log.e("FIle Id", fileId);


                GoogleDriveFiles.this.runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    public void run() {
                        DriveServiceHelper driveServiceHelper = new DriveServiceHelper(googleDriveService);

                        driveServiceHelper.readFile(fileId, userId);
                        ProgressDialog dialog = new ProgressDialog(GoogleDriveFiles.this);
                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.setCancelable(false);
                        dialog.setMessage("Loading");
                        dialog.show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();

                                Dialog dialogg = new Dialog(GoogleDriveFiles.this);

                                dialogg.setContentView(R.layout.email_empty);
                                dialogg.setCanceledOnTouchOutside(false);
                                dialogg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                TextView tv_scriptDelete = dialogg.findViewById(R.id.tv_path);
                                TextView tv_okay = dialogg.findViewById(R.id.tv_ok);
                                tv_scriptDelete.setText("Successfully imported file" + " " + file_list.get(position).get("name").toString());

                                tv_okay.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogg.dismiss();
                                        Intent intent = new Intent(GoogleDriveFiles.this, Navigation.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                                dialogg.show();
                            }

                        }, 1500);

                    }
                });

            }

            @Override
            public void onClicFonts(View view, int position) {

            }
        };

        Log.d(TAG, "Requesting sign-in");

        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                        .build();
        client = GoogleSignIn.getClient(this, signInOptions);

        // The result of the sign-in Intent is handled in onActivityResult.
        startActivityForResult(client.getSignInIntent(), REQUEST_CODE_SIGN_IN);

        log_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.signOut();
                Intent intent = new Intent(GoogleDriveFiles.this, Navigation.class);
                startActivity(intent);
            }
        });
        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                startActivity(getIntent());
            }
        });

    }

    private void readTextFile(java.io.File absolutePath) {

        try {
            //Read text from file
            StringBuilder text = new StringBuilder();


            BufferedReader br = new BufferedReader(new FileReader(absolutePath));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }

            br.close();
            Log.e("TextFileData:-----", text.toString());
            Script_Toolbar script_toolbar = new Script_Toolbar();
            script_toolbar.AddScriptapi(userId, absolutePath.getName(), text.toString(),"new","");
        } catch (Exception e) {

        }
    }


    public void ReadPdfFile(String actualfilepath) {
        try {
            String parsedText = "";
            PdfReader reader = new PdfReader(actualfilepath);
            int n = reader.getNumberOfPages();
            for (int i = 0; i < n; i++) {
                parsedText = parsedText + PdfTextExtractor.getTextFromPage(reader, i + 1).trim() + "\n"; //Extracting the content from the different pages
            }
//            System.out.println(parsedText);
            Log.e("Parsed Text", parsedText);
//            textView.setText(parsedText);
            reader.close();

            Script_Toolbar script_toolbar = new Script_Toolbar();
            script_toolbar.AddScriptapi(userId, file.getName(), parsedText,"new","");


        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public List<File> retrieveAllFiles(Drive service) throws IOException {
        List<File> result = new ArrayList<File>();
        Files.List request = service.files().list();

        do {
            try {
                files = request.execute();

//                Log.e("Check_Name", files.getFiles().get(0).get("name").toString());


                for (int i = 0; i < files.getFiles().size(); i++) {
//
                    if (files.getFiles().get(i).get("name").toString().contains(".pdf")) {
                        mFiles.add(files.getFiles().get(i).get("name").toString());
                        file_list.add(files.getFiles().get(i));
                        Log.e("Check_pdf", files.getFiles().get(i).toString());
//                        mFilesAdapter.setFiles(result.getEntries());
                    } else if (files.getFiles().get(i).get("name").toString().contains(".doc")) {
                        mFiles.add(files.getFiles().get(i).get("name").toString());
                        file_list.add(files.getFiles().get(i));
                        Log.e("Check_pdf", files.getFiles().get(i).toString());
//                        mFilesAdapter.setFiles(result.getEntries());
                    } else if (files.getFiles().get(i).get("name").toString().contains(".docx")) {
                        mFiles.add(files.getFiles().get(i).get("name").toString());
                        file_list.add(files.getFiles().get(i));
//                        mFilesAdapter.setFiles(result.getEntries());
                    } else if (files.getFiles().get(i).get("name").toString().contains(".txt")) {
                        mFiles.add(files.getFiles().get(i).get("name").toString());
                        file_list.add(files.getFiles().get(i));
                        Log.e("Check_pdf", files.getFiles().get(i).toString());
//                        mFilesAdapter.setFiles(result.getEntries());
                    }
                }
                result.addAll(files.getFiles());
                request.setPageToken(files.getNextPageToken());

                Collections.sort(mFiles, new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return s1.compareToIgnoreCase(s2);
                    }
                });

                Collections.sort(file_list, new Comparator<File>() {
                    public int compare(File v1, File v2) {
                        return v1.getName().compareToIgnoreCase(v2.getName());
                    }
                });

                googleDriveFilesAdapter = new GoogleDriveFilesAdapter(GoogleDriveFiles.this, mFiles, mListener);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // Stuff that updates the UI
                        recyclerView.setAdapter(googleDriveFilesAdapter);
                    }
                });


            } catch (IOException e) {
                Log.e("An error occurred: ", e.toString());
                request.setPageToken(null);
            }
        } while (request.getPageToken() != null &&
                request.getPageToken().length() > 0);

        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CODE_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                handleSignInResult(data);
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void openFileFromFilePicker(Uri uri) {

        if (mDriveServiceHelper != null) {
            Log.d(TAG, "Opening " + uri.getPath());

            mDriveServiceHelper.openFileUsingStorageAccessFramework(getContentResolver(), uri)
                    .addOnSuccessListener(nameAndContent -> {
                        String name = nameAndContent.first;
                        String content = nameAndContent.second;

                        Log.e("Name.......", name);
                        Log.e("content.......", content);

                        // Files opened through SAF cannot be modified.
//                        setReadOnlyMode();
                    })
                    .addOnFailureListener(exception ->
                            Log.e(TAG, "Unable to open file from picker.", exception));
        }

    }

    private void handleSignInResult(Intent result) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(googleAccount -> {
                    Log.d(TAG, "Signed in as " + googleAccount.getEmail());

                    // Use the authenticated account to sign in to the Drive service.
                    GoogleAccountCredential credential =
                            GoogleAccountCredential.usingOAuth2(
                                    this, Collections.singleton(DriveScopes.DRIVE_FILE));
                    credential.setSelectedAccount(googleAccount.getAccount());
                    googleDriveService =
                            new Drive.Builder(
                                    AndroidHttp.newCompatibleTransport(),
                                    new GsonFactory(),
                                    credential)
                                    .setApplicationName("Scriptively")
                                    .build();

                    // The DriveServiceHelper encapsulates all REST API and SAF functionality.
                    // Its instantiation is required before handling any onClick actions.
                    mDriveServiceHelper = new DriveServiceHelper(googleDriveService);

                    if (mDriveServiceHelper != null) {
                        Log.d(TAG, "Opening file picker.");
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    retrieveAllFiles(googleDriveService);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
//                        Intent pickerIntent = mDriveServiceHelper.createFilePickerIntent();
//                        pickerIntent.setType("*/*");
//                        // The result of the SAF Intent is handled in onActivityResult.
//                        startActivityForResult(pickerIntent, REQUEST_CODE_OPEN_DOCUMENT);
                            }

                        }).start();
                    }


                })
                .addOnFailureListener(exception -> Log.e(TAG, "Unable to sign in.", exception));
    }

}
