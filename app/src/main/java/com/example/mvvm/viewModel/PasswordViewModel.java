package com.example.mvvm.viewModel;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mvvm.model.Password;
import com.example.mvvm.model.PasswordStrength;
import com.example.mvvm.model.source.PasswordRepository;

import java.util.Objects;

public class PasswordViewModel extends ViewModel {
    public PasswordViewModel(@NonNull PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
        passwordRepository.deleteAll();
        String passwordId = Password.DEFAULT_ID;
        Password password = new Password(passwordId, "", PasswordStrength.INITIAL);
        passwordRepository.insert(password);
        this.password = passwordRepository.get(passwordId);
    }

    private final PasswordRepository passwordRepository;
    private final LiveData<Password> password;

    public LiveData<String> getStrengthText() {
        return Transformations.map(password, password -> (password == null)
                ? PasswordStrength.INITIAL.name()
                : password.getStrength().name());
    }

    public LiveData<Integer> getStrengthBackgroundColor() {
        return Transformations.map(password, password -> {
            if (password == null) return Color.TRANSPARENT;
            switch (password.getStrength()) {
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
        return Transformations.map(password, password -> {
            if (password == null) return Color.BLACK;
            switch (password.getStrength()) {
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

    private boolean validLength(String string) {
        return string.length() > 4;
    }

    private boolean containsUpperCase(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isUpperCase(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean containsNumber(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private void updatePassword(String passwordId, PasswordStrength passwordStrength, String text) {
        Password updatedPassword = new Password(passwordId, text, passwordStrength);
        passwordRepository.update(updatedPassword);
    }

    public void verify(CharSequence cs) {
        String passwordId = Objects.requireNonNull(password.getValue()).getId();
        PasswordStrength passwordStrength;
        String passwordValue = cs.toString();

        if (!validLength(passwordValue))
            passwordStrength = PasswordStrength.WEAK;
        else if (!containsUpperCase(passwordValue))
            passwordStrength = PasswordStrength.MEDIUM;
        else if (!containsNumber(passwordValue))
            passwordStrength = PasswordStrength.STRONG;
        else {
            passwordStrength = PasswordStrength.VERY_STRONG;
            updatePassword(passwordId, passwordStrength, passwordValue);
            return;
        }
        passwordRepository.updateStrength(passwordId, passwordStrength);
    }

    public static class Factory implements ViewModelProvider.Factory {
        public Factory(@NonNull PasswordRepository passwordRepository) {
            this.passwordRepository = passwordRepository;
        }

        private final PasswordRepository passwordRepository;

        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new PasswordViewModel(passwordRepository);
        }
    }
}

