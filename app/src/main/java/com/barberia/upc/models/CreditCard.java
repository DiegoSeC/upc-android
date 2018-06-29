package com.barberia.upc.models;

public class CreditCard {
    private String name;
    private String number;
    private String ccv;
    private String type;
    private String exp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public CreditCard(String name, String number, String ccv, String type, String exp) {
        this.name = name;
        this.number = number;
        this.ccv = ccv;
        this.type = type;
        this.exp = exp;
    }
}
