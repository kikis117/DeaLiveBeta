package com.example.dealivebeta;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {
	static connectServer server;
	static getData cData,dData,fData;
	static ArrayList<String> c = new ArrayList<String>();
	static ArrayList<String> d = new ArrayList<String>();
	static ArrayList<String> f = new ArrayList<String>();
	
	
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		 server = new connectServer("192.168.0.3");
		 server.execute();
		 try {
			server.get();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		 cData=new getData(1,false,server.out,server.in);
		 fData=new getData(2,false,server.out,server.in);
		 dData=new getData(3,false,server.out,server.in);
		 cData.execute();
		try {
			cData.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cData.response(c);
		
		 fData.execute();
		try {
			fData.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fData.response(f);
		
		 dData.execute();
		try {
			dData.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dData.response(d);
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
		mViewPager.getAdapter().notifyDataSetChanged();
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DataList();
			Bundle args = new Bundle();
			args.putInt(DataList.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			
			return rootView;
		}
	}
	public static class DataList extends ListFragment{
		public static final String ARG_SECTION_NUMBER = "section_number";
	    String[] countries = new String[] {
	            "India",
	            "Pakistan",
	            "Sri Lanka",
	            "China",
	            "Bangladesh",
	            "Nepal",
	            "Afghanistan",
	            "North Korea",
	            "South Korea",
	            "Japan"
	        };
	     
	        @Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	            /** Creating an array adapter to store the list of countries **/
	        	
	        	
	        	switch(getArguments().getInt(ARG_SECTION_NUMBER)){
	        		case 1:
	        			ArrayAdapter<String> adapter1  = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,c);
	        			setListAdapter(adapter1);
	        			adapter1.notifyDataSetChanged();
	        			break;
	        		case 2:
	        			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,d);
	        			setListAdapter(adapter2);

	        			adapter2.notifyDataSetChanged();
	        			break;
	        		case 3:
	        			ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,f);
	        			setListAdapter(adapter3);
	        			adapter3.notifyDataSetChanged();
	        			break;
	        			
	        	
	        	
	        	}
	           
	     
	            /** Setting the list adapter for the ListFragment */
	            
	    
	            return super.onCreateView(inflater, container, savedInstanceState);
	        }
		
		
	}
	

}
