package com.example.projetoaulaunc.app.pages.authentication.ui.login;

import com.example.projetoaulaunc.data.datasource.model.UserModel;

import java.util.Map;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private final UserModel userModel;

    LoggedInUserView(UserModel user) {
        this.userModel = user;
    }

    String getDisplayName() {
        return userModel.getName();
    }

    String getEmail() {
        return userModel.getEmail();
    }

    String getUid() {
        return userModel.getId();
    }

    Map<String, Object> userToJson() {
        return userModel.toJson();
    }
}