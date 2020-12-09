package com.example.bloodonation1.RoomDB;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import com.example.bloodonation1.Interfaces.RequestDAO;
import com.example.bloodonation1.Interfaces.RequestDAO_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class RequestDatabase_Impl extends RequestDatabase {
  private volatile RequestDAO _requestDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `LocalRequestModel` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `bloodType` TEXT, `name` TEXT, `age` TEXT, `phone` TEXT, `date` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"7b665f9182db9a2d852fcd1f3a713e1e\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `LocalRequestModel`");
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
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsLocalRequestModel = new HashMap<String, TableInfo.Column>(6);
        _columnsLocalRequestModel.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsLocalRequestModel.put("bloodType", new TableInfo.Column("bloodType", "TEXT", false, 0));
        _columnsLocalRequestModel.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsLocalRequestModel.put("age", new TableInfo.Column("age", "TEXT", false, 0));
        _columnsLocalRequestModel.put("phone", new TableInfo.Column("phone", "TEXT", false, 0));
        _columnsLocalRequestModel.put("date", new TableInfo.Column("date", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLocalRequestModel = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLocalRequestModel = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLocalRequestModel = new TableInfo("LocalRequestModel", _columnsLocalRequestModel, _foreignKeysLocalRequestModel, _indicesLocalRequestModel);
        final TableInfo _existingLocalRequestModel = TableInfo.read(_db, "LocalRequestModel");
        if (! _infoLocalRequestModel.equals(_existingLocalRequestModel)) {
          throw new IllegalStateException("Migration didn't properly handle LocalRequestModel(com.example.bloodonation1.Models.LocalRequestModel).\n"
                  + " Expected:\n" + _infoLocalRequestModel + "\n"
                  + " Found:\n" + _existingLocalRequestModel);
        }
      }
    }, "7b665f9182db9a2d852fcd1f3a713e1e", "634d841837331092bab3c906afd8a513");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "LocalRequestModel");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `LocalRequestModel`");
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
  public RequestDAO requestDAO() {
    if (_requestDAO != null) {
      return _requestDAO;
    } else {
      synchronized(this) {
        if(_requestDAO == null) {
          _requestDAO = new RequestDAO_Impl(this);
        }
        return _requestDAO;
      }
    }
  }
}
