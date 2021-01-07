package com.kodstar.backend.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
   /**
    the password must include at least a number, a lowercase character, an uppercase character,
    a punctuation or a symbol and the length o the password should be between 7-20
    */
    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#&()–{}:.;',?/*~$^+=<>]).{7,20}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(final String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
