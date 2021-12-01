package com.scriptively.app.Database;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.scriptively.app.DatabaseModel.FolderDB;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FolderDao_Impl implements FolderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FolderDB> __insertionAdapterOfFolderDB;

  private final EntityDeletionOrUpdateAdapter<FolderDB> __updateAdapterOfFolderDB;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFolderInternetFlag;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFolderTitle;

  public FolderDao_Impl(RoomDatabase __db) {
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
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM FolderDB Where folder_key Like ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateFolderInternetFlag = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE folderdb SET internet_flag = ?, folder_Id = ? where folder_key Like ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateFolderTitle = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE folderdb SET name = ?, editFlag = ? where folder_key Like ?";
        return _query;
      }
    };
  }

  @Override
  public long insertFolder(final FolderDB folder) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfFolderDB.insertAndReturnId(folder);
      __db.setTransactionSuccessful();
      return _result;
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
  public int delete(final int updateKey) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, updateKey);
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete.release(_stmt);
    }
  }

  @Override
  public int updateFolderInternetFlag(final int value, final int folderId, final int updateKey) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFolderInternetFlag.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, value);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, folderId);
    _argIndex = 3;
    _stmt.bindLong(_argIndex, updateKey);
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateFolderInternetFlag.release(_stmt);
    }
  }

  @Override
  public int updateFolderTitle(final String value, final int editValue, final int updateKey) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFolderTitle.acquire();
    int _argIndex = 1;
    if (value == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, value);
    }
    _argIndex = 2;
    _stmt.bindLong(_argIndex, editValue);
    _argIndex = 3;
    _stmt.bindLong(_argIndex, updateKey);
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateFolderTitle.release(_stmt);
    }
  }

  @Override
  public List<FolderDB> loadAllFolders() {
    final String _sql = "SELECT * FROM folderdb order by name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfFolderKey = CursorUtil.getColumnIndexOrThrow(_cursor, "folder_key");
      final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folder_Id");
      final int _cursorIndexOfInternetFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "internet_flag");
      final int _cursorIndexOfEditFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "editFlag");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfFoldercreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modified_at");
      final List<FolderDB> _result = new ArrayList<FolderDB>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final FolderDB _item;
        _item = new FolderDB();
        _item.folder_key = _cursor.getInt(_cursorIndexOfFolderKey);
        _item.folder_Id = _cursor.getString(_cursorIndexOfFolderId);
        _item.internet_flag = _cursor.getInt(_cursorIndexOfInternetFlag);
        _item.editFlag = _cursor.getInt(_cursorIndexOfEditFlag);
        _item.userId = _cursor.getString(_cursorIndexOfUserId);
        _item.name = _cursor.getString(_cursorIndexOfName);
        _item.foldercreatedAt = _cursor.getString(_cursorIndexOfFoldercreatedAt);
        _item.modified_at = _cursor.getString(_cursorIndexOfModifiedAt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public FolderDB lastPrimaryKey() {
    final String _sql = "SELECT * FROM folderdb ORDER BY folder_key DESC  LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfFolderKey = CursorUtil.getColumnIndexOrThrow(_cursor, "folder_key");
      final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folder_Id");
      final int _cursorIndexOfInternetFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "internet_flag");
      final int _cursorIndexOfEditFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "editFlag");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfFoldercreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modified_at");
      final FolderDB _result;
      if(_cursor.moveToFirst()) {
        _result = new FolderDB();
        _result.folder_key = _cursor.getInt(_cursorIndexOfFolderKey);
        _result.folder_Id = _cursor.getString(_cursorIndexOfFolderId);
        _result.internet_flag = _cursor.getInt(_cursorIndexOfInternetFlag);
        _result.editFlag = _cursor.getInt(_cursorIndexOfEditFlag);
        _result.userId = _cursor.getString(_cursorIndexOfUserId);
        _result.name = _cursor.getString(_cursorIndexOfName);
        _result.foldercreatedAt = _cursor.getString(_cursorIndexOfFoldercreatedAt);
        _result.modified_at = _cursor.getString(_cursorIndexOfModifiedAt);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<FolderDB> editFolderBased() {
    final String _sql = "SELECT * FROM folderdb WHERE editFlag Like 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfFolderKey = CursorUtil.getColumnIndexOrThrow(_cursor, "folder_key");
      final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folder_Id");
      final int _cursorIndexOfInternetFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "internet_flag");
      final int _cursorIndexOfEditFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "editFlag");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfFoldercreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modified_at");
      final List<FolderDB> _result = new ArrayList<FolderDB>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final FolderDB _item;
        _item = new FolderDB();
        _item.folder_key = _cursor.getInt(_cursorIndexOfFolderKey);
        _item.folder_Id = _cursor.getString(_cursorIndexOfFolderId);
        _item.internet_flag = _cursor.getInt(_cursorIndexOfInternetFlag);
        _item.editFlag = _cursor.getInt(_cursorIndexOfEditFlag);
        _item.userId = _cursor.getString(_cursorIndexOfUserId);
        _item.name = _cursor.getString(_cursorIndexOfName);
        _item.foldercreatedAt = _cursor.getString(_cursorIndexOfFoldercreatedAt);
        _item.modified_at = _cursor.getString(_cursorIndexOfModifiedAt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<FolderDB> internetBased() {
    final String _sql = "SELECT * FROM folderdb WHERE internet_flag Like 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfFolderKey = CursorUtil.getColumnIndexOrThrow(_cursor, "folder_key");
      final int _cursorIndexOfFolderId = CursorUtil.getColumnIndexOrThrow(_cursor, "folder_Id");
      final int _cursorIndexOfInternetFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "internet_flag");
      final int _cursorIndexOfEditFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "editFlag");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfFoldercreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modified_at");
      final List<FolderDB> _result = new ArrayList<FolderDB>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final FolderDB _item;
        _item = new FolderDB();
        _item.folder_key = _cursor.getInt(_cursorIndexOfFolderKey);
        _item.folder_Id = _cursor.getString(_cursorIndexOfFolderId);
        _item.internet_flag = _cursor.getInt(_cursorIndexOfInternetFlag);
        _item.editFlag = _cursor.getInt(_cursorIndexOfEditFlag);
        _item.userId = _cursor.getString(_cursorIndexOfUserId);
        _item.name = _cursor.getString(_cursorIndexOfName);
        _item.foldercreatedAt = _cursor.getString(_cursorIndexOfFoldercreatedAt);
        _item.modified_at = _cursor.getString(_cursorIndexOfModifiedAt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
