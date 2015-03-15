package com.easysplit.base;

import java.util.Date;

public class EventModel {
    
	int eventId;
    String name;
    UserModel EvehHost;
    double budget;
    String status;
    Date dateCreated;
	ExpenseModel[] Expenses;
	UserModel[] Participants;
    
}
