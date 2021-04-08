package com.example.application.backend.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * User
 *
 * @author Kaan BİNAT
 * @since 5.230.0
 */
@Entity
public class User extends AbstractEntity {

    @NotNull
    @NotEmpty
    private String userName;

    @NotNull
    @NotEmpty
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
