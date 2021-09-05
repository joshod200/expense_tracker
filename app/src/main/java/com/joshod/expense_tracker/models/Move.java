package com.joshod.expense_tracker.models;

import com.orm.SugarRecord;
import com.orm.SugarTransactionHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Move extends SugarRecord {
    public String name;
    public int type;
    public Account account;
    private static int INCOME = 1;
    public static int EXPENSE = 2;
    public String amount;
    public String created_at;

    public Move(){
        super();
    }

    public Move(String name, int type, String amount, Account account){
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.account = account;
    }


    public static void doMove(Move move){
        SugarTransactionHelper.doInTransaction(new SugarTransactionHelper.Callback() {
            @Override
            public void manipulateInTransaction() {
                // The code that you want to be executed inside the same transaction goes here :)
                double a = Double.parseDouble(move.amount);
                if(move.type == INCOME) move.account.credit(a);
                else if(move.type == EXPENSE) move.account.debit(a);
                move.created_at = new SimpleDateFormat("MMM d, hh:mm", Locale.ENGLISH).format(Calendar.getInstance().getTime());
                move.save();
            }
        });
    }

    public static void undoMove(Move move){
        SugarTransactionHelper.doInTransaction(new SugarTransactionHelper.Callback() {
            @Override
            public void manipulateInTransaction() {
                // The code that you want to be executed inside the same transaction goes here :)
                double a = Double.parseDouble(move.amount);
                if(move.type == INCOME) move.account.debit(a);
                else if(move.type == EXPENSE) move.account.credit(a);
                move.delete();
            }
        });
    }

}
