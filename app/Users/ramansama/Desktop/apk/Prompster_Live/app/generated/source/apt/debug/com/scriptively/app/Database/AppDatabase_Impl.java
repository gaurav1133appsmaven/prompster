package com.scriptively.app.Database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ScriptDBDao _scriptDBDao;

  private volatile StoryBoardDao _storyBoardDao;

  private volatile FolderDao _folderDao;

  private volatile FolderDataDao _folderDataDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `scriptdata` (`scriptDBkey` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `scriptId` TEXT, `internetFlag` INTEGER NOT NULL, `editFlag` INTEGER NOT NULL, `getScrPromptTextSize` TEXT, `scr_txt` TEXT, `scriptDescription` TEXT, `Scripttitle` TEXT, `ScriptcreatedAt` TEXT, `scriptEditTextSize` TEXT, `promptSpeed` TEXT, `scriptAlignment` TEXT, `scriptColor` TEXT, `scriptuser_Id` TEXT, `finalScrText` TEXT, `scriptTextSize` TEXT, `promptTextSize` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `storyboard` (`script_id` TEXT, `storyKey` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `scriptKey` INTEGER NOT NULL, `StoryboardId` TEXT, `storyBoardinternetFlag` INTEGER NOT NULL, `storyBoardeditFlag` INTEGER NOT NULL, `Storyboard_att_text` TEXT, `storyboard_name` TEXT, `storyboardTextSize` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `folderdb` (`folder_key` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `folder_Id` TEXT, `internet_flag` INTEGER NOT NULL, `editFlag` INTEGER NOT NULL, `userId` TEXT, `name` TEXT, `createdAt` TEXT, `modified_at` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `folder_data` (`createFlag` INTEGER NOT NULL, `index` INTEGER NOT NULL, `folderName` TEXT, PRIMARY KEY(`index`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '85466d6068c90ea831c81e5a77772f57')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `scriptdata`");
        _db.execSQL("DROP TABLE IF EXISTS `storyboard`");
        _db.execSQL("DROP TABLE IF EXISTS `folderdb`");
        _db.execSQL("DROP TABLE IF EXISTS `folder_data`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsScriptdata = new HashMap<String, TableInfo.Column>(17);
        _columnsScriptdata.put("scriptDBkey", new TableInfo.Column("scriptDBkey", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScriptdata.put("scriptId", new TableInfo.Column("scriptId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScriptdata.put("internetFlag", new TableInfo.Column("internetFlag", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScriptdata.put("editFlag", new TableInfo.Column("editFlag", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScriptdata.put("getScrPromptTextSize", new TableInfo.Column("getScrPromptTextSize", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScriptdata.put("scr_txt", new TableInfo.Column("scr_txt", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScriptdata.put("scriptDescription", new TableInfo.Column("scriptDescription", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScriptdata.put("Scripttitle", new TableInfo.Column("Scripttitle", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScriptdata.put("ScriptcreatedAt", new TableInfo.Column("ScriptcreatedAt", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScriptdata.put("scriptEditTextSize", new TableInfo.Column("scriptEditTextSize", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScriptdata.put("promptSpeed", new TableInfo.Column("promptSpeed", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScriptdata.put("scriptAlignment", new TableInfo.Column("scriptAlignment", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScriptdata.put("scriptColor", new TableInfo.Column("scriptColor", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScriptdata.put("scriptuser_Id", new TableInfo.Column("scriptuser_Id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScriptdata.put("finalScrText", new TableInfo.Column("finalScrText", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScriptdata.put("scriptTextSize", new TableInfo.Column("scriptTextSize", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScriptdata.put("promptTextSize", new TableInfo.Column("promptTextSize", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysScriptdata = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesScriptdata = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoScriptdata = new TableInfo("scriptdata", _columnsScriptdata, _foreignKeysScriptdata, _indicesScriptdata);
        final TableInfo _existingScriptdata = TableInfo.read(_db, "scriptdata");
        if (! _infoScriptdata.equals(_existingScriptdata)) {
          return new RoomOpenHelper.ValidationResult(false, "scriptdata(com.scriptively.app.DatabaseModel.ScriptDB).\n"
                  + " Expected:\n" + _infoScriptdata + "\n"
                  + " Found:\n" + _existingScriptdata);
        }
        final HashMap<String, TableInfo.Column> _columnsStoryboard = new HashMap<String, TableInfo.Column>(9);
        _columnsStoryboard.put("script_id", new TableInfo.Column("script_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStoryboard.put("storyKey", new TableInfo.Column("storyKey", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStoryboard.put("scriptKey", new TableInfo.Column("scriptKey", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStoryboard.put("StoryboardId", new TableInfo.Column("StoryboardId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStoryboard.put("storyBoardinternetFlag", new TableInfo.Column("storyBoardinternetFlag", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStoryboard.put("storyBoardeditFlag", new TableInfo.Column("storyBoardeditFlag", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStoryboard.put("Storyboard_att_text", new TableInfo.Column("Storyboard_att_text", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStoryboard.put("storyboard_name", new TableInfo.Column("storyboard_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStoryboard.put("storyboardTextSize", new TableInfo.Column("storyboardTextSize", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStoryboard = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStoryboard = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStoryboard = new TableInfo("storyboard", _columnsStoryboard, _foreignKeysStoryboard, _indicesStoryboard);
        final TableInfo _existingStoryboard = TableInfo.read(_db, "storyboard");
        if (! _infoStoryboard.equals(_existingStoryboard)) {
          return new RoomOpenHelper.ValidationResult(false, "storyboard(com.scriptively.app.DatabaseModel.StoryBoardDB).\n"
                  + " Expected:\n" + _infoStoryboard + "\n"
                  + " Found:\n" + _existingStoryboard);
        }
        final HashMap<String, TableInfo.Column> _columnsFolderdb = new HashMap<String, TableInfo.Column>(8);
        _columnsFolderdb.put("folder_key", new TableInfo.Column("folder_key", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolderdb.put("folder_Id", new TableInfo.Column("folder_Id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolderdb.put("internet_flag", new TableInfo.Column("internet_flag", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolderdb.put("editFlag", new TableInfo.Column("editFlag", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolderdb.put("userId", new TableInfo.Column("userId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolderdb.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolderdb.put("createdAt", new TableInfo.Column("createdAt", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolderdb.put("modified_at", new TableInfo.Column("modified_at", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFolderdb = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFolderdb = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFolderdb = new TableInfo("folderdb", _columnsFolderdb, _foreignKeysFolderdb, _indicesFolderdb);
        final TableInfo _existingFolderdb = TableInfo.read(_db, "folderdb");
        if (! _infoFolderdb.equals(_existingFolderdb)) {
          return new RoomOpenHelper.ValidationResult(false, "folderdb(com.scriptively.app.DatabaseModel.FolderDB).\n"
                  + " Expected:\n" + _infoFolderdb + "\n"
                  + " Found:\n" + _existingFolderdb);
        }
        final HashMap<String, TableInfo.Column> _columnsFolderData = new HashMap<String, TableInfo.Column>(3);
        _columnsFolderData.put("createFlag", new TableInfo.Column("createFlag", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolderData.put("index", new TableInfo.Column("index", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFolderData.put("folderName", new TableInfo.Column("folderName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFolderData = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFolderData = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFolderData = new TableInfo("folder_data", _columnsFolderData, _foreignKeysFolderData, _indicesFolderData);
        final TableInfo _existingFolderData = TableInfo.read(_db, "folder_data");
        if (! _infoFolderData.equals(_existingFolderData)) {
          return new RoomOpenHelper.ValidationResult(false, "folder_data(com.scriptively.app.DatabaseModel.FolderDataDb).\n"
                  + " Expected:\n" + _infoFolderData + "\n"
                  + " Found:\n" + _existingFolderData);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "85466d6068c90ea831c81e5a77772f57", "99e60c6cbfcfe477e8bbc1fbea7358ef");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "scriptdata","storyboard","folderdb","folder_data");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `scriptdata`");
      _db.execSQL("DELETE FROM `storyboard`");
      _db.execSQL("DELETE FROM `folderdb`");
      _db.execSQL("DELETE FROM `folder_data`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public ScriptDBDao scriptDBDao() {
    if (_scriptDBDao != null) {
      return _scriptDBDao;
    } else {
      synchronized(this) {
        if(_scriptDBDao == null) {
          _scriptDBDao = new ScriptDBDao_Impl(this);
        }
        return _scriptDBDao;
      }
    }
  }

  @Override
  public StoryBoardDao storyBoardDao() {
    if (_storyBoardDao != null) {
      return _storyBoardDao;
    } else {
      synchronized(this) {
        if(_storyBoardDao == null) {
          _storyBoardDao = new StoryBoardDao_Impl(this);
        }
        return _storyBoardDao;
      }
    }
  }

  @Override
  public FolderDao folderDao() {
    if (_folderDao != null) {
      return _folderDao;
    } else {
      synchronized(this) {
        if(_folderDao == null) {
          _folderDao = new FolderDao_Impl(this);
        }
        return _folderDao;
      }
    }
  }

  @Override
  public FolderDataDao folderDataDao() {
    if (_folderDataDao != null) {
      return _folderDataDao;
    } else {
      synchronized(this) {
        if(_folderDataDao == null) {
          _folderDataDao = new FolderDataDao_Impl(this);
        }
        return _folderDataDao;
      }
    }
  }
}
