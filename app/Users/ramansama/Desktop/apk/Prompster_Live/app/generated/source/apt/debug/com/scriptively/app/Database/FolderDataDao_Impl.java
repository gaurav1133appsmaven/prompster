package com.scriptively.app.Database;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.scriptively.app.DatabaseModel.FolderDB;
import com.scriptively.app.DatabaseModel.FolderDataDb;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FolderDataDao_Impl implements FolderDataDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FolderDB> __insertionAdapterOfFolderDB;

  private final EntityDeletionOrUpdateAdapter<FolderDB> __deletionAdapterOfFolderDB;

  private final EntityDeletionOrUpdateAdapter<FolderDB> __updateAdapterOfFolderDB;

  public FolderDataDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFolderDB = new EntityInsertionAdapter<FolderDB>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `folderdb` (`folder_key`,`folder_Id`,`internet_flag`,`editFlag`,`userId`,`name`,`createdAt`,`modified_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FolderDB value) {
        stmt.bindLong(1, value.folder_key);
        if (value.folder_Id == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.folder_Id);
        }
        stmt.bindLong(3, value.internet_flag);
        stmt.bindLong(4, value.editFlag);
        if (value.userId == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.userId);
        }
        if (value.name == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.name);
        }
        if (value.foldercreatedAt == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.foldercreatedAt);
        }
        if (value.modified_at == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.modified_at);
        }
      }
    };
    this.__deletionAdapterOfFolderDB = new EntityDeletionOrUpdateAdapter<FolderDB>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `folderdb` WHERE `folder_key` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FolderDB value) {
        stmt.bindLong(1, value.folder_key);
      }
    };
    this.__updateAdapterOfFolderDB = new EntityDeletionOrUpdateAdapter<FolderDB>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `folderdb` SET `folder_key` = ?,`folder_Id` = ?,`internet_flag` = ?,`editFlag` = ?,`userId` = ?,`name` = ?,`createdAt` = ?,`modified_at` = ? WHERE `folder_key` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FolderDB value) {
        stmt.bindLong(1, value.folder_key);
        if (value.folder_Id == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.folder_Id);
        }
        stmt.bindLong(3, value.internet_flag);
        stmt.bindLong(4, value.editFlag);
        if (value.userId == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.userId);
        }
        if (value.name == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.name);
        }
        if (value.foldercreatedAt == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.foldercreatedAt);
        }
        if (value.modified_at == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.modified_at);
        }
        stmt.bindLong(9, value.folder_key);
      }
    };
  }

  @Override
  public void insertFolder(final FolderDB folder) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfFolderDB.insert(folder);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final FolderDB folder) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfFolderDB.handle(folder);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateFolder(final FolderDB folder) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfFolderDB.handle(folder);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<FolderDataDb> loadAllFolderData() {
    final String _sql = "SELECT * FROM folder_data";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfCreateFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "createFlag");
      final int _cursorIndexOfIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "index");
      final int _cursorIndexOfFolderName = CursorUtil.getColumnIndexOrThrow(_cursor, "folderName");
      final List<FolderDataDb> _result = new ArrayList<FolderDataDb>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final FolderDataDb _item;
        _item = new FolderDataDb();
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCreateFlag);
        _item.createFlag = _tmp != 0;
        _item.index = _cursor.getInt(_cursorIndexOfIndex);
        _item.folderName = _cursor.getString(_cursorIndexOfFolderName);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
