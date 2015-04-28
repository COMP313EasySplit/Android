package com.easysplit.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpException;

import com.easysplit.base.EasySplitGlobal;
import com.easysplit.base.EventModel;
import com.easysplit.base.UserModel;
import com.easysplit.mainview.MainActivity;
import com.easysplit.net.EasySplitRequest;
import com.easysplit.net.Parse;
import com.example.easysplit.R;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ParticipantTabFragment extends Fragment {
	private Context thiscontext;
	private View fragment_v;
	private ArrayList<HashMap<String, String>> elist;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.participant_tab_fragment, container, false);
		//TextView textview = (TextView) view.findViewById(R.id.tabtextview2);
		//textview.setText("tab 2");
		
		if(getResources().getBoolean(R.bool.portrait_only)){
	        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    }
		else 
		{
			getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);			
		} 
		elist = new ArrayList<HashMap<String, String>>();
		
		thiscontext = getActivity().getApplicationContext();
		
		fragment_v=view;
        
		loadHostEvent = new LoadHostEvent();
		loadHostEvent.execute();
		
		((MainActivity)getActivity()).setFragmentRefreshListener(new MainActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
            	updateData();
            	Toast.makeText(getActivity().getBaseContext(),"Refreshed", 
                        Toast.LENGTH_SHORT).show();
            }
        });
		
		return view;
	}
	   private LoadHostEvent loadHostEvent;
	    private class LoadHostEvent extends AsyncTask<String, Void, String> {
	        @Override
			protected String doInBackground(String... params) {
				String result = null;
				EasySplitRequest request = new EasySplitRequest(thiscontext);

	    		final EasySplitGlobal esGlobal = (EasySplitGlobal) getActivity().getApplicationContext();
	        	UserModel user = esGlobal.getCurrentUser();			
				
				try {
					result = request.getParticipantEvent(user.UserId);	// retrieve event list by host id, json string of eventModel is returned
				} catch (IOException e) {
					e.printStackTrace();
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return result;       }
	        // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(String result) {
	        	
	        	ArrayList<EventModel> eventList = Parse.getEventList(result);
	    		final EasySplitGlobal esGlobal = (EasySplitGlobal) getActivity().getApplicationContext();
	    		esGlobal.setParticipantEventList(eventList); 
	        	//Log.v("Type 1"," Number of Events found: " + eventList.size());
	    		elist.clear();
	        	for (EventModel event : eventList)
	        	{
	        		HashMap<String, String> map = new HashMap<String, String>();
	        		map.put("EventId", Integer.toString(event.EventId));
	                map.put("txtPLVDEventName", event.Name);
	                map.put("txtPLVDStatus", event.Status);
	                map.put("txtPLVDamount", "$" + Double.toString( event.Budget) );
	                elist.add(map);
	        	}
	        	
	            SimpleAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(),
	            		elist,
	            		R.layout.participant_listview_details,
	            		new String[]{"txtPLVDEventName","txtPLVDStatus","txtPLVDamount"},
	            		new int[]{R.id.txtPLVDEventName, R.id.txtPLVDStatus, R.id.txtPLVDamount});
	            ListView hostEventList = (ListView) fragment_v.findViewById(R.id.lvPTFPartEventList);
	            hostEventList.setAdapter(adapter);
	        	adapter.notifyDataSetChanged();
	            
	            hostEventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	                @Override
	                public void onItemClick(AdapterView<?> parent, View view,
	                                        int position, long id) {
	                	//Log.v("Type 1", "Clicked");
	                   int eventId = Integer.parseInt(elist.get(position).get("EventId"));
	                   
	                   Intent viewEventIntent = new Intent(getActivity().getApplicationContext(), com.easysplit.mainview.ViewEvent.class);
	                   viewEventIntent.putExtra("eventId", Integer.toString(eventId)); //Optional parameters
	                   viewEventIntent.putExtra("source", "participant");
	                   startActivity(viewEventIntent);
	                   
	                }
	            });
	       }
	    }
	    
	    public void updateData() {
	    	
	    	loadHostEvent = new LoadHostEvent();
			loadHostEvent.execute();
			
	    }
	}
