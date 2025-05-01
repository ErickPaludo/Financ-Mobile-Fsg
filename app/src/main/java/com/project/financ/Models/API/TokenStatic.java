package com.project.financ.Models.API;

public class TokenStatic {
    public static String token;
    public static String refreshToken;
    public static String user;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        TokenStatic.token = token;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }

    public static void setRefreshToken(String refreshToken) {
        TokenStatic.refreshToken = refreshToken;
    }
    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        TokenStatic.user = user;
    }
}
