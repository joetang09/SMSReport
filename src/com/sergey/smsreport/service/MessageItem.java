package com.sergey.smsreport.service;

import java.io.Serializable;

public class MessageItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int type;
	private int protocol;
	private long date;
	private String phone;
	private String body;

	public MessageItem() {
	}

	public MessageItem(int id, int type, int protocol, long date, String phone,
			String body) {
		this.id = id;
		this.type = type;
		this.protocol = protocol;
		this.date = date;
		this.phone = phone;
		this.body = body;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getProtocol() {
		return protocol;
	}

	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String toString() {
		return "id=" + id + ",type=" + type + ",protocol=" + protocol
				+ ",phone=" + phone + ",body=" + body;
	}
}
