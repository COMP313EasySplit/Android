package com.easysplit.mainview;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import com.easysplit.fragments.EventHostTabFragment;
import com.easysplit.fragments.ParticipantTabFragment;
import com.example.easysplit.R;

public class MainActivity extends Activity {

	// Create action bar tabs and fragments
	ActionBar.Tab tab1, tab2;
	Fragment fragmentTab1 = new EventHostTabFragment();
	Fragment fragmentTab2 = new ParticipantTabFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Action bar tab code
		ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		tab1 = actionBar.newTab().setText("Event Host");
		tab2 = actionBar.newTab().setText("Participant");
		tab1.setTabListener(new MyTabListener(fragmentTab1));
		tab2.setTabListener(new MyTabListener(fragmentTab2));
		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		switch (item.getItemId()) {
		case R.id.action_home:
			Intent homeIntent = new Intent(MainActivity.this, MainActivity.class);
			startActivity(homeIntent);
			return true;
		case R.id.action_addNewEvent:
			Intent newEventIntent = new Intent(MainActivity.this, NewEvent.class);
			startActivity(newEventIntent);
			return true;
		case R.id.action_accountInfo:
			//openAccountInfo();
			return true;
		case R.id.action_logout:
			//processLogout();
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
