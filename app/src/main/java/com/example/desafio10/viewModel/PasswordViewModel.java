package com.example.desafio10.viewModel;

import android.graphics.Color;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.desafio10.model.Password;
import com.example.desafio10.model.PasswordStrength;

public class PasswordViewModel extends ViewModel {
    public PasswordViewModel() {
        Password initialPassword = new Password("", PasswordStrength.INITIAL);
        passwordStrength = new MutableLiveData<>(initialPassword.getStrength());
    }

    private final MutableLiveData<PasswordStrength> passwordStrength;

    public LiveData<String> getStrength() {
        return Transformations.map(passwordStrength,
                strength -> strength == PasswordStrength.INITIAL ? null : strength.name());
    }

    public LiveData<Integer> getStrengthBackgroundColor() {
        return Transformations.map(passwordStrength, strength -> {
            switch (strength) {
                case WEAK:
                    return Color.RED;
                case MEDIUM:
                    return Color.YELLOW;
                case STRONG:
                    return Color.GREEN;
                case VERY_STRONG:
                    return Color.BLUE;
                default:
                    return Color.TRANSPARENT;
            }
        });
    }

    public LiveData<Integer> getStrengthTextColor() {
        return Transformations.map(passwordStrength, strength -> {
            switch (strength) {
                case WEAK:
                case VERY_STRONG:
                    return Color.WHITE;
                case MEDIUM:
                case STRONG:
                default:
                    return Color.BLACK;
            }
        });
    }

    public void onPasswordChanged(CharSequence cs) {
        passwordStrength.setValue(Password.verify(cs));
    }
}