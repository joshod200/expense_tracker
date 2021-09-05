package com.joshod.expense_tracker.models;

import com.orm.SugarRecord;

public class Account extends SugarRecord {
    public String balance;

    public Account(){
        super();
    }

    public Account(String balance){
        this.balance = balance;
    }

    public void debit(Double amount){
        Double b = Double.parseDouble(balance);
        b -= amount;
        balance = b.toString();
        save();
    }

    public void credit(Double amount){
        Double b = Double.parseDouble(balance);
        b += amount;
        balance = b.toString();
        save();
    }

}