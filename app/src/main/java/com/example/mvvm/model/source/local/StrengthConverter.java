package com.example.mvvm.model.source.local;

import androidx.room.TypeConverter;

import com.example.mvvm.model.PasswordStrength;

public class StrengthConverter {
    @TypeConverter
    public static PasswordStrength toStrength(String value) {
        return PasswordStrength.valueOf(value);
    }

    @TypeConverter
    public static String fromStrength(PasswordStrength value) {
        return value.name();
    }
}
