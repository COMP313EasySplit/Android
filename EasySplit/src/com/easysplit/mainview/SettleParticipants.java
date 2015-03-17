package com.easysplit.mainview;

import com.example.easysplit.R;
import com.example.easysplit.R.id;
import com.example.easysplit.R.layout;
import com.example.easysplit.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SettleParticipants extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settle_participants);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settle_participants, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_home:
			Intent homeIntentSP = new Intent(SettleParticipants.this, MainActivity.class);
			startActivity(homeIntentSP);
			return true;
		case R.id.action_accountInfo:
			Intent accountInfoIntentSP = new Intent(SettleParticipants.this, AccountInfo.class);
			startActivity(accountInfoIntentSP);
			return true;
		case R.id.action_logout:
			//processLogout(); need to deactivate user credentials
			Intent logoutIntentSP = new Intent(SettleParticipants.this, UserLogin.class);
			startActivity(logoutIntentSP);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
