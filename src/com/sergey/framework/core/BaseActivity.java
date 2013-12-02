package com.sergey.framework.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public abstract class BaseActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(setView());
		initView();
		initAction();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	protected void jump(Intent intent) {
		startActivity(intent);
	}
	
	protected void jumpAndKillself(Intent intent) {
		jump(intent);
		this.finish();
	}
	
	protected void showToast(String data) {
		Toast.makeText(this, data, Toast.LENGTH_LONG).show();
	}

	protected abstract int setView();
	protected abstract void initView();
	protected abstract void initAction();
	
}
