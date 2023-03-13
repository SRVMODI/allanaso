package com.astix.allanasosfa;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;


public class TabListener implements ActionBar.TabListener {

	private Fragment fragment;

	// The contructor.
	public TabListener(Fragment fragment) {
		this.fragment = fragment;
	}


	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		fragmentTransaction.replace(R.id.mysummary_bytab, fragment);
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
		fragmentTransaction.replace(R.id.mysummary_bytab, fragment);
	}


}
