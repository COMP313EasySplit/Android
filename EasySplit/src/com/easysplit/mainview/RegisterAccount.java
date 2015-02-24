package com.easysplit.mainview;

import com.example.easysplit.R;
import com.example.easysplit.R.id;
import com.example.easysplit.R.layout;
import com.example.easysplit.R.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class RegisterAccount extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_account);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_account, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
	
		switch (item.getItemId()) {
		case R.id.action_cancel:
			// openCancel();
			return true;		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
