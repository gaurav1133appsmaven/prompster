package com.scriptively.app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.core.DbxException;
import com.dropbox.core.android.AuthActivity;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.scriptively.app.Adapter.FilesAdapter;
import com.scriptively.app.R;
import com.scriptively.app.Utils.ConnectToDB;
import com.scriptively.app.Utils.DownloadFileTask;
import com.scriptively.app.Utils.DropboxActivity;
import com.scriptively.app.Utils.DropboxClientFactory;
import com.scriptively.app.Utils.GetCurrentAccountTask;
import com.scriptively.app.Utils.ListFolderTask;
import com.scriptively.app.Utils.Shared_PrefrencePrompster;
import com.scriptively.app.Utils.UploadFileTask;
import com.scriptively.app.Utils.Util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DropBox extends DropboxActivity {
    public static final String ACCESS_TOKEN="I6iOGffC4LoAAAAAAAAAAXt_3v-59vUEZ9ZebrFdZX_XSYFG9krnhdEwlAH_vcjp";
    private static final String TAG = DropBox.class.getName();

    public final static String EXTRA_PATH = "FilesActivity_Path";
    private static final int PICKFILE_REQUEST_CODE = 1;

    private String mPath, title, description, userId, title_tag;
    private FilesAdapter mFilesAdapter;
    private FileMetadata mSelectedFile;
    TextView log_off_dropbox, tv_reset, tv_title_main;
    RecyclerView recyclerView;
    Shared_PrefrencePrompster shared_prefrencePrompster;
    SharedPreferences prefs;
    Intent intent;
    SharedPreferences.Editor editor;
    private DbxClientV2 mClient=null;


    public static Intent getIntent(Context context, String path) {
        Intent filesIntent = new Intent(context, DropBox.class);
        filesIntent.putExtra(DropBox.EXTRA_PATH, path);
        return filesIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String path = getIntent().getStringExtra(EXTRA_PATH);
        mPath = path == null ? "" : path;
        prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
        editor = prefs.edit();

        setContentView(R.layout.activity_drop_box);
        DropboxActivity.startOAuth2Authentication(this, getString(R.string.app_key),  Arrays.asList("account_info.read", "files.content.write", "files.content.read"));
        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(this);
        userId = shared_prefrencePrompster.getUserid().toString();

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        intent = getIntent();
        title_tag = intent.getStringExtra("drive");
        log_off_dropbox = findViewById(R.id.log_off_dropbox);
        tv_title_main = findViewById(R.id.tv_title_main);
        tv_reset = findViewById(R.id.tv_reset);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_dropbox);
//        if (title_tag.equals("2")){
//            tv_title_main.setText("DropBox");
//        }
        tv_title_main.setText("DropBox");
        log_off_dropbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(mClient!=null)
{
    try {
        mClient.auth().tokenRevoke();
        DropboxClientFactory.clearClient();
        AuthActivity.result=null;

    } catch (Exception e) {
     Log.e("exceptionCaught", e.getMessage());
    }
}


//                if (Util.checkDropbox) {
//
//                    startActivity(new Intent(DropBox.this, Navigation.class));
//                    finish();
//                }
            }
        });

        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                startActivity(getIntent());
            }
        });

        //init picaso client

        mFilesAdapter = new FilesAdapter(new FilesAdapter.Callback() {
            @Override
            public void onFolderClicked(FolderMetadata folder) {
                startActivity(DropBox.getIntent(DropBox.this, folder.getPathLower()));
            }

            @Override
            public void onFileClicked(final FileMetadata file) {
                mSelectedFile = file;
                performWithPermissions(FileAction.DOWNLOAD);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
          //  ListFolderResult result = client.files().listFolder("");
            new ConnectToDB(this, ACCESS_TOKEN, new ConnectToDB.Callback() {
                @Override
                public void onLoginComplete(FullAccount result, DbxClientV2 client) {

mClient=client;
                    Log.e(TAG, "Successdata."+client.account().toString());
                   new  ListFolderTask(client, DropBox.this);


                }

                @Override
                public void onError(Exception e) {
                    Log.e(TAG, "Failed to login.", e);
                    Toast.makeText(DropBox.this,
                            e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }).execute();
        } catch (Exception e) {
            Log.e("exceptionCaught", "" + e.getMessage());
            e.printStackTrace();
        }

        recyclerView.setAdapter(mFilesAdapter);

        mSelectedFile = null;
    }

    private void launchFilePicker() {
        // Launch intent to pick file for upload
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, PICKFILE_REQUEST_CODE);
    }

    public void getDropboxData(ListFolderResult result)
    {
        Log.e("allEntries", "ondata loaded");
        Log.e("allEntries", ""+result.getEntries().size());
        mFilesAdapter.setFiles(result.getEntries());
        while (true) {
            for (Metadata metadata : result.getEntries()) {
                Log.e("PathData",""+metadata.getPathLower());
                System.out.println(metadata.getPathLower());
            }

            if (!result.getHasMore()) {
                break;
            }
            DropboxActivity.USE_SLT=false;

            //  result = client.files().listFolderContinue(resultData.getCursor());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICKFILE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                loadData();
                // This is the result of a call to launchFilePicker
              //  uploadFile(data.getData().toString());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int actionCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        FileAction action = FileAction.fromCode(actionCode);

        boolean granted = true;
        for (int i = 0; i < grantResults.length; ++i) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                Log.w(TAG, "User denied " + permissions[i] +
                        " permission to perform file action: " + action);
                granted = false;
                break;
            }
        }

        if (granted) {
            performAction(action);
        } else {
            switch (action) {
                case UPLOAD:
                    Toast.makeText(this,
                            "Can't upload file: read access denied. " +
                                    "Please grant storage permissions to use this functionality.",
                            Toast.LENGTH_LONG)
                            .show();
                    break;
                case DOWNLOAD:
                    Toast.makeText(this,
                            "Can't download file: write access denied. " +
                                    "Please grant storage permissions to use this functionality.",
                            Toast.LENGTH_LONG)
                            .show();
                    break;
            }
        }
    }

    private void performAction(FileAction action) {
        switch (action) {
            case UPLOAD:
                launchFilePicker();
                break;
            case DOWNLOAD:
                if (mSelectedFile != null) {
                    downloadFile(mSelectedFile);
                } else {
                    Log.e(TAG, "No file selected to download.");
                }
                break;
            default:
                Log.e(TAG, "Can't perform unhandled file action: " + action);
        }
    }

    List<Metadata> mFiles = new ArrayList<>();

    @Override
    protected void loadData() {
        Log.e("loaddata", "data is loading");

        new GetCurrentAccountTask(DropboxClientFactory.getClient(), new GetCurrentAccountTask.Callback() {
            @Override
            public void onComplete(FullAccount result) {
                Log.e("completed data", result.getEmail());

//
//                ((TextView) findViewById(R.id.email_text)).setText(result.getEmail());
//                ((TextView) findViewById(R.id.name_text)).setText(result.getName().getDisplayName());
//                ((TextView) findViewById(R.id.type_text)).setText(result.getAccountType().name());
            }

            @Override
            public void onError(Exception e) {
                Log.e(getClass().getName(), "Failed to get account details.", e);
            }
        }).execute();

//        final ProgressDialog dialog = new ProgressDialog(this);
//        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        dialog.setCancelable(false);
//        dialog.setMessage("Loading");
//        dialog.show();

        new ListFolderTask(DropboxClientFactory.getClient(),this).execute(mPath);

    }

    private void downloadFile(FileMetadata file) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Loading");
        dialog.show();

        new DownloadFileTask(DropBox.this, DropboxClientFactory.getClient(), new DownloadFileTask.Callback() {
            @Override
            public void onDownloadComplete(File result) {
                dialog.dismiss();

                if (result != null) {
//                    viewFileInExternalApp(result);

                    Log.e("Data_PDf", result.getName());

                    title = result.getName();

                    Log.e("Check_pdf", result.getAbsolutePath());

                    if (title.contains(".pdf")) {
                        ReadPdfFile(result.getAbsolutePath());
                    } else if (title.contains(".doc")) {
                        readTextFile(result);
                    } else if (title.contains(".docx")) {
                        readTextFile(result);
                    } else if (title.contains(".txt")) {
                        readTextFile(result);
                    }
                    Script_Toolbar script_toolbar = new Script_Toolbar();
//                    script_toolbar.AddScriptapi(userid,new File(actualfilepath).getName(),parsedText);

                }
            }

            @Override
            public void onError(Exception e) {
                dialog.dismiss();

                Log.e(TAG, "Failed to download file.", e);
                Toast.makeText(DropBox.this,
                        "An error has occurred",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }).execute(file);

    }

    private void readTextFile(File absolutePath) {

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
            Dialog dialogg = new Dialog(DropBox.this);

            dialogg.setContentView(R.layout.email_empty);
            dialogg.setCanceledOnTouchOutside(false);
            dialogg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView tv_scriptDelete = dialogg.findViewById(R.id.tv_path);
            TextView tv_okay = dialogg.findViewById(R.id.tv_ok);
            tv_scriptDelete.setText("Successfully imported file" + " " + absolutePath.getName());

            tv_okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogg.dismiss();
                    Intent intent = new Intent(DropBox.this, Navigation.class);
                    startActivity(intent);
                    finish();
                }
            });

            dialogg.show();

            String[] parts = absolutePath.getName().split("\\."); // escape .
            String part1 = parts[0];
            String part2 = parts[1];

            Log.e("part1", part1);
            Script_Toolbar script_toolbar = new Script_Toolbar();
            script_toolbar.AddScriptapi(userId, part1, text.toString(), "new", "");


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
            Dialog dialogg = new Dialog(DropBox.this);

            dialogg.setContentView(R.layout.email_empty);
            dialogg.setCanceledOnTouchOutside(false);
            dialogg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView tv_scriptDelete = dialogg.findViewById(R.id.tv_path);
            TextView tv_okay = dialogg.findViewById(R.id.tv_ok);
            tv_scriptDelete.setText("Successfully imported file" + " " + new File(actualfilepath).getName());

            tv_okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogg.dismiss();
                    Intent intent = new Intent(DropBox.this, Navigation.class);
                    startActivity(intent);
                    finish();
                }
            });

            dialogg.show();

            String[] parts = new File(actualfilepath).getName().split("\\."); // escape .
            String part1 = parts[0];
            String part2 = parts[1];

            Log.e("part1", part1);

            Script_Toolbar script_toolbar = new Script_Toolbar();
            script_toolbar.AddScriptapi(userId, part1, parsedText, "new", "");


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void viewFileInExternalApp(File result) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext = result.getName().substring(result.getName().indexOf(".") + 1);
        String type = mime.getMimeTypeFromExtension(ext);

        intent.setDataAndType(Uri.fromFile(result), type);

        // Check for a handler first to avoid a crash
        PackageManager manager = getPackageManager();
        List<ResolveInfo> resolveInfo = manager.queryIntentActivities(intent, 0);
        if (resolveInfo.size() > 0) {
            startActivity(intent);
        }
    }

//    private void uploadFile(String fileUri) {
//        final ProgressDialog dialog = new ProgressDialog(this);
//        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        dialog.setCancelable(false);
//        dialog.setMessage("Uploading");
//        dialog.show();
//
//        new UploadFileTask(this, DropboxClientFactory.getClient(), new UploadFileTask.Callback() {
//            @Override
//            public void onUploadComplete(FileMetadata result) {
//                dialog.dismiss();
//
//                String message = result.getName() + " size " + result.getSize() + " modified " +
//                        DateFormat.getDateTimeInstance().format(result.getClientModified());
//                Toast.makeText(DropBox.this, message, Toast.LENGTH_SHORT)
//                        .show();
//
//                // Reload the folder
//                loadData();
//            }
//
//            @Override
//            public void onError(Exception e) {
//                dialog.dismiss();
//
//                Log.e(TAG, "Failed to upload file.", e);
//                Toast.makeText(DropBox.this,
//                        "An error has occurred",
//                        Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }).execute(fileUri, mPath);
//    }

    private void performWithPermissions(final FileAction action) {
        if (hasPermissionsForAction(action)) {
            performAction(action);
            return;
        }

        if (shouldDisplayRationaleForAction(action)) {
            new AlertDialog.Builder(this)
                    .setMessage("This app requires storage access to download and upload files.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissionsForAction(action);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        } else {
            requestPermissionsForAction(action);
        }
    }

    private boolean hasPermissionsForAction(FileAction action) {
        for (String permission : action.getPermissions()) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    private boolean shouldDisplayRationaleForAction(FileAction action) {
        for (String permission : action.getPermissions()) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true;
            }
        }
        return false;
    }

    private void requestPermissionsForAction(FileAction action) {
        ActivityCompat.requestPermissions(
                this,
                action.getPermissions(),
                action.getCode()
        );
    }

    private enum FileAction {
        DOWNLOAD(Manifest.permission.WRITE_EXTERNAL_STORAGE),
        UPLOAD(Manifest.permission.READ_EXTERNAL_STORAGE);

        private static final FileAction[] values = values();

        private final String[] permissions;

        FileAction(String... permissions) {
            this.permissions = permissions;
        }

        public int getCode() {
            return ordinal();
        }

        public String[] getPermissions() {
            return permissions;
        }

        public static FileAction fromCode(int code) {
            if (code < 0 || code >= values.length) {
                throw new IllegalArgumentException("Invalid FileAction code: " + code);
            }
            return values[code];
        }
    }
}
