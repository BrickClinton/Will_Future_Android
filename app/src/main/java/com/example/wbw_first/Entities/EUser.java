package com.example.wbw_first.Entities;

import java.io.Serializable;

public class EUser implements Serializable {
    private int iduser;
    private String nameuser;
    private String lastname;

    public EUser() {
    }

    public EUser(int iduser, String nameuser, String lastname) {
        this.iduser = iduser;
        this.nameuser = nameuser;
        this.lastname = lastname;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getNameuser() {
        return nameuser;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "EUser{" +
                "iduser=" + iduser +
                ", nameuser='" + nameuser + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
