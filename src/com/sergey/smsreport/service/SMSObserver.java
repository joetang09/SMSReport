package com.sergey.smsreport.service;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;

public class SMSObserver extends ContentObserver {
	private static boolean isRegist = false;
	private Handler mHandler;
	private ContentResolver mResolver;
	private static final int MAX_NUMS = 10;
	private static int MAX_ID = 0;
	private static final String[] PROJECTION = { SMSContent.ID,
			SMSContent.TYPE, SMSContent.ADDRESS, SMSContent.BODY,
			SMSContent.DATE, SMSContent.THREAD_ID, SMSContent.READ,
			SMSContent.PROTOCOL };
	private static final String SELECTION = SMSContent.ID + " > %s" + " and "
			+ SMSContent.TYPE + "=" + SMSContent.MESSAGE_TYPE_INBOX ;

	private static final int COLUMN_INDEX_ID = 0;
	private static final int COLUMN_INDEX_TYPE = 1;
	private static final int COLUMN_INDEX_PHONE = 2;
	private static final int COLUMN_INDEX_BODY = 3;
	private static final int COLUMN_INDEX_DATE = 4;
	private static final int COLUMN_INDEX_PROTOCOL = 7;

	public SMSObserver(ContentResolver resolver, Handler handler) {
		super(handler);
		this.mResolver = resolver;
		this.mHandler = handler;
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);

		Cursor cursor = mResolver.query(SMSContent.CONTENT_URI, PROJECTION,
				String.format(SELECTION, MAX_ID), null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				int id = cursor.getInt(COLUMN_INDEX_ID);
				int type = cursor.getInt(COLUMN_INDEX_TYPE);
				int protocol = cursor.getInt(COLUMN_INDEX_PROTOCOL);
				long date = cursor.getLong(COLUMN_INDEX_DATE);
				String phone = cursor.getString(COLUMN_INDEX_PHONE);
				String body = cursor.getString(COLUMN_INDEX_BODY);
				if (protocol == SMSContent.PROTOCOL_SMS && body != null) {
					MessageItem item = new MessageItem(id, type, protocol,
							date, phone, body);
					Message msg = new Message();
					msg.obj = item;
					mHandler.sendMessage(msg);
					break;
				}
				MAX_ID  = id;
			}
			cursor.close();
		}
	}
	
	public static void setRegistFlag(boolean flag){
		SMSObserver.isRegist = flag;
	}
	
	public static boolean getRegistFlag(){
		return isRegist;
	}
}
