package com.easysplit.base;

import android.app.Application;

public class EasySplitGlobal extends Application{

	private int hostID;
	    public int getHostID() {
	        return hostID;
	    }

	    public void setHostID(int HostID) {
	        this.hostID = HostID;
	    }
	    
	//set  global variable
	//((EasySplitGlobal) this.getApplication()).setHostID(1);
    //get global variable
	//String s = ((EasySplitGlobal) this.getApplication()).getHostID();
}
