package com.easysplit.mainview;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpException;

import com.easysplit.base.EasySplitGlobal;
import com.easysplit.base.EventModel;
import com.easysplit.base.SHA1Helper;
import com.easysplit.base.UserModel;
import com.easysplit.net.EasySplitRequest;
import com.easysplit.net.Parse;
import com.example.easysplit.R;
import com.example.easysplit.R.id;
import com.example.easysplit.R.layout;
import com.example.easysplit.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class UserLogin extends Activity {

	final String key = "faab1810-5039-4741-abdc-51a7b628b57f";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login);
		
		Button btnULLogin = (Button) findViewById(R.id.btnULLogin);
		btnULLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText etULEmail = (EditText) findViewById(R.id.etULEmail);
				EditText etULPassword = (EditText) findViewById(R.id.etULPassword);

				String salt = etULEmail.getText().toString();
				String password = etULPassword.getText().toString();
				
				if (salt.length() > 0 && password.length() > 0)
				{
					String passcode;
					try {
						passcode = SHA1Helper.SHA1(salt) + SHA1Helper.SHA1(password) + SHA1Helper.SHA1(key);
						passcode = SHA1Helper.SHA1(passcode);
						Log.v("Type 1", "Pass code is: " + passcode);
						
						login = new Login();
						login.execute(salt, passcode);
						
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
    private Login login;
    private class Login extends AsyncTask<String, Void, String> {
        @Override
		protected String doInBackground(String... params) {
			String result = null;
			EasySplitRequest request = new EasySplitRequest(getApplicationContext());

    		final EasySplitGlobal esGlobal = (EasySplitGlobal) getApplicationContext();
        	//int hostID = esGlobal.getHostID();
    		String email = params[0];
    		String passcode = params[1];
			
			try {
				result = request.login(email, passcode);	// retrieve event list by host id, json string of eventModel is returned
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;       }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
        	UserModel user = Parse.getLoginUser(result);
        	if (user != null)
        	{
        		final EasySplitGlobal esGlobal = (EasySplitGlobal) getApplicationContext();
        		esGlobal.setCurrentUser(user);
        		// start activity
                Intent viewEventIntent = new Intent(getApplicationContext(), com.easysplit.mainview.MainActivity.class);
                startActivity(viewEventIntent);
        	}
        	else
        	{
        		Toast.makeText(getApplicationContext(), "Email or Password is invalid!",
        				   Toast.LENGTH_LONG).show();
        	}
       }
    }
	
}
