package com.easysplit.mainview;

import com.example.easysplit.R;
import com.example.easysplit.R.id;
import com.example.easysplit.R.layout;
import com.example.easysplit.R.menu;
import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

	public class UserManual extends FragmentActivity {
		 
	    CustomPagerAdapter mCustomPagerAdapter;
	    ViewPager mViewPager;
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_user_manual);
	        getActionBar().hide();
	        
	        //if(getResources().getBoolean(R.bool.portrait_only)){
		    //    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		    //}
	        
	        mCustomPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), this);
	 
	        mViewPager = (ViewPager) findViewById(R.id.pager);
	        mViewPager.setAdapter(mCustomPagerAdapter);
	    }
	 
	    class CustomPagerAdapter extends FragmentPagerAdapter {
	 
	        Context mContext;
	 
	        public CustomPagerAdapter(FragmentManager fm, Context context)
	        {
	            super(fm);
	            mContext = context;
	        }
	 
	        @Override
	        public Fragment getItem(int position) {
	 
	        	Fragment fragment;
	        	
	        	Bundle args = new Bundle();
	            args.putInt("page_position", position + 1);
	            
	            if(getResources().getBoolean(R.bool.tablet)){
	            	if (position == 0){	        		
		        		fragment = new UserManualFragment1();
		        	}
		        	else if (position == 1){
		        		fragment = new UserManualFragment2();	        		
		        	}
		        	else if (position == 2){
		        		fragment = new UserManualFragment3();	        		
		        	}
		        	else {
		        		fragment = new UserManualFragment6();       		
		        	}

			    }
	            else {
			        	if (position == 0){	        		
			        		fragment = new UserManualFragment1();
			        	}
			        	else if (position == 1){
			        		fragment = new UserManualFragment2();	        		
			        	}
			        	else if (position == 2){
			        		fragment = new UserManualFragment3();	        		
			        	}
			        	else if (position == 3){
			        		fragment = new UserManualFragment4();       		
			        	}
			        	else if (position == 4){
			        		fragment = new UserManualFragment5();	        		
			        	}
			        	else {
			        		fragment = new UserManualFragment6();	        		
			        	}
	            }

	            fragment.setArguments(args);
	            return fragment;
	        }
	 
	        @Override
	        public int getCount() {
	        
	        	if(getResources().getBoolean(R.bool.tablet)){
	        		return 4;
	        	}
	        	else
	        	{
	        		return 6;	        		
	        	}
	        }
	 
	        @Override
	        public CharSequence getPageTitle(int position) {
	        	switch (position) {
                case 0:                	
                    return "Tab One";
                case 1:
                    return "Tab Two";
                case 2:
                    return "Tab Three";
                case 3:
                    return "Tab Four";
                case 4:
                    return "Tab Five";
            }
	            return null;	        	
	        }	        
	    }
	 
	    class UserManualFragment1 extends Fragment {
	        @Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	            View rootView = inflater.inflate(R.layout.usermanual_p1, container, false);
	            Bundle args = getArguments();
	            	            
	            TextView t2;
	            t2 = ((TextView) rootView.findViewById(R.id.link));
	           
	            t2.setOnClickListener(new View.OnClickListener() {

	                @Override
	                public void onClick(View v) {
	                 Intent intent = new Intent(getActivity(), UserLogin.class);
	      	    	  startActivity(intent);
	                }
	            });
	            return rootView;
	        }
	    }

	    
	    class UserManualFragment2 extends Fragment {
	        @Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	            View rootView = inflater.inflate(R.layout.usermanual_p2, container, false);
	            Bundle args = getArguments();
	           // ((TextView) rootView.findViewById(R.id.textView2)).setText("Page " + args.getInt("page_position"));
	            
	            
	            return rootView;
	        }
	    }
	    
	    class UserManualFragment3 extends Fragment {
	        @Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	            View rootView = inflater.inflate(R.layout.usermanual_p3, container, false);
	            Bundle args = getArguments();
	            //((TextView) rootView.findViewById(R.id.textView3)).setText("Page " + args.getInt("page_position"));
	 
	            return rootView;
	        }
	    }
	    
	    class UserManualFragment4 extends Fragment {
	        @Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	        	if(getResources().getBoolean(R.bool.tablet)){
	        		return null;	        		
	        	}
	        	else{
	            View rootView = inflater.inflate(R.layout.usermanual_p4, container, false);
	            Bundle args = getArguments();
	            //((TextView) rootView.findViewById(R.id.textView3)).setText("Page " + args.getInt("page_position"));
	 
	            return rootView;
	        	}
	        }
	    }
	    
	    class UserManualFragment5 extends Fragment {
	        @Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	        	if(getResources().getBoolean(R.bool.tablet)){
	        		return null;	        		
	        	}
	        	else{
	        	
	            View rootView = inflater.inflate(R.layout.usermanual_p5, container, false);
	            Bundle args = getArguments();
	            //((TextView) rootView.findViewById(R.id.textView3)).setText("Page " + args.getInt("page_position"));
	 
	            return rootView;
	        	}
	        	}	        
	    }
	    
	    class UserManualFragment6 extends Fragment {
	        @Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	            View rootView = inflater.inflate(R.layout.usermanual_p6, container, false);
	            Bundle args = getArguments();
	            //((TextView) rootView.findViewById(R.id.textView3)).setText("Page " + args.getInt("page_position"));
	 
	            TextView t3;
	            t3 = ((TextView) rootView.findViewById(R.id.loginHere));
	           
	            t3.setOnClickListener(new View.OnClickListener() {

	                @Override
	                public void onClick(View v) {
	                 Intent intent = new Intent(getActivity(), UserLogin.class);
	      	    	  startActivity(intent);
	                }
	            });
	            
	            TextView t4;
	            t4 = ((TextView) rootView.findViewById(R.id.link));
	           
	            t4.setOnClickListener(new View.OnClickListener() {

	                @Override
	                public void onClick(View v) {
	                 Intent intent = new Intent(getActivity(), RegisterAccount.class);
	      	    	  startActivity(intent);
	                }
	            });
	            return rootView;
	        }
	    }
	 
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.user_manual, menu);
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
	}


