package com.scriptively.app.Utils;

import android.os.AsyncTask;
import android.util.Log;
import com.scriptively.app.Activity.DropBox;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;

/**
 * Async task to list items in a folder
 */
public class ListFolderTask extends AsyncTask<String, Void, ListFolderResult> {

    private final DbxClientV2 mDbxClient;
    private final DropBox mCallback;
    private Exception mException;

    public interface Callback {
        void onDataLoaded(ListFolderResult result);

        void onError(Exception e);
    }

    public ListFolderTask(DbxClientV2 dbxClient, DropBox callback) {
        mDbxClient = dbxClient;
        mCallback = callback;
    }

    @Override
    protected void onPostExecute(ListFolderResult result) {
        super.onPostExecute(result);

        if (mException != null) {
          //  mCallback.onError(mException);
        } else {
            mCallback.getDropboxData(result);
        }
    }

    @Override
    protected ListFolderResult doInBackground(String... params) {
        try {
            return mDbxClient.files().listFolder("");
        } catch (DbxException e) {
            mException = e;
            Log.e("exceptionCaught", ""+e.getMessage());
        }

        return null;
    }
}
