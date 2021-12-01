package com.scriptively.app.Database;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.scriptively.app.DatabaseModel.StoryBoardDB;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class StoryBoardDao_Impl implements StoryBoardDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StoryBoardDB> __insertionAdapterOfStoryBoardDB;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final SharedSQLiteStatement __preparedStmtOfUpdateInternetFlag;

  private final SharedSQLiteStatement __preparedStmtOfUpdateStoryBoard;

  public StoryBoardDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStoryBoardDB = new EntityInsertionAdapter<StoryBoardDB>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `storyboard` (`script_id`,`storyKey`,`scriptKey`,`StoryboardId`,`storyBoardinternetFlag`,`storyBoardeditFlag`,`Storyboard_att_text`,`storyboard_name`,`storyboardTextSize`) VALUES (?,nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, StoryBoardDB value) {
        if (value.script_id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.script_id);
        }
        stmt.bindLong(2, value.storyKey);
        stmt.bindLong(3, value.scriptKey);
        if (value.StoryboardId == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.StoryboardId);
        }
        stmt.bindLong(5, value.storyBoardinternetFlag);
        stmt.bindLong(6, value.storyBoardeditFlag);
        if (value.Storyboard_att_text == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.Storyboard_att_text);
        }
        if (value.storyboard_name == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.storyboard_name);
        }
        if (value.storyboardTextSize == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.storyboardTextSize);
        }
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM storyboard Where storyKey Like ? and scriptKey Like ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateInternetFlag = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE storyboard SET storyBoardinternetFlag = ? where storyKey Like ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateStoryBoard = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE storyboard SET storyboard_name = ?, Storyboard_att_text = ? ,storyboardeditFlag = ? where storyKey Like ?";
        return _query;
      }
    };
  }

  @Override
  public long insertStoryBoard(final StoryBoardDB story) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfStoryBoardDB.insertAndReturnId(story);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Long> insertAllStoryBoard(final List<StoryBoardDB> story) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      List<Long> _result = __insertionAdapterOfStoryBoardDB.insertAndReturnIdsList(story);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int delete(final int updateKey, final int scriptKey) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, updateKey);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, scriptKey);
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
  public int updateInternetFlag(final int value, final int updateKey) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateInternetFlag.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, value);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, updateKey);
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateInternetFlag.release(_stmt);
    }
  }

  @Override
  public int updateStoryBoard(final String value, final String value_desc, final int editValue,
      final int updateKey) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateStoryBoard.acquire();
    int _argIndex = 1;
    if (value == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, value);
    }
    _argIndex = 2;
    if (value_desc == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, value_desc);
    }
    _argIndex = 3;
    _stmt.bindLong(_argIndex, editValue);
    _argIndex = 4;
    _stmt.bindLong(_argIndex, updateKey);
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateStoryBoard.release(_stmt);
    }
  }

  @Override
  public List<StoryBoardDB> getAll(final int scriptKey) {
    final String _sql = "SELECT * FROM storyboard WHERE scriptKey Like ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, scriptKey);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfScriptId = CursorUtil.getColumnIndexOrThrow(_cursor, "script_id");
      final int _cursorIndexOfStoryKey = CursorUtil.getColumnIndexOrThrow(_cursor, "storyKey");
      final int _cursorIndexOfScriptKey = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptKey");
      final int _cursorIndexOfStoryboardId = CursorUtil.getColumnIndexOrThrow(_cursor, "StoryboardId");
      final int _cursorIndexOfStoryBoardinternetFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "storyBoardinternetFlag");
      final int _cursorIndexOfStoryBoardeditFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "storyBoardeditFlag");
      final int _cursorIndexOfStoryboardAttText = CursorUtil.getColumnIndexOrThrow(_cursor, "Storyboard_att_text");
      final int _cursorIndexOfStoryboardName = CursorUtil.getColumnIndexOrThrow(_cursor, "storyboard_name");
      final int _cursorIndexOfStoryboardTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "storyboardTextSize");
      final List<StoryBoardDB> _result = new ArrayList<StoryBoardDB>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final StoryBoardDB _item;
        _item = new StoryBoardDB();
        _item.script_id = _cursor.getString(_cursorIndexOfScriptId);
        _item.storyKey = _cursor.getInt(_cursorIndexOfStoryKey);
        _item.scriptKey = _cursor.getInt(_cursorIndexOfScriptKey);
        _item.StoryboardId = _cursor.getString(_cursorIndexOfStoryboardId);
        _item.storyBoardinternetFlag = _cursor.getInt(_cursorIndexOfStoryBoardinternetFlag);
        _item.storyBoardeditFlag = _cursor.getInt(_cursorIndexOfStoryBoardeditFlag);
        _item.Storyboard_att_text = _cursor.getString(_cursorIndexOfStoryboardAttText);
        _item.storyboard_name = _cursor.getString(_cursorIndexOfStoryboardName);
        _item.storyboardTextSize = _cursor.getString(_cursorIndexOfStoryboardTextSize);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<StoryBoardDB> internetBased() {
    final String _sql = "SELECT * FROM storyboard WHERE storyBoardinternetFlag Like 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfScriptId = CursorUtil.getColumnIndexOrThrow(_cursor, "script_id");
      final int _cursorIndexOfStoryKey = CursorUtil.getColumnIndexOrThrow(_cursor, "storyKey");
      final int _cursorIndexOfScriptKey = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptKey");
      final int _cursorIndexOfStoryboardId = CursorUtil.getColumnIndexOrThrow(_cursor, "StoryboardId");
      final int _cursorIndexOfStoryBoardinternetFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "storyBoardinternetFlag");
      final int _cursorIndexOfStoryBoardeditFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "storyBoardeditFlag");
      final int _cursorIndexOfStoryboardAttText = CursorUtil.getColumnIndexOrThrow(_cursor, "Storyboard_att_text");
      final int _cursorIndexOfStoryboardName = CursorUtil.getColumnIndexOrThrow(_cursor, "storyboard_name");
      final int _cursorIndexOfStoryboardTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "storyboardTextSize");
      final List<StoryBoardDB> _result = new ArrayList<StoryBoardDB>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final StoryBoardDB _item;
        _item = new StoryBoardDB();
        _item.script_id = _cursor.getString(_cursorIndexOfScriptId);
        _item.storyKey = _cursor.getInt(_cursorIndexOfStoryKey);
        _item.scriptKey = _cursor.getInt(_cursorIndexOfScriptKey);
        _item.StoryboardId = _cursor.getString(_cursorIndexOfStoryboardId);
        _item.storyBoardinternetFlag = _cursor.getInt(_cursorIndexOfStoryBoardinternetFlag);
        _item.storyBoardeditFlag = _cursor.getInt(_cursorIndexOfStoryBoardeditFlag);
        _item.Storyboard_att_text = _cursor.getString(_cursorIndexOfStoryboardAttText);
        _item.storyboard_name = _cursor.getString(_cursorIndexOfStoryboardName);
        _item.storyboardTextSize = _cursor.getString(_cursorIndexOfStoryboardTextSize);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<StoryBoardDB> editBased() {
    final String _sql = "SELECT * FROM storyboard WHERE storyBoardeditFlag Like 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfScriptId = CursorUtil.getColumnIndexOrThrow(_cursor, "script_id");
      final int _cursorIndexOfStoryKey = CursorUtil.getColumnIndexOrThrow(_cursor, "storyKey");
      final int _cursorIndexOfScriptKey = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptKey");
      final int _cursorIndexOfStoryboardId = CursorUtil.getColumnIndexOrThrow(_cursor, "StoryboardId");
      final int _cursorIndexOfStoryBoardinternetFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "storyBoardinternetFlag");
      final int _cursorIndexOfStoryBoardeditFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "storyBoardeditFlag");
      final int _cursorIndexOfStoryboardAttText = CursorUtil.getColumnIndexOrThrow(_cursor, "Storyboard_att_text");
      final int _cursorIndexOfStoryboardName = CursorUtil.getColumnIndexOrThrow(_cursor, "storyboard_name");
      final int _cursorIndexOfStoryboardTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "storyboardTextSize");
      final List<StoryBoardDB> _result = new ArrayList<StoryBoardDB>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final StoryBoardDB _item;
        _item = new StoryBoardDB();
        _item.script_id = _cursor.getString(_cursorIndexOfScriptId);
        _item.storyKey = _cursor.getInt(_cursorIndexOfStoryKey);
        _item.scriptKey = _cursor.getInt(_cursorIndexOfScriptKey);
        _item.StoryboardId = _cursor.getString(_cursorIndexOfStoryboardId);
        _item.storyBoardinternetFlag = _cursor.getInt(_cursorIndexOfStoryBoardinternetFlag);
        _item.storyBoardeditFlag = _cursor.getInt(_cursorIndexOfStoryBoardeditFlag);
        _item.Storyboard_att_text = _cursor.getString(_cursorIndexOfStoryboardAttText);
        _item.storyboard_name = _cursor.getString(_cursorIndexOfStoryboardName);
        _item.storyboardTextSize = _cursor.getString(_cursorIndexOfStoryboardTextSize);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
