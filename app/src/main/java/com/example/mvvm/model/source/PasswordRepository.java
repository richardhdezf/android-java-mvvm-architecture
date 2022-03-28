package com.example.mvvm.model.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.mvvm.model.Password;
import com.example.mvvm.model.PasswordStrength;

import java.util.List;

public class PasswordRepository implements PasswordDataSource {
    private PasswordRepository(@NonNull PasswordDataSource tasksLocalDataSource) {
        localDataSource = tasksLocalDataSource;
        setup();
    }

    private void setup() {
        localDataSource.deleteAll();
        String passwordId = Password.DEFAULT_ID;
        Password password = new Password(passwordId, "", PasswordStrength.INITIAL);
        localDataSource.insert(password);
    }

    private final PasswordDataSource localDataSource;

    private static PasswordRepository INSTANCE = null;

    public static PasswordRepository getInstance(PasswordDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new PasswordRepository(tasksLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public LiveData<List<Password>> getAll() {
        return localDataSource.getAll();
    }

    @Override
    public LiveData<Password> get(@NonNull final String id) {
        return localDataSource.get(id);
    }

    @Override
    public void insert(@NonNull Password password) {
        localDataSource.insert(password);
    }

    @Override
    public void update(@NonNull Password password) {
        localDataSource.update(password);
    }

    @Override
    public void updateStrength(String id, PasswordStrength strength) {
        localDataSource.updateStrength(id, strength);
    }

    @Override
    public void deleteAll() {
        localDataSource.deleteAll();
    }

    @Override
    public void delete(@NonNull String id) {
        localDataSource.delete(id);
    }
}
