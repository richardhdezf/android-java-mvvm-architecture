package com.example.mvvm.model.source.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mvvm.model.Password;
import com.example.mvvm.model.PasswordStrength;

import java.util.List;

@Dao
public interface PasswordDao {

    @Query("SELECT * FROM passwords")
    LiveData<List<Password>> getAll();

    @Query("SELECT * FROM passwords WHERE id = :id")
    LiveData<Password> get(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Password password);

    @Update
    void update(Password password);

    @Query("UPDATE passwords SET strength = :strength WHERE id = :id")
    void updateStrength(String id, PasswordStrength strength);

    @Query("DELETE FROM passwords")
    void deleteAll();

    @Query("DELETE FROM passwords WHERE id = :id")
    void delete(String id);
}
