package com.noodleesystem.gateway.model;

public class UserAuthenticationCommand {
    private String username;
    private String password;

    public UserAuthenticationCommand() { }

    public UserAuthenticationCommand(final String _username, final String _password) {
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
