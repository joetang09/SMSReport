package com.sergey.smsreport.act;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sergey.framework.core.BaseActivity;
import com.sergey.smsreport.R;
import com.sergey.smsreport.receiver.SMSReceiver;
import com.sergey.smsreport.service.SMSContent;
import com.sergey.smsreport.service.SMSHandler;
import com.sergey.smsreport.service.SMSObserver;
import com.sergey.smsreport.stc.SMSStatic;

public class MainActivity extends BaseActivity {

	private TextView tvInfo = null;
	private Button btContrl = null;
	private Button btSetting = null;
	private SharedPreferences sharedPreferences = null;
	private ContentResolver resolver = null;
	private SMSObserver observer = null;
	private SMSReceiver receiver = null;

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
		resolver = getContentResolver();
		observer = new SMSObserver(resolver, new SMSHandler(this));
		receiver = new SMSReceiver(this);
		btSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SettingActivity.class);
				MainActivity.this.jump(intent);

			}
		});

		sharedPreferences = getSharedPreferences(
				SMSStatic.COMMONT_PREFERENCES_NAME, MODE_WORLD_READABLE);

		if (sharedPreferences.getString("listener", "boardcast").equals(
				"observer")) {
			if (SMSObserver.getRegistFlag()) {
				btContrl.setText("停止");
			}
		} else {
			if (SMSReceiver.getRegistFlag()) {
				btContrl.setText("停止");
			}
		}

		btContrl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (btContrl.getText().equals("启动")) {
					registListener();
				} else if (btContrl.getText().equals("停止")) {
					unregistListener();
				}
			}
		});

	}

	private boolean registListener() {
		if (sharedPreferences.getString("listener", "boardcast").equals(
				"observer")) {
			resolver.registerContentObserver(SMSContent.CONTENT_URI, true, observer);
		}else{
			IntentFilter filter = new IntentFilter();         
			filter.addAction("android.provider.Telephony.SMS_RECEIVED");  
			this.registerReceiver(receiver, filter);
		}
		return false;
	}

	private boolean unregistListener() {
		if (sharedPreferences.getString("listener", "boardcast").equals(
				"observer")) {
			getContentResolver().unregisterContentObserver(observer);
		}else{
			this.unregisterReceiver(receiver);
		}
		return false;
	}

}
