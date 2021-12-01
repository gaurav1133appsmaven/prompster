package com.scriptively.app.Database;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.scriptively.app.DatabaseModel.ScriptDB;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ScriptDBDao_Impl implements ScriptDBDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ScriptDB> __insertionAdapterOfScriptDB;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final SharedSQLiteStatement __preparedStmtOfUpdate;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTitle;

  private final SharedSQLiteStatement __preparedStmtOfUpdateScript;

  public ScriptDBDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfScriptDB = new EntityInsertionAdapter<ScriptDB>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `scriptdata` (`scriptDBkey`,`scriptId`,`internetFlag`,`editFlag`,`getScrPromptTextSize`,`scr_txt`,`scriptDescription`,`Scripttitle`,`ScriptcreatedAt`,`scriptEditTextSize`,`promptSpeed`,`scriptAlignment`,`scriptColor`,`scriptuser_Id`,`finalScrText`,`scriptTextSize`,`promptTextSize`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ScriptDB value) {
        stmt.bindLong(1, value.key);
        if (value.scriptId == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.scriptId);
        }
        stmt.bindLong(3, value.internetFlag);
        stmt.bindLong(4, value.editFlag);
        if (value.getScrPromptTextSize == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getScrPromptTextSize);
        }
        if (value.scr_txt == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.scr_txt);
        }
        if (value.scriptDescription == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.scriptDescription);
        }
        if (value.Scripttitle == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.Scripttitle);
        }
        if (value.ScriptcreatedAt == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.ScriptcreatedAt);
        }
        if (value.scriptEditTextSize == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.scriptEditTextSize);
        }
        if (value.promptSpeed == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.promptSpeed);
        }
        if (value.scriptAlignment == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.scriptAlignment);
        }
        if (value.scriptColor == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.scriptColor);
        }
        if (value.scriptuser_Id == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.scriptuser_Id);
        }
        if (value.finalScrText == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.finalScrText);
        }
        if (value.scriptTextSize == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, value.scriptTextSize);
        }
        if (value.promptTextSize == null) {
          stmt.bindNull(17);
        } else {
          stmt.bindString(17, value.promptTextSize);
        }
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM scriptdata Where scriptDBkey Like ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdate = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE SCRIPTDATA SET internetFlag = ? , scriptId = ? where scriptDBkey Like ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateTitle = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE SCRIPTDATA SET Scripttitle = ?, editFlag = ? where scriptDBkey Like ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateScript = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE SCRIPTDATA SET Scripttitle = ?, editFlag = ? ,scriptDescription = ?, scriptAlignment = ?, scriptColor = ?, scriptTextSize = ?,promptTextSize = ?,promptSpeed = ? where scriptDBkey Like ?";
        return _query;
      }
    };
  }

  @Override
  public long insertScript(final ScriptDB script) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfScriptDB.insertAndReturnId(script);
      __db.setTransactionSuccessful();
      return _result;
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
  public int update(final int value, final int updateKey, final String scriptId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, value);
    _argIndex = 2;
    if (scriptId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, scriptId);
    }
    _argIndex = 3;
    _stmt.bindLong(_argIndex, updateKey);
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate.release(_stmt);
    }
  }

  @Override
  public int updateTitle(final String value, final int editValue, final int updateKey) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTitle.acquire();
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
      __preparedStmtOfUpdateTitle.release(_stmt);
    }
  }

  @Override
  public int updateScript(final String value, final int value_desc, final String scriptDescription,
      final String scriptAlignment, final String scriptColor, final String scriptTextSize,
      final String promptTextSize, final int updateKey, final String promptSpeedValue) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateScript.acquire();
    int _argIndex = 1;
    if (value == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, value);
    }
    _argIndex = 2;
    _stmt.bindLong(_argIndex, value_desc);
    _argIndex = 3;
    if (scriptDescription == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, scriptDescription);
    }
    _argIndex = 4;
    if (scriptAlignment == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, scriptAlignment);
    }
    _argIndex = 5;
    if (scriptColor == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, scriptColor);
    }
    _argIndex = 6;
    if (scriptTextSize == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, scriptTextSize);
    }
    _argIndex = 7;
    if (promptTextSize == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, promptTextSize);
    }
    _argIndex = 8;
    if (promptSpeedValue == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, promptSpeedValue);
    }
    _argIndex = 9;
    _stmt.bindLong(_argIndex, updateKey);
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateScript.release(_stmt);
    }
  }

  @Override
  public List<ScriptDB> loadAddData() {
    final String _sql = "SELECT * FROM SCRIPTDATA order by Scripttitle";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfKey = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptDBkey");
      final int _cursorIndexOfScriptId = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptId");
      final int _cursorIndexOfInternetFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "internetFlag");
      final int _cursorIndexOfEditFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "editFlag");
      final int _cursorIndexOfGetScrPromptTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "getScrPromptTextSize");
      final int _cursorIndexOfScrTxt = CursorUtil.getColumnIndexOrThrow(_cursor, "scr_txt");
      final int _cursorIndexOfScriptDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptDescription");
      final int _cursorIndexOfScripttitle = CursorUtil.getColumnIndexOrThrow(_cursor, "Scripttitle");
      final int _cursorIndexOfScriptcreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "ScriptcreatedAt");
      final int _cursorIndexOfScriptEditTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptEditTextSize");
      final int _cursorIndexOfPromptSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "promptSpeed");
      final int _cursorIndexOfScriptAlignment = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptAlignment");
      final int _cursorIndexOfScriptColor = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptColor");
      final int _cursorIndexOfScriptuserId = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptuser_Id");
      final int _cursorIndexOfFinalScrText = CursorUtil.getColumnIndexOrThrow(_cursor, "finalScrText");
      final int _cursorIndexOfScriptTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptTextSize");
      final int _cursorIndexOfPromptTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "promptTextSize");
      final List<ScriptDB> _result = new ArrayList<ScriptDB>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ScriptDB _item;
        _item = new ScriptDB();
        _item.key = _cursor.getInt(_cursorIndexOfKey);
        _item.scriptId = _cursor.getString(_cursorIndexOfScriptId);
        _item.internetFlag = _cursor.getInt(_cursorIndexOfInternetFlag);
        _item.editFlag = _cursor.getInt(_cursorIndexOfEditFlag);
        _item.getScrPromptTextSize = _cursor.getString(_cursorIndexOfGetScrPromptTextSize);
        _item.scr_txt = _cursor.getString(_cursorIndexOfScrTxt);
        _item.scriptDescription = _cursor.getString(_cursorIndexOfScriptDescription);
        _item.Scripttitle = _cursor.getString(_cursorIndexOfScripttitle);
        _item.ScriptcreatedAt = _cursor.getString(_cursorIndexOfScriptcreatedAt);
        _item.scriptEditTextSize = _cursor.getString(_cursorIndexOfScriptEditTextSize);
        _item.promptSpeed = _cursor.getString(_cursorIndexOfPromptSpeed);
        _item.scriptAlignment = _cursor.getString(_cursorIndexOfScriptAlignment);
        _item.scriptColor = _cursor.getString(_cursorIndexOfScriptColor);
        _item.scriptuser_Id = _cursor.getString(_cursorIndexOfScriptuserId);
        _item.finalScrText = _cursor.getString(_cursorIndexOfFinalScrText);
        _item.scriptTextSize = _cursor.getString(_cursorIndexOfScriptTextSize);
        _item.promptTextSize = _cursor.getString(_cursorIndexOfPromptTextSize);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ScriptDB> internetBased() {
    final String _sql = "SELECT * FROM SCRIPTDATA WHERE internetFlag Like 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfKey = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptDBkey");
      final int _cursorIndexOfScriptId = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptId");
      final int _cursorIndexOfInternetFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "internetFlag");
      final int _cursorIndexOfEditFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "editFlag");
      final int _cursorIndexOfGetScrPromptTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "getScrPromptTextSize");
      final int _cursorIndexOfScrTxt = CursorUtil.getColumnIndexOrThrow(_cursor, "scr_txt");
      final int _cursorIndexOfScriptDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptDescription");
      final int _cursorIndexOfScripttitle = CursorUtil.getColumnIndexOrThrow(_cursor, "Scripttitle");
      final int _cursorIndexOfScriptcreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "ScriptcreatedAt");
      final int _cursorIndexOfScriptEditTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptEditTextSize");
      final int _cursorIndexOfPromptSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "promptSpeed");
      final int _cursorIndexOfScriptAlignment = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptAlignment");
      final int _cursorIndexOfScriptColor = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptColor");
      final int _cursorIndexOfScriptuserId = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptuser_Id");
      final int _cursorIndexOfFinalScrText = CursorUtil.getColumnIndexOrThrow(_cursor, "finalScrText");
      final int _cursorIndexOfScriptTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptTextSize");
      final int _cursorIndexOfPromptTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "promptTextSize");
      final List<ScriptDB> _result = new ArrayList<ScriptDB>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ScriptDB _item;
        _item = new ScriptDB();
        _item.key = _cursor.getInt(_cursorIndexOfKey);
        _item.scriptId = _cursor.getString(_cursorIndexOfScriptId);
        _item.internetFlag = _cursor.getInt(_cursorIndexOfInternetFlag);
        _item.editFlag = _cursor.getInt(_cursorIndexOfEditFlag);
        _item.getScrPromptTextSize = _cursor.getString(_cursorIndexOfGetScrPromptTextSize);
        _item.scr_txt = _cursor.getString(_cursorIndexOfScrTxt);
        _item.scriptDescription = _cursor.getString(_cursorIndexOfScriptDescription);
        _item.Scripttitle = _cursor.getString(_cursorIndexOfScripttitle);
        _item.ScriptcreatedAt = _cursor.getString(_cursorIndexOfScriptcreatedAt);
        _item.scriptEditTextSize = _cursor.getString(_cursorIndexOfScriptEditTextSize);
        _item.promptSpeed = _cursor.getString(_cursorIndexOfPromptSpeed);
        _item.scriptAlignment = _cursor.getString(_cursorIndexOfScriptAlignment);
        _item.scriptColor = _cursor.getString(_cursorIndexOfScriptColor);
        _item.scriptuser_Id = _cursor.getString(_cursorIndexOfScriptuserId);
        _item.finalScrText = _cursor.getString(_cursorIndexOfFinalScrText);
        _item.scriptTextSize = _cursor.getString(_cursorIndexOfScriptTextSize);
        _item.promptTextSize = _cursor.getString(_cursorIndexOfPromptTextSize);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ScriptDB> editBased() {
    final String _sql = "SELECT * FROM SCRIPTDATA WHERE editFlag Like 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfKey = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptDBkey");
      final int _cursorIndexOfScriptId = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptId");
      final int _cursorIndexOfInternetFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "internetFlag");
      final int _cursorIndexOfEditFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "editFlag");
      final int _cursorIndexOfGetScrPromptTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "getScrPromptTextSize");
      final int _cursorIndexOfScrTxt = CursorUtil.getColumnIndexOrThrow(_cursor, "scr_txt");
      final int _cursorIndexOfScriptDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptDescription");
      final int _cursorIndexOfScripttitle = CursorUtil.getColumnIndexOrThrow(_cursor, "Scripttitle");
      final int _cursorIndexOfScriptcreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "ScriptcreatedAt");
      final int _cursorIndexOfScriptEditTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptEditTextSize");
      final int _cursorIndexOfPromptSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "promptSpeed");
      final int _cursorIndexOfScriptAlignment = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptAlignment");
      final int _cursorIndexOfScriptColor = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptColor");
      final int _cursorIndexOfScriptuserId = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptuser_Id");
      final int _cursorIndexOfFinalScrText = CursorUtil.getColumnIndexOrThrow(_cursor, "finalScrText");
      final int _cursorIndexOfScriptTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptTextSize");
      final int _cursorIndexOfPromptTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "promptTextSize");
      final List<ScriptDB> _result = new ArrayList<ScriptDB>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ScriptDB _item;
        _item = new ScriptDB();
        _item.key = _cursor.getInt(_cursorIndexOfKey);
        _item.scriptId = _cursor.getString(_cursorIndexOfScriptId);
        _item.internetFlag = _cursor.getInt(_cursorIndexOfInternetFlag);
        _item.editFlag = _cursor.getInt(_cursorIndexOfEditFlag);
        _item.getScrPromptTextSize = _cursor.getString(_cursorIndexOfGetScrPromptTextSize);
        _item.scr_txt = _cursor.getString(_cursorIndexOfScrTxt);
        _item.scriptDescription = _cursor.getString(_cursorIndexOfScriptDescription);
        _item.Scripttitle = _cursor.getString(_cursorIndexOfScripttitle);
        _item.ScriptcreatedAt = _cursor.getString(_cursorIndexOfScriptcreatedAt);
        _item.scriptEditTextSize = _cursor.getString(_cursorIndexOfScriptEditTextSize);
        _item.promptSpeed = _cursor.getString(_cursorIndexOfPromptSpeed);
        _item.scriptAlignment = _cursor.getString(_cursorIndexOfScriptAlignment);
        _item.scriptColor = _cursor.getString(_cursorIndexOfScriptColor);
        _item.scriptuser_Id = _cursor.getString(_cursorIndexOfScriptuserId);
        _item.finalScrText = _cursor.getString(_cursorIndexOfFinalScrText);
        _item.scriptTextSize = _cursor.getString(_cursorIndexOfScriptTextSize);
        _item.promptTextSize = _cursor.getString(_cursorIndexOfPromptTextSize);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public ScriptDB lastPrimaryKey() {
    final String _sql = "SELECT * FROM SCRIPTDATA ORDER BY scriptDBkey DESC  LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfKey = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptDBkey");
      final int _cursorIndexOfScriptId = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptId");
      final int _cursorIndexOfInternetFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "internetFlag");
      final int _cursorIndexOfEditFlag = CursorUtil.getColumnIndexOrThrow(_cursor, "editFlag");
      final int _cursorIndexOfGetScrPromptTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "getScrPromptTextSize");
      final int _cursorIndexOfScrTxt = CursorUtil.getColumnIndexOrThrow(_cursor, "scr_txt");
      final int _cursorIndexOfScriptDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptDescription");
      final int _cursorIndexOfScripttitle = CursorUtil.getColumnIndexOrThrow(_cursor, "Scripttitle");
      final int _cursorIndexOfScriptcreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "ScriptcreatedAt");
      final int _cursorIndexOfScriptEditTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptEditTextSize");
      final int _cursorIndexOfPromptSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "promptSpeed");
      final int _cursorIndexOfScriptAlignment = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptAlignment");
      final int _cursorIndexOfScriptColor = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptColor");
      final int _cursorIndexOfScriptuserId = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptuser_Id");
      final int _cursorIndexOfFinalScrText = CursorUtil.getColumnIndexOrThrow(_cursor, "finalScrText");
      final int _cursorIndexOfScriptTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "scriptTextSize");
      final int _cursorIndexOfPromptTextSize = CursorUtil.getColumnIndexOrThrow(_cursor, "promptTextSize");
      final ScriptDB _result;
      if(_cursor.moveToFirst()) {
        _result = new ScriptDB();
        _result.key = _cursor.getInt(_cursorIndexOfKey);
        _result.scriptId = _cursor.getString(_cursorIndexOfScriptId);
        _result.internetFlag = _cursor.getInt(_cursorIndexOfInternetFlag);
        _result.editFlag = _cursor.getInt(_cursorIndexOfEditFlag);
        _result.getScrPromptTextSize = _cursor.getString(_cursorIndexOfGetScrPromptTextSize);
        _result.scr_txt = _cursor.getString(_cursorIndexOfScrTxt);
        _result.scriptDescription = _cursor.getString(_cursorIndexOfScriptDescription);
        _result.Scripttitle = _cursor.getString(_cursorIndexOfScripttitle);
        _result.ScriptcreatedAt = _cursor.getString(_cursorIndexOfScriptcreatedAt);
        _result.scriptEditTextSize = _cursor.getString(_cursorIndexOfScriptEditTextSize);
        _result.promptSpeed = _cursor.getString(_cursorIndexOfPromptSpeed);
        _result.scriptAlignment = _cursor.getString(_cursorIndexOfScriptAlignment);
        _result.scriptColor = _cursor.getString(_cursorIndexOfScriptColor);
        _result.scriptuser_Id = _cursor.getString(_cursorIndexOfScriptuserId);
        _result.finalScrText = _cursor.getString(_cursorIndexOfFinalScrText);
        _result.scriptTextSize = _cursor.getString(_cursorIndexOfScriptTextSize);
        _result.promptTextSize = _cursor.getString(_cursorIndexOfPromptTextSize);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
