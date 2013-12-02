package com.sergey.smsreport.net;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Net {

	private NetListener listener = null;

	public Net(NetListener listener) {
		this.listener = listener;
	}

	public Net() {
	}

	public void setListner(NetListener listener) {
		this.listener = listener;
	}

	public void get(final String url) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				BufferedReader in = null;
				try {
					HttpClient client = new DefaultHttpClient();
					HttpGet request = new HttpGet(url);
					HttpResponse response = client.execute(request);
					in = new BufferedReader(new InputStreamReader(response
							.getEntity().getContent()));

					StringBuffer sb = new StringBuffer("");
					String line = "";
					String NL = System.getProperty("line.separator");
					while ((line = in.readLine()) != null) {
						sb.append(line + NL);
					}
					in.close();

					if (Net.this.listener != null) {
						Net.this.listener.result(sb.toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

	public void post(final String url, final HashMap<String, String> paramList) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				DefaultHttpClient client = new DefaultHttpClient();
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				Iterator iter = paramList.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					Object key = entry.getKey();
					Object val = entry.getValue();
					NameValuePair pair = new BasicNameValuePair(key.toString(),
							val.toString());
					list.add(pair);
				}
				UrlEncodedFormEntity entity;
				InputStream in = null;
				try {
					entity = new UrlEncodedFormEntity(list, "UTF-8");
					HttpPost post = new HttpPost(url);
					post.setEntity(entity);
					HttpResponse response = client.execute(post);
					if (response.getStatusLine().getStatusCode() == 200) {
						in = response.getEntity().getContent();
						byte[] data = new byte[1024];
						int length = 0;
						ByteArrayOutputStream bout = new ByteArrayOutputStream();
						while ((length = in.read(data)) != -1) {
							bout.write(data, 0, length);
						}
						if (Net.this.listener != null) {
							listener.result(new String(bout.toByteArray(),
									"UTF-8"));
						}
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}
		}).start();

	}

	public boolean CheckNet(Context c) {
		ConnectivityManager conn = (ConnectivityManager) c
				.getSystemService(c.CONNECTIVITY_SERVICE);
		if (conn != null) {
			NetworkInfo[] info = conn.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void exists(final String URLName) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					HttpURLConnection con = (HttpURLConnection) new URL(URLName)
							.openConnection();
					con.setRequestMethod("HEAD");
					if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
						listener.conn(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
					listener.conn(false);
				}
				
			}
		}).start();
		
	}
}
