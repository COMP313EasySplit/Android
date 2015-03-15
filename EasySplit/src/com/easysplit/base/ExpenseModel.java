package com.easysplit.base;

import java.util.Date;

public class ExpenseModel {
int ExpenseID;
String Name;
double Amount;
String Place;
UserModel OriginalPayer;
Date  DateCreated;
ExpenseShareModel[] SharedBy;
}
