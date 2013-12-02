package com.sergey.smsreport.service;

import java.util.HashMap;

import com.sergey.smsreport.net.Net;
import com.sergey.smsreport.net.NetListener;
import com.sergey.smsreport.stc.SMSStatic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class SMSHandler extends Handler{

	private static final String TAG="SMSHandler";  
    private Context mContext;  
    private SharedPreferences sharedPreferences;
      
    public SMSHandler(Context context)  
    {  
        super();  
        this.mContext=context;  
        sharedPreferences = context.getSharedPreferences(SMSStatic.COMMONT_PREFERENCES_NAME, context.MODE_WORLD_READABLE);
    }  
    @Override  
    public void handleMessage(Message msg)  
    {  
        MessageItem item=(MessageItem)msg.obj;
        Net net = new Net();
        
        NetListener netListener = new NetListener() {
			
			@Override
			public void result(String result) {
				Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
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
					param += "&smsreport_content=" + item.getBody();
				}
				if(sharedPreferences.getBoolean("phone", false)){
					param += "&smsreport_phone_number=" + item.getPhone();
				}
				if(sharedPreferences.getBoolean("time", false)){
					param += "&smsreport_time=" + String.valueOf(item.getDate());
				}
				net.get(url);
			}
		}else{
			String url = sharedPreferences.getString("url", "");
			if(!url.equals("")){
				HashMap<String, String> param = new HashMap<String, String>();
				if(sharedPreferences.getBoolean("content", false)){
					param.put("smsreport_content", item.getBody());
				}
				if(sharedPreferences.getBoolean("phone", false)){
					param.put("smsreport_phone_number", item.getPhone());
				}
				if(sharedPreferences.getBoolean("time", false)){
					param.put("smsreport_time", String.valueOf(item.getDate()));
				}
				
				net.post(url, param);
			}
		}
    }  
}
