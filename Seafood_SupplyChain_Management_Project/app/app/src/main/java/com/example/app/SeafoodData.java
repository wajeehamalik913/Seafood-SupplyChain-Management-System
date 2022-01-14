package com.example.app;


import com.google.firebase.firestore.auth.User;

import java.sql.Timestamp;
import java.util.Date;

public class SeafoodData {
    private String Name;
    private String Type;
    private String Quantity;
    private String Cost;
    private String Status;
    private Date date;
    private String User_Id;

    public SeafoodData(){}
    public SeafoodData(String Name,String Type,String Quantity,String Cost,String Status,Date date,String User_Id){
        this.Name=Name;
        this.Type=Type;
        this.Quantity=Quantity;
        this.Cost=Cost;
        this.Status=Status;
        this.date=date;
        this.User_Id=User_Id;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public String getQuantity() {
        return Quantity;
    }

    public String getCost() {
        return Cost;
    }

    public String getStatus() {
        return Status;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public Date getDate() {
        return date;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

}
