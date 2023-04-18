package com.example.projetoaulaunc.data.repository;

import com.example.projetoaulaunc.data.Result;
import com.example.projetoaulaunc.data.datasource.LoginDataSource;
import com.example.projetoaulaunc.data.model.UserModel;

import java.util.Map;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private final LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private UserModel user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setUserModel(UserModel user) {
        this.user = user;
    }

    public Result<UserModel> login(Map<String, Object> json) {
        // handle login
        Result<UserModel> result = dataSource.login(json);
        if (result instanceof Result.Success) {
            setUserModel(((Result.Success<UserModel>) result).getData());
        }
        return result;
    }

    public Result<UserModel> register(Map<String, Object> json) {
        // handle login
        Result<UserModel> result = dataSource.createUser(json);
        if (result instanceof Result.Success) {
            setUserModel(((Result.Success<UserModel>) result).getData());
        }
        return result;
    }
}