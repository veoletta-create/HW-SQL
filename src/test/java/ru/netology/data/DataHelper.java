package ru.netology.data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getFirstUser() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getSecondUser() {
        return new AuthInfo("petya", "123qwerty");
    }

    public static AuthInfo getInvalidUser() {
        return new AuthInfo("vasya", "wrongPassword");
    }
}