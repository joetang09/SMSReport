package com.sergey.smsreport.net;

public interface NetListener {

	public void conn(boolean conn);
	public void result(String result);
}
