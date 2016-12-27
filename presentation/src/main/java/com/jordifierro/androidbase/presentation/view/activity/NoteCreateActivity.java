package com.jordifierro.androidbase.presentation.view.activity;

import android.os.Bundle;

import com.jordifierro.androidbase.presentation.R;
import com.jordifierro.androidbase.presentation.view.activity.base.CleanActivity;
import com.jordifierro.androidbase.presentation.view.fragment.NoteCreateFragment;

public class NoteCreateActivity extends CleanActivity {

	@Override
	protected void initializeActivity(Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			addFragment(R.id.fragment_container, new NoteCreateFragment());
		}
	}

}
