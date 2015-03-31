package com.easysplit.base;

import java.util.Date;

public class ExpenseModel {

	public double Amount;
	public String  DateCreated;
	public int ExpenseID;
	public String Name;
	public String Place;
	public ExpensePayerModel OriginalPayer;
	public int[] UserId;
	
	public class ExpensePayerModel
	{
		public String Email;
		public String Firstname;
		public String Lastname;
		public int UserId;
	}
}

