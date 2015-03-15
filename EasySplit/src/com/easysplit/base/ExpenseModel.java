package com.easysplit.base;

import java.util.Date;

public class ExpenseModel {

	public int ExpenseID;
	public String Name;
	public double Amount;
	public String Place;
	public UserModel OriginalPayer;
	public Date  DateCreated;
	public ExpenseShareModel[] SharedBy;
}
