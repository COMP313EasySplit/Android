package com.easysplit.base;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;

public class EasySplitGlobal extends Application{

	// how to use this class
	// set  global variable
	// ((EasySplitGlobal) this.getApplication()).setHostID(1);
    // get global variable
	// String s = ((EasySplitGlobal) this.getApplication()).getHostID();
	
	private int hostID;
    public int getHostID() {
        return hostID;
    }

    public void setHostID(int HostID) {
        this.hostID = HostID;
    }
	    
	// participant arraylist of one event
    private ArrayList<ParticipantModel> participantList;
	public ArrayList<ParticipantModel> getParticipantList()
	{
		return participantList;
	}
	public void setParticipantList(ArrayList<ParticipantModel> pList)
	{
		this.participantList = pList;
	}
	
	// expense arraylist of one event
	private ArrayList<ExpenseModel> expenseList;
	public ArrayList<ExpenseModel> getExpenseList()
	{
		return expenseList;
	}
	public void setExpenseList(ArrayList<ExpenseModel> eList)
	{
		this.expenseList = eList;
	}
	
	// event arraylist of one host
	private ArrayList<EventModel> eventList;
	public ArrayList<EventModel> getEventList(String source)
	{
		if (source.equals("host"))
		{
		return eventList;
		}
		else	// participant
		{
			return participantEventList;
		}
	}
	public void setEventList(ArrayList<EventModel> eList)
	{
		this.eventList = eList;
	}	    
	// event arraylist of one participant
	private ArrayList<EventModel> participantEventList;
	//public ArrayList<EventModel> getParticipantEventList()
	//{
	//	return participantEventList;
	//}
	public void setParticipantEventList(ArrayList<EventModel> eList)
	{
		this.participantEventList = eList;
	}
}
