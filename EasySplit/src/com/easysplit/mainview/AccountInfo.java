package com.easysplit.mainview;

import com.example.easysplit.R;
import com.example.easysplit.R.id;
import com.example.easysplit.R.layout;
import com.example.easysplit.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AccountInfo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_info);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_home:
			Intent homeIntentVE = new Intent(AccountInfo.this, MainActivity.class);
			startActivity(homeIntentVE);
			return true;
		case R.id.action_logout:
			//processLogout(); need to deactivate user credentials
			Intent logoutIntentVE = new Intent(AccountInfo.this, UserLogin.class);
			startActivity(logoutIntentVE);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
