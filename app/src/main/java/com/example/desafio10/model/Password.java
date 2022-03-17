package com.example.desafio10.model;

public class Password {
    public Password(String value, PasswordStrength strength) {
        this.value = value;
        this.strength = strength;
    }

    private String value;
    private PasswordStrength strength;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PasswordStrength getStrength() {
        return strength;
    }

    public void setStrength(PasswordStrength strength) {
        this.strength = strength;
    }

    private static boolean validLength(String string) {
        return string.length() > 4;
    }

    private static boolean containsUpperCase(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isUpperCase(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsNumber(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static PasswordStrength verify(CharSequence cs) {
        String text = cs.toString();
        if (!validLength(text))
            return PasswordStrength.WEAK;
        else if (!containsUpperCase(text))
            return PasswordStrength.MEDIUM;
        else if (!containsNumber(text))
            return PasswordStrength.STRONG;
        else return PasswordStrength.VERY_STRONG;
    }
}
