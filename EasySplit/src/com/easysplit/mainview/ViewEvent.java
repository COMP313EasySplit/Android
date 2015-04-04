package com.easysplit.mainview;

import com.easysplit.fragments.EventDetailsFragment;
import com.easysplit.fragments.EventExpensesFragment;
import com.easysplit.fragments.EventHostTabFragment;
import com.easysplit.fragments.EventSummaryFragment;
import com.easysplit.fragments.ParticipantTabFragment;
import com.easysplit.mainview.MainActivity.MyTabListener;
import com.example.easysplit.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ViewEvent extends Activity {

	// Create action bar tabs and fragments
		ActionBar.Tab tab1, tab2, tab3;
		Fragment fragmentTab1 = new EventDetailsFragment();
		Fragment fragmentTab2 = new EventExpensesFragment();
		Fragment fragmentTab3 = new EventSummaryFragment();
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_event);
		
		// Action bar tab code
				ActionBar bar = getActionBar();
				bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
				ActionBar actionBar = getActionBar();
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

				tab1 = actionBar.newTab().setText("Details");
				tab2 = actionBar.newTab().setText("Expenses");
				tab3 = actionBar.newTab().setText("Summary");
				tab1.setTabListener(new MyTabListener(fragmentTab1));
				tab2.setTabListener(new MyTabListener(fragmentTab2));
				tab3.setTabListener(new MyTabListener(fragmentTab3));
				actionBar.addTab(tab1);
				actionBar.addTab(tab2);
				actionBar.addTab(tab3);
				
				Intent intent = getIntent();
				String eventId = intent.getStringExtra("eventId");
				String source = intent.getStringExtra("source");
				Log.v("Type 1", "Creating View Event Activity");				
				
				Bundle eventBundle = new Bundle();
				eventBundle.putString("eventId", eventId);
				eventBundle.putString("source", source); // host or participant
				fragmentTab1.setArguments(eventBundle);
				fragmentTab2.setArguments(eventBundle);
				fragmentTab3.setArguments(eventBundle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_event, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_home:
			Intent homeIntentVE = new Intent(ViewEvent.this, MainActivity.class);
			startActivity(homeIntentVE);
			return true;
		case R.id.action_addNewExpense:
			Intent newExIntentVE = new Intent(ViewEvent.this, NewExpense.class);
			startActivity(newExIntentVE);
			return true;
		case R.id.action_accountInfo:
			Intent accountInfoIntentVE = new Intent(ViewEvent.this, AccountInfo.class);
			startActivity(accountInfoIntentVE);
			return true;
		case R.id.action_logout:
			//processLogout(); need to deactivate user credentials
			Intent logoutIntentVE = new Intent(ViewEvent.this, UserLogin.class);
			startActivity(logoutIntentVE);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	// listener for tabs
		public class MyTabListener implements ActionBar.TabListener {
			Fragment fragment;

			public MyTabListener(Fragment fragment) {
				this.fragment = fragment;
			}

			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				ft.replace(R.id.fragment_container, fragment);
			}

			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				ft.remove(fragment);
			}

			public void onTabReselected(Tab tab, FragmentTransaction ft) {
			}
		}
}
