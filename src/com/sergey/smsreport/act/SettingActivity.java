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
	private RadioGroup rgRequestMethod = null;
	private RadioButton rbGet = null;
	private RadioButton rbPost = null;
	private RadioGroup rgListenMethod = null;
	private RadioButton rbBroadCast = null;
	private RadioButton rbObserver = null;
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
		rgRequestMethod = (RadioGroup) findViewById(R.id.rg_request_method);
		rbGet = (RadioButton)findViewById(R.id.rb_get);
		rbPost = (RadioButton)findViewById(R.id.rb_post);
		rgListenMethod = (RadioGroup) findViewById(R.id.rg_listen_method);
		rbBroadCast = (RadioButton)findViewById(R.id.rb_broadcast);
		rbObserver = (RadioButton)findViewById(R.id.rb_observer);
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
		if(sharedPreferences.getString("method", "post").equals("post")){
			rbPost.setChecked(true);
		}else{
			rbGet.setChecked(true);
		}
		if(sharedPreferences.getString("listen", "boardcast").equals("boardcast")){
			rbBroadCast.setChecked(true);
		}else{
			rbObserver.setChecked(true);
		}
		btSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Editor editor = sharedPreferences.edit();
				editor.putBoolean("content", cbContent.isChecked());
				editor.putBoolean("phone", cbPhoneNumber.isChecked());
				editor.putBoolean("time", cbTime.isChecked());
				editor.putString("url", etUrl.getText().toString());
				editor.putString("method", rbGet.isChecked() ? "get" : "post");
				editor.putString("listener", rbObserver.isChecked() ? "observer" : "boardcast");
				editor.commit();
			}
		});
	}

}
