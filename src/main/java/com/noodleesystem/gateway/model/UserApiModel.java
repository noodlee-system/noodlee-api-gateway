package com.noodleesystem.gateway.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class UserApiModel {
    private int id;
    private String username;
    private String password;
    private String role;

    public UserApiModel() { }

    public UserApiModel(final String _username, final String _password) {
        this.username = _username;
        this.password = _password;
        this.role = "";
    }

    public UserApiModel(UserCredentialsModel _userCredentials) {
        this.username = _userCredentials.getUsername();
        this.password = _userCredentials.getPassword();
        this.role = "";
    }

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "role", nullable = false)
    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }
}
