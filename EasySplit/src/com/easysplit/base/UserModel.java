package com.easysplit.base;

import java.util.Date;

public class UserModel {

	int UserID;
	String FirstName;
	String LastName;
	String Email;
	String Passcode;
	String Salt;
	String Status;
	Date DateCreated;
	EventModel[] HostEvents;
	EventModel[] ParticitEvents;
}
