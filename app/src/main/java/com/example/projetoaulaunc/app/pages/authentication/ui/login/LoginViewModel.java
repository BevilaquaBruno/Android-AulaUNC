package com.example.projetoaulaunc.app.pages.authentication.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.util.Patterns;

import com.example.projetoaulaunc.R;
import com.example.projetoaulaunc.data.model.UserModel;
import com.example.projetoaulaunc.data.repository.LoginRepository;
import com.example.projetoaulaunc.data.Result;
import com.example.projetoaulaunc.data.model.LoggedInUser;

import java.util.HashMap;
import java.util.Map;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private final LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String email, String password) {
        // can be launched in a separate asynchronous job
        Map<String, Object> json = new HashMap<>();
        json.put("email", email);
        json.put("password", password);
        Result<UserModel> result = loginRepository.login(json);


        if (result instanceof Result.Success) {
            UserModel data = ((Result.Success<UserModel>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data)));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void register(String email, String password, String name) {
        // can be launched in a separate asynchronous job
        Map<String, Object> json = new HashMap<>();
        json.put("email", email);
        json.put("password", password);
        json.put("name", name);
        Result<UserModel> result = loginRepository.register(json);


        if (result instanceof Result.Success) {
            UserModel data = ((Result.Success<UserModel>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data)));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String email, String password, String name) {
        if (!isEmailValid(email)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password, null));
        } else if (!isNameValid(name)) {
            loginFormState.setValue(new LoginFormState(null,null,R.string.invalid_name));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        if (email.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } else {
            return !email.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 1;
    }

    private boolean isNameValid(String name) {
        return name != null && name.trim().length() > 2;
    }
}