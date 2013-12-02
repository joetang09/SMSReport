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
import android.telephony.*;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
	private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	private static boolean isRegist = false;
    private SharedPreferences sharedPreferences;
    private Context context;

    public SMSReceiver(Context context){
    	this.context = context;
    	sharedPreferences = context.getSharedPreferences(SMSStatic.COMMONT_PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
    }
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(SMS_RECEIVED)) {
			SmsManager sms = SmsManager.getDefault();
			Bundle bundle = intent.getExtras();
			Object[] messages = (Object[]) intent.getSerializableExtra("pdus");
			byte[][] pduObjs = new byte[messages.length][];
			for (int i = 0; i < messages.length; i++) {
				pduObjs[i] = (byte[]) messages[i];
			}
			byte[][] pdus = new byte[pduObjs.length][];
			int pduCount = pdus.length;
			SmsMessage[] message = new SmsMessage[pduCount];
			for (int i = 0; i < pduCount; i++) {
				pdus[i] = pduObjs[i];
				messages[i] = SmsMessage.createFromPdu(pdus[i]);
			}
			for (SmsMessage msg : message) {
				
				
				 Net net = new Net();
			        
			        NetListener netListener = new NetListener() {
						
						@Override
						public void result(String result) {
							Toast.makeText(SMSReceiver.this.context, result, Toast.LENGTH_SHORT).show();
						}
						
						@Override
						public void conn(boolean conn) {
							
						}
					};
					if(sharedPreferences.getString("method", "post").equals("get")){
						String url = sharedPreferences.getString("url", "");
						if(!url.equals("")){
							String param = "?";
							if(sharedPreferences.getBoolean("content", false)){
								param += "&smsreport_content=" + msg.getMessageBody();
							}
							if(sharedPreferences.getBoolean("phone", false)){
								param += "&smsreport_phone_number=" + msg.getDisplayOriginatingAddress();
							}
							if(sharedPreferences.getBoolean("time", false)){
								param += "&smsreport_time=" + String.valueOf(msg.getTimestampMillis());
							}
							net.get(url);
						}
					}else{
						String url = sharedPreferences.getString("url", "");
						if(!url.equals("")){
							HashMap<String, String> param = new HashMap<String, String>();
							if(sharedPreferences.getBoolean("content", false)){
								param.put("smsreport_content", msg.getMessageBody());
							}
							if(sharedPreferences.getBoolean("phone", false)){
								param.put("smsreport_phone_number", msg.getDisplayOriginatingAddress());
							}
							if(sharedPreferences.getBoolean("time", false)){
								param.put("smsreport_time", String.valueOf(msg.getTimestampMillis()));
							}
							
							net.post(url, param);
						}
					}
				
				
			}
		}
	}
	
	public static void setRegistFlag(boolean isRegist){
		SMSReceiver.isRegist = isRegist;
	}
	
	public static boolean getRegistFlag(){
		return isRegist;
	}
	
}
