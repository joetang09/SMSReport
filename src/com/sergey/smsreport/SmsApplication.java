package com.sergey.smsreport;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Environment;

public class SmsApplication extends Application{

	private File extStorageAppBasePath = null;
	private File extStorageAppCachePath = null;
	@Override
	public void onCreate() {
		super.onCreate();
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			File externalStorageDir = Environment.getExternalStorageDirectory();
			if(externalStorageDir != null){
				extStorageAppBasePath = new File(
						Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
								+ "Sergey" + File.separator + "data"
								+ File.separator + getPackageName());
				boolean pathEnable = true;
				if(!extStorageAppBasePath.exists()){
					pathEnable = extStorageAppBasePath.mkdirs();
				}
				if(!pathEnable){
					extStorageAppBasePath = null;
					pathEnable = true;
				}
				extStorageAppCachePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
						+ "Sergey" + File.separator + "cache"
						+ File.separator + getPackageName());
				if(!extStorageAppCachePath.exists()){
					pathEnable = extStorageAppCachePath.mkdirs();
				}
				if(!pathEnable){
					extStorageAppCachePath = null;
				}
			}
		}
	}
	
	public File getCacheFile() {
		return extStorageAppCachePath;
	}
	public File getDataFile() {
		return extStorageAppBasePath;
	}

}
