package com.easysplit.mainview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;
import org.apache.http.HttpException;
import com.easysplit.base.EasySplitGlobal;
import com.easysplit.base.ParticipantModel;
import com.easysplit.base.UserModel;
import com.easysplit.net.EasySplitRequest;
import com.example.easysplit.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class NewEvent extends Activity {
	final Context context = this;
	final Application application = this.getApplication();
	ArrayList<ParticipantModel> participantList;
	ListView participantListView;
	UserModel user;
	EditText etNEName;
	EditText etNEBudget;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_event);
		
		if(getResources().getBoolean(R.bool.portrait_only)){
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    }
		
		addEvent = new AddEvent(NewEvent.this);
    	
    	participantList = new ArrayList<ParticipantModel>();
    	((EasySplitGlobal) getApplication()).setParticipantList(participantList);
		
    	user = ((EasySplitGlobal) getApplication()).getCurrentUser();
    	ParticipantModel newParticipant = new ParticipantModel();
    	newParticipant.Firstname = user.FirstName;
    	newParticipant.Lastname = user.LastName;
    	newParticipant.Email = user.Email;
    	newParticipant.Userid = user.UserId;

    	participantList.add(newParticipant);
    	etNEName = (EditText) findViewById(R.id.etNEEventName);	// get name
    	etNEBudget = (EditText) findViewById(R.id.etNEBudget);		// get budge
    	
    	participantListView = (ListView) findViewById(R.id.lvNEparticipants);
    	refreshParticipantListView();
    			
		Button btnNESearch = (Button) findViewById(R.id.btnNESearch);
		btnNESearch.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
			
				// get input_participant_dialog.xml view
				LayoutInflater li = LayoutInflater.from(context);
				View input_participant_View = li.inflate(R.layout.input_participant_dialog, null);
 
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(input_participant_View);
 
				final EditText firstName = (EditText) input_participant_View.findViewById(R.id.etIPDFName);
				final EditText lastName = (EditText)  input_participant_View.findViewById(R.id.etIPDLName);
				final EditText email = (EditText)  input_participant_View.findViewById(R.id.etIPDEMail);
 
				// set dialog message
				alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("OK",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						// get user input and set it to list
					    	if ( !TextUtils.isEmpty(firstName.getText().toString().trim())
					    		&& !TextUtils.isEmpty(lastName.getText().toString().trim())
					    		&& !TextUtils.isEmpty(email.getText().toString().trim()) )
					    		{
							    	ParticipantModel newParticipant = new ParticipantModel();
							    	newParticipant.Firstname = firstName.getText().toString();
							    	newParticipant.Lastname = lastName.getText().toString();
							    	newParticipant.Email = email.getText().toString();
		
							    	participantList.add(newParticipant);
					    		}
					    	refreshParticipantListView();
					    }
					  })
					.setNegativeButton("Cancel",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					    }
					  });
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				// show it
				alertDialog.show();					
			}
		});
	}
	
	private void refreshParticipantListView()
	{
		ArrayList<HashMap<String, String>> plist = new ArrayList<HashMap<String, String>>();
		for (ParticipantModel participant : participantList)
    	{
    		HashMap<String, String> map = new HashMap<String, String>();
    		map.put("UserId", Integer.toString(participant.Userid));
            map.put("txtEPLVDFullname", participant.Firstname + " " + participant.Lastname);
            map.put("txtEPLVDEmail", participant.Email);
            plist.add(map);
    	}
		
		// bind to list view
        ListAdapter adapter = new SimpleAdapter(getApplicationContext(),
        		plist,
        		R.layout.event_detail_participant_listview_detail,
        		new String[]{"txtEPLVDFullname","txtEPLVDEmail"},
        		new int[]{R.id.txtEPLVDFullname, R.id.txtEPLVDEmail});
        participantListView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_event, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_cancel:
			Intent homeIntentNEv = new Intent(NewEvent.this, MainActivity.class);
			startActivity(homeIntentNEv);
			return true;
		case R.id.action_accept:
	            	if (!TextUtils.isEmpty(etNEName.getText().toString().trim()))
	            	{
	            		addEvent.execute();	// call add event request to connect to web service
	            	}
	            	else
	            	{
	            		Toast.makeText(getApplicationContext(), "Event name cannot be empty.", Toast.LENGTH_SHORT).show();
	            	}
			return true;	
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) 
                return true;
            else
                return false;   
    }

    private AddEvent addEvent;
    private class AddEvent extends AsyncTask<String, Void, String> {
    	private Activity mActivity;
    	public AddEvent(Activity activity) {
    		mActivity = activity;
    	} 
    	
        @Override
		protected String doInBackground(String... params) {
			String result = null;
			EasySplitRequest request = new EasySplitRequest(NewEvent.this);	// create reqeust


        	String name = etNEName.getText().toString();
        	double budget = Double.parseDouble( etNEBudget.getText().toString() );
        	
    		final EasySplitGlobal esGlobal = (EasySplitGlobal) getApplicationContext();	// get login user id
        	UserModel user  = esGlobal.getCurrentUser();
			
			try {
				result = request.addEvent(name,budget,user.UserId);	// call web service add event
				
				int newEventID = Integer.parseInt(result);
				if (newEventID>0)
				{
					for (ParticipantModel participant : participantList)
					{	// add event participants
						result = request.addEventParticipants(Integer.toString(newEventID),
								participant.Firstname,
								participant.Lastname,
								participant.Email);
					}
				}
				
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
        	
        	if (result.equals("true"))
        	{
        		Toast.makeText(getBaseContext(), "Event has been saved.", Toast.LENGTH_SHORT).show();
        		mActivity.finish();
        	}
        	else
        	{
        		Toast.makeText(getBaseContext(), "Error: cannot save event", Toast.LENGTH_SHORT).show();
        	}
       }
    }
}
