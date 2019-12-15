package com.example.billbook2;

import org.litepal.crud.LitePalSupport;

public class Count extends LitePalSupport {
    private Double money;
    private String type;
    private String date;
    private String describe;
    private  int id;

    public Count() {
    }
    public Count(Double money, String date, String type, String describe,int id) {
        this.money = money;
        this.date = date;
        this.type = type;
        this.describe = describe;
        this.id=id;
    }
    public int getId() {

        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Double getMoney() {

        return money;
    }
    public void setMoney(Double money) {
        this.money = money;
    }
    public String getType()    {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDescribe() {
        return describe;
    }
    public void setDescribe(String describe)   {
        this.describe = describe;
    }
}


