package com.example.mvvm.model.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.mvvm.model.Password;
import com.example.mvvm.model.PasswordStrength;

import java.util.List;

public interface PasswordDataSource {

    LiveData<List<Password>> getAll();
    LiveData<Password> get(@NonNull String id);

    void insert(@NonNull Password password);

    void update(@NonNull Password password);

    void updateStrength(String id, PasswordStrength strength);

    void deleteAll();
    void delete(@NonNull String id);
}
