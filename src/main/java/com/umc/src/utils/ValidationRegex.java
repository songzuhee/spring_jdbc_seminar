package com.umc.src.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {
    public static boolean isRegexEmail(String target) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public static boolean isRegexPassword1(String password) {
        // 비밀번호 포맷 확인(영문, 특수문자, 숫자 포함 8자 이상)
        Pattern passPattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");
        Matcher passMatcher1 = passPattern1.matcher(password);

        return passMatcher1.find();
    }

    /*public static boolean isRegexPassword2(String password) {
        // 반복된 문자 확인
        Pattern passPattern2 = Pattern.compile("(\\w)\\1\\1\\1");
        Matcher passMatcher2 = passPattern2.matcher(password);

        return passMatcher2.find();
    }*/

}

