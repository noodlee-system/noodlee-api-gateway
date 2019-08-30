package com.noodleesystem.authentication.model;

public class UserLoginCommand {
    private String username;
    private String password;

    public UserLoginCommand() { }

    public UserLoginCommand(final String _username, final String _password) {
        username = _username;
        password = _password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
