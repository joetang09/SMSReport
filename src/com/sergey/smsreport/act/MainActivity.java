package com.sergey.smsreport.act;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sergey.framework.core.BaseActivity;
import com.sergey.smsreport.R;

public class MainActivity extends BaseActivity {

	private TextView tvInfo = null;
	private Button btContrl = null;
	private Button btSetting = null;

	@Override
	protected int setView() {
		return R.layout.activity_main;
	}

	@Override
	protected void initView() {
		btContrl = (Button) findViewById(R.id.bt_control);
		btSetting = (Button) findViewById(R.id.bt_setting);
		tvInfo = (TextView) findViewById(R.id.tv_info);
	}

	@Override
	protected void initAction() {
		btSetting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SettingActivity.class);
				MainActivity.this.jump(intent);
			}
		});


		btContrl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			}
		});

	}

}
