package com.example.notification.function;

import java.io.Serializable;

public class ViewDto implements Serializable {
    private String name;
    private String tel;
    private String mail;

    public ViewDto() {
    }

    public ViewDto(String name, String tel, String mail) {
        this.name = name;
        this.tel = tel;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}