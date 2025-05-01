package com.project.financ.Models.API;

public class Token {
    public String token;
    public String refreshToken;
    public String expiration;
    public String id;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        refreshToken = refreshToken;
    }
    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        expiration = expiration;
    }
    public String getUser() {
        return id;
    }

    public void setUser(String id) {
        id = id;
    }

    public Token(String token,String refreshToken,String expiration,String id){
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
        this.id = id;
    }
}
