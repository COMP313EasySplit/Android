package com.easysplit.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;
import com.easysplit.base.EasySplitGlobal;
import com.easysplit.base.EventModel;
import com.easysplit.base.ParticipantModel;
import com.easysplit.mainview.ViewEvent;
import com.easysplit.net.EasySplitRequest;
import com.easysplit.net.Parse;
import com.example.easysplit.R;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class EventSummaryFragment extends Fragment {

	private Activity thisActivity;
	private Spinner partSpinner;
	//private ArrayList<HashMap<String, String>> plist;
	private int eventId;
	private String source;
	TableLayout tableSummary;

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.event_summary_fragment,
				container, false);
		
		if(getResources().getBoolean(R.bool.portrait_only)){
	        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    }
		else 
		{
			getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);			
		} 
		thisActivity = getActivity();
		Log.v("Type 1","Creating Event Summary Fragment");
		
		eventId = Integer.parseInt(getArguments().getString("eventId"));
		source = getArguments().getString("source");
		Log.v("Type 1", "Loading event summary: " + eventId + " Source: " + source);
		
		final EasySplitGlobal esGlobal = (EasySplitGlobal) getActivity().getApplicationContext();
		ArrayList<EventModel> eventList = esGlobal.getEventList(source);
	    for ( EventModel event : eventList)
	    {
	    	if (eventId == event.EventId)
	    	{	    		
	    		TextView txtESFDate = (TextView) view.findViewById(R.id.txtESFDate);
	    		txtESFDate.setText(event.DateCreated);
	    		TextView txtESFBudgetAmount = (TextView) view.findViewById(R.id.txtESFBudgetAmount);
	    		txtESFBudgetAmount.setText(Double.toString(event.Budget));
	    		TextView txtESFTotalAmount = (TextView) view.findViewById(R.id.txtESFTotalAmount);
	    		txtESFTotalAmount.setText(Double.toString(event.TotalSpend));
	    		TextView txtESFEventName = (TextView) view.findViewById(R.id.txtESFEventName);
	    		txtESFEventName.setText("Summary for " + event.Name);
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
	    
	    //partSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
        //       {
        //            public void onItemSelected(AdapterView<?> parent, View view,int position,long id) 
        //            {
        //            	String name = partSpinner.getSelectedItem().toString();
        //            	int userId = spinnerMap.get(name);
        //            	Log.v("Type 1", "You selected user id : " + userId);
        //            }
        //
        //            public void onNothingSelected(AdapterView<?> parent) {
        //            }
        //        }
        //    );	    
		
		//ArrayList<String> list = new ArrayList<String>();
		//ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
		//		this.getActivity(), android.R.layout.simple_spinner_item, list);
		//dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//partSpinner.setAdapter(dataAdapter);

		//partSpinner.setOnItemSelectedListener(new MyListener());
		
	    tableSummary =(TableLayout) view.findViewById(R.id.table);
	    displaySummary = new DisplaySummary(thisActivity);
	    displaySummary.execute(eventId);
	    
	    ((ViewEvent)getActivity()).setFragmentRefreshListener(new ViewEvent.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
            	//updateData();
            	Toast.makeText(getActivity().getBaseContext(),"Refreshed", 
                        Toast.LENGTH_SHORT).show();
            }
        });

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
	
	DisplaySummary displaySummary;
    private class DisplaySummary extends AsyncTask<Integer, Void, ArrayList<ArrayList<String>>> {
    	private Activity mActivity;
    	public DisplaySummary(Activity activity) {
    		mActivity = activity;
    	} 
		@Override
		protected ArrayList<ArrayList<String>> doInBackground(Integer... params) {
			ArrayList<ArrayList<String>> result = null;
			try {
				EasySplitRequest request = new EasySplitRequest(mActivity.getApplication());
				result = Parse.getSummary(request.getSummary(params[0]));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return result;
		}
        @Override
        protected void onPostExecute(ArrayList<ArrayList<String>> result) {
        	
        	
        	if (result.size()>0)
        	{
        		
    		  for(int i=0; i<result.size(); i++) {
    			    
    			     TableRow tablerow = new TableRow(mActivity);
    			     tablerow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
    			         			     
    			     //set row
    			     ArrayList<String> row = result.get(i);
    			     for(int j=0; j<row.size(); j++) {
    			         TextView actualData = new TextView(mActivity);
    			         //set properties
    			         actualData.setBackground(getResources().getDrawable(R.drawable.cell_shape));
    			         actualData.setText(" " + row.get(j) + " ");    			    
    			         
    			         if(getResources().getBoolean(R.bool.tablet)){
    			        	 actualData.setTextSize(20);
    			         }
    			         else{    			        	 
    			        	 actualData.setTextSize(16);
    			         }
    			         

    			         if (j>0) 
    			        	 {
    			        	 	actualData.setGravity(Gravity.RIGHT);
    			        	 	actualData.setMinimumWidth(100);
        			         }
    			         tablerow.addView(actualData);
    			     }
    			    
    			     tableSummary.addView(tablerow,new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    			  }
        		  
        		//Toast.makeText(mActivity.getBaseContext(), result.size(), Toast.LENGTH_SHORT).show();
        		
        	}
        	else
        	{
        		Toast.makeText(mActivity.getBaseContext(), "Error.", Toast.LENGTH_SHORT).show();
        	}
        }
    	
    }
    
    public void updateData() {
    	
    	displaySummary = new DisplaySummary(thisActivity);
    	displaySummary.execute(eventId);
		
    }

}
