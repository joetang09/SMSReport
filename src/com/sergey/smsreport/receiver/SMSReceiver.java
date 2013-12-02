package com.sergey.smsreport.receiver;

import java.util.HashMap;

import com.sergey.smsreport.net.Net;
import com.sergey.smsreport.net.NetListener;
import com.sergey.smsreport.stc.SMSStatic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.*;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
	private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(SMS_RECEIVED)) {
			final Context c = context;
			SmsManager sms = SmsManager.getDefault();
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object[] pdus = (Object[]) bundle.get("pdus");
				SmsMessage[] messages = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++)
					messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				for (SmsMessage message : messages) {
					Net net = new Net();
					SharedPreferences sharedPreferences = context
							.getSharedPreferences(
									SMSStatic.COMMONT_PREFERENCES_NAME,
									context.MODE_WORLD_READABLE);
					String url = sharedPreferences.getString("url", "");
					if (!url.equals("")) {
						HashMap<String, String> param = new HashMap<String, String>();
						if (sharedPreferences.getBoolean("content", false)) {
							param.put("smsreport_content",
									message.getMessageBody());
						}
						if (sharedPreferences.getBoolean("phone", false)) {
							param.put("smsreport_phone_number",
									message.getDisplayOriginatingAddress());
						}
						if (sharedPreferences.getBoolean("time", false)) {
							param.put("smsreport_time", String.valueOf(message
									.getTimestampMillis()));
						}

						net.post(url, param);
					}
				}
			}
		}

	}

}
