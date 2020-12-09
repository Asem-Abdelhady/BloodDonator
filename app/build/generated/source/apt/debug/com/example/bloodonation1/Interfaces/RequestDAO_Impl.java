package com.example.bloodonation1.Interfaces;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import android.support.annotation.NonNull;
import com.example.bloodonation1.Models.LocalRequestModel;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class RequestDAO_Impl implements RequestDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfLocalRequestModel;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfLocalRequestModel;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfLocalRequestModel;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllRequestes;

  public RequestDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLocalRequestModel = new EntityInsertionAdapter<LocalRequestModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `LocalRequestModel`(`id`,`bloodType`,`name`,`age`,`phone`,`date`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LocalRequestModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getBloodType() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getBloodType());
        }
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getAge() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAge());
        }
        if (value.getPhone() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getPhone());
        }
        if (value.getDate() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDate());
        }
      }
    };
    this.__deletionAdapterOfLocalRequestModel = new EntityDeletionOrUpdateAdapter<LocalRequestModel>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `LocalRequestModel` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LocalRequestModel value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfLocalRequestModel = new EntityDeletionOrUpdateAdapter<LocalRequestModel>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `LocalRequestModel` SET `id` = ?,`bloodType` = ?,`name` = ?,`age` = ?,`phone` = ?,`date` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, LocalRequestModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getBloodType() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getBloodType());
        }
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getAge() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAge());
        }
        if (value.getPhone() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getPhone());
        }
        if (value.getDate() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDate());
        }
        stmt.bindLong(7, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAllRequestes = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM LocalRequestModel";
        return _query;
      }
    };
  }

  @Override
  public void insert(LocalRequestModel model) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfLocalRequestModel.insert(model);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(LocalRequestModel model) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfLocalRequestModel.handle(model);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(LocalRequestModel model) {
    __db.beginTransaction();
    try {
      __updateAdapterOfLocalRequestModel.handle(model);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllRequestes() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllRequestes.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllRequestes.release(_stmt);
    }
  }

  @Override
  public LiveData<List<LocalRequestModel>> getAllRequestes() {
    final String _sql = "SELECT * FROM LocalRequestModel ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<LocalRequestModel>>() {
      private Observer _observer;

      @Override
      protected List<LocalRequestModel> compute() {
        if (_observer == null) {
          _observer = new Observer("LocalRequestModel") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfBloodType = _cursor.getColumnIndexOrThrow("bloodType");
          final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
          final int _cursorIndexOfAge = _cursor.getColumnIndexOrThrow("age");
          final int _cursorIndexOfPhone = _cursor.getColumnIndexOrThrow("phone");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final List<LocalRequestModel> _result = new ArrayList<LocalRequestModel>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final LocalRequestModel _item;
            final String _tmpBloodType;
            _tmpBloodType = _cursor.getString(_cursorIndexOfBloodType);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpAge;
            _tmpAge = _cursor.getString(_cursorIndexOfAge);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item = new LocalRequestModel(_tmpBloodType,_tmpName,_tmpAge,_tmpPhone,_tmpDate);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
