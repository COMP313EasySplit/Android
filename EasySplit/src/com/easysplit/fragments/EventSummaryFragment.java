package com.easysplit.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easysplit.base.EasySplitGlobal;
import com.easysplit.base.EventModel;
import com.easysplit.base.ParticipantModel;
import com.example.easysplit.R;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class EventSummaryFragment extends Fragment {

	private Spinner partSpinner;
	//private ArrayList<HashMap<String, String>> plist;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.event_summary_fragment,
				container, false);
		Log.v("Type 1","Creating Event Summary Fragment");
		
		int eventId = Integer.parseInt(getArguments().getString("eventId"));  
		Log.v("Type 1", "Loading event summary: " + eventId);
		
		final EasySplitGlobal esGlobal = (EasySplitGlobal) getActivity().getApplicationContext();
		ArrayList<EventModel> eventList = esGlobal.getEventList();
	    for ( EventModel event : eventList)
	    {
	    	if (eventId == event.EventId)
	    	{
	    		TextView txtESFEventName = (TextView) view.findViewById(R.id.txtESFEventName);
	    		txtESFEventName.setText(event.Name);
	    		TextView txtESFDate = (TextView) view.findViewById(R.id.txtESFDate);
	    		txtESFDate.setText(event.DateCreated);
	    		TextView txtESFBudgetAmount = (TextView) view.findViewById(R.id.txtESFBudgetAmount);
	    		txtESFBudgetAmount.setText(Double.toString(event.Budget));
	    		break;
	    	}
	    }		
		
		partSpinner = (Spinner) view.findViewById(R.id.spESFName);
		//plist = new ArrayList<HashMap<String, String>>();
		ArrayList<ParticipantModel> participantList = esGlobal.getParticipantList();
    	//Log.v("Type 1"," Number of Events found: " + eventList.size());
		final HashMap<String, Integer> spinnerMap = new HashMap<String,Integer>();
		String[] spinnerArray = new String[participantList.size() + 1];
		spinnerMap.put("All",0);
		spinnerArray[0] = "All";
		for (int i=0; i<participantList.size(); i++)
    	{
			spinnerMap.put(participantList.get(i).Firstname + " " + participantList.get(i).Lastname + " " + participantList.get(i).Email, participantList.get(i).Userid);
			spinnerArray[i+1] = participantList.get(i).Firstname + " " + participantList.get(i).Lastname + " " + participantList.get(i).Email;
    	}

		ArrayAdapter<String> adapter =new ArrayAdapter<String>(getActivity().getApplication(),R.layout.spinner_item, spinnerArray);
		adapter.setDropDownViewResource(R.layout.spinner_item);
	    partSpinner.setAdapter(adapter);
	    
	    partSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
                {
                    public void onItemSelected(AdapterView<?> parent, View view,int position,long id) 
                    {
                    	String name = partSpinner.getSelectedItem().toString();
                    	int userId = spinnerMap.get(name);
                    	Log.v("Type 1", "You selected user id : " + userId);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
            );	    
		
		//ArrayList<String> list = new ArrayList<String>();
		//ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
		//		this.getActivity(), android.R.layout.simple_spinner_item, list);
		//dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//partSpinner.setAdapter(dataAdapter);

		//partSpinner.setOnItemSelectedListener(new MyListener());
		
		TableLayout t1 = (TableLayout) view.findViewById(R.id.table);
		TableRow tr = new TableRow(getActivity());
		TextView tv1 = new TextView(getActivity());
		tv1.setText("Items");
		tv1.setBackground(getResources().getDrawable(R.drawable.cell_shape));
		tr.addView(tv1);

		tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		t1.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		return view;
	}

	private class MyListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// Write your logic what you want to do on selecting the item
			/*TableLayout t1 = (TableLayout) arg1.findViewById(R.id.table);
			TableRow tr = new TableRow(getActivity());
			TextView tv1 = new TextView(getActivity());
			tv1.setText("Items");
			tr.addView(tv1);

			tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));

			t1.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));*/
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	}

}
