package com.easysplit.mainview;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpException;

import com.easysplit.base.EasySplitGlobal;
import com.easysplit.net.EasySplitRequest;
import com.example.easysplit.R;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewEvent extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_event);
		
		addEvent = new AddEvent();
		
		Button btnNESave = (Button) findViewById(R.id.btnNESave);
		btnNESave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	if(isConnected()){
            		Toast.makeText(getApplicationContext(), "You are conncted",  Toast.LENGTH_SHORT).show();
                }
                else{
            		Toast.makeText(getApplicationContext(), "You are NOT conncted",  Toast.LENGTH_SHORT).show();
                }
            	
           	
            	addEvent.execute();
            }
        });
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
        @Override
		protected String doInBackground(String... params) {
			String result = null;
			EasySplitRequest request = new EasySplitRequest(NewEvent.this);

        	EditText etNEName = (EditText) findViewById(R.id.etNEEventName);
        	String name = etNEName.getText().toString();
        	
        	EditText etNEBudget = (EditText) findViewById(R.id.etNEBudget);
        	double budget = Double.parseDouble( etNEBudget.getText().toString() );
        	
    		final EasySplitGlobal esGlobal = (EasySplitGlobal) getApplicationContext();
        	int hostID = esGlobal.getHostID();
			
			
			try {
				result = request.addEvent(name,budget,hostID);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;       }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
       }
    }
}
