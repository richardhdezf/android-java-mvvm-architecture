package com.example.mvvm.model.source.local;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;

import com.example.mvvm.model.Password;
import com.example.mvvm.model.PasswordStrength;
import com.example.mvvm.model.source.PasswordDataSource;
import com.example.mvvm.util.AppExecutors;

import java.util.List;

public class PasswordLocalDataSource implements PasswordDataSource {
    private PasswordLocalDataSource(@NonNull AppExecutors appExecutors,
                                    @NonNull PasswordDao passwordDao) {
        this.appExecutors = appExecutors;
        this.passwordDao = passwordDao;
    }


    private final PasswordDao passwordDao;
    private final AppExecutors appExecutors;

    private static volatile PasswordLocalDataSource INSTANCE;

    public static PasswordLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                      @NonNull PasswordDao passwordDao) {
        if (INSTANCE == null) {
            synchronized (PasswordLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PasswordLocalDataSource(appExecutors, passwordDao);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Password>> getAll() {
        return passwordDao.getAll();
    }

    @Override
    public LiveData<Password> get(@NonNull String id) {
        return passwordDao.get(id);
    }

    @Override
    public void insert(@NonNull Password password) {
        Runnable saveRunnable = () -> passwordDao.insert(password);
        appExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void update(@NonNull Password password) {
        Runnable saveRunnable = () -> passwordDao.update(password);
        appExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void updateStrength(String id, PasswordStrength strength) {
        Runnable completeRunnable = () -> passwordDao.updateStrength(id, strength);
        appExecutors.diskIO().execute(completeRunnable);
    }

    @Override
    public void deleteAll() {
        Runnable deleteRunnable = passwordDao::deleteAll;
        appExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void delete(@NonNull String id) {
        Runnable deleteRunnable = () -> passwordDao.delete(id);
        appExecutors.diskIO().execute(deleteRunnable);
    }

    @VisibleForTesting
    static void clearInstance() {
        INSTANCE = null;
    }
}
