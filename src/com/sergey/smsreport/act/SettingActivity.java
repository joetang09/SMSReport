package com.sergey.smsreport.act;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sergey.framework.core.BaseActivity;
import com.sergey.smsreport.R;
import com.sergey.smsreport.net.Net;
import com.sergey.smsreport.net.NetListener;
import com.sergey.smsreport.stc.SMSStatic;

public class SettingActivity extends BaseActivity{

	private EditText etUrl = null;
	private CheckBox cbContent = null;
	private CheckBox cbPhoneNumber = null;
	private CheckBox cbTime = null;
	private Button btSave = null;
	
	private SharedPreferences sharedPreferences = null;
	@Override
	protected int setView() {
		return R.layout.activity_setting;
	}

	@Override
	protected void initView() {
		etUrl = (EditText)findViewById(R.id.et_url);
		cbContent = (CheckBox) findViewById(R.id.cb_content);
		cbPhoneNumber = (CheckBox)findViewById(R.id.cb_phone);
		cbTime = (CheckBox)findViewById(R.id.cb_time);
		btSave = (Button)findViewById(R.id.bt_save);
	}

	@Override
	protected void initAction() {
		sharedPreferences = getSharedPreferences(SMSStatic.COMMONT_PREFERENCES_NAME, MODE_WORLD_READABLE);
		etUrl.setText(sharedPreferences.getString("url", ""));
		cbContent.setChecked(sharedPreferences.getBoolean("content", false));
		cbPhoneNumber.setChecked(sharedPreferences.getBoolean("phone", false));
		cbTime.setChecked(sharedPreferences.getBoolean("time", false));
		btSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Editor editor = sharedPreferences.edit();
				editor.putBoolean("content", cbContent.isChecked());
				editor.putBoolean("phone", cbPhoneNumber.isChecked());
				editor.putBoolean("time", cbTime.isChecked());
				editor.putString("url", etUrl.getText().toString());
				editor.commit();
			}
		});
	}

}
