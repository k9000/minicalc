package com.tlulybluemonochrome.minimarucalc;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainCalc extends FragmentActivity implements ActionBar.TabListener {

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
		setContentView(R.layout.activity_main_calc);

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
		getMenuInflater().inflate(R.menu.activity_main_calc, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
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
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 5 total pages.
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase();
			case 1:
				return getString(R.string.title_section2).toUpperCase();
			case 2:
				return getString(R.string.title_section3).toUpperCase();
			case 3:
				return getString(R.string.title_section4).toUpperCase();
			case 4:
				return getString(R.string.title_section5).toUpperCase();
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */

	public static class DummySectionFragment extends Fragment implements
			OnClickListener {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */

		public static final String ARG_SECTION_NUMBER = "section_number";
		private EditText mEditText;
		private Button mButton;
		private Button[] figButton = new Button[10];
		private Button[] calcButton = new Button[10];
		Calc[] calc = { new Calc(), new Calc(), new Calc(), new Calc(),
				new Calc() };
		/* ボタンのidを配列に */
		int[] fig = { R.id.button0, R.id.button1, R.id.button2, R.id.button3,
				R.id.button4, R.id.button5, R.id.button6, R.id.button7,
				R.id.button8, R.id.button9 };
		int[] cal = { R.id.button_equal, R.id.button_add, R.id.button_sub,
				R.id.button_mul, R.id.button_div, R.id.button_dot,
				R.id.button_AC, R.id.button_C, R.id.button_copy,
				R.id.button_paste };
		int i;

		public DummySectionFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			View v = inflater.inflate(R.layout.activity_main, container, false);
			for (int i = 0; i < fig.length; i++) {
				figButton[i] = new Button(getActivity());
				figButton[i] = (Button) v.findViewById(fig[i]);
				figButton[i].setOnClickListener(this);
			}
			for (int i = 0; i < cal.length; i++) {
				calcButton[i] = new Button(getActivity());
				calcButton[i] = (Button) v.findViewById(cal[i]);
				calcButton[i].setOnClickListener(this);
			}

			return v;

		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			mEditText = (EditText) getView().findViewById(R.id.editText1);

		}

		@Override
		public void onClick(View v) {
			// TODO 自動生成されたメソッド・スタブ
			for (int i = 0; i < fig.length; i++) {
				if (fig[i] == v.getId())
					mEditText.setText(calc[getArguments().getInt(
							ARG_SECTION_NUMBER) - 1].figure(i));
			}
			for (int i = 0; i < 4; i++) {
				if (cal[i] == v.getId()) {
					mEditText.setText(calc[getArguments().getInt(
							ARG_SECTION_NUMBER) - 1].calc(i));
				}
			}
			switch (v.getId()) {
			case R.id.button_dot:
				mEditText.setText(calc[getArguments()
						.getInt(ARG_SECTION_NUMBER) - 1].dot());
				break;
			case R.id.button_AC:
				mEditText.setText(calc[getArguments()
						.getInt(ARG_SECTION_NUMBER) - 1].AC());
				break;
			case R.id.button_C:
				mEditText.setText(calc[getArguments()
						.getInt(ARG_SECTION_NUMBER) - 1].C());
				break;
			case R.id.button_copy:
				Toast.makeText(getActivity(), "まだ未実装です", Toast.LENGTH_SHORT)
						.show();
				break;
			case R.id.button_paste:
				Toast.makeText(getActivity(), "まだ未実装です", Toast.LENGTH_SHORT)
						.show();
				break;
			}

		}

	}

}
