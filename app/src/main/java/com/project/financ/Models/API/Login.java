package com.project.financ.Models.API;

import android.icu.lang.UProperty;

import com.project.financ.Models.Credito;

public class Login {
    public String userName;
    public String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = password;
    }

    public Login(String userName,String password){
        this.userName = userName;
        this.password = password;
    }
}
