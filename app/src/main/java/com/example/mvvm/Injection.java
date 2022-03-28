package com.example.mvvm;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.mvvm.model.source.PasswordRepository;
import com.example.mvvm.model.source.local.PasswordDatabase;
import com.example.mvvm.model.source.local.PasswordLocalDataSource;
import com.example.mvvm.util.AppExecutors;

public class Injection {
    public static PasswordRepository providePasswordRepository(@NonNull Context context) {
        PasswordDatabase database = PasswordDatabase.getInstance(context);
        return PasswordRepository.getInstance(PasswordLocalDataSource
                .getInstance(new AppExecutors(), database.passwordDao()));
    }
}
