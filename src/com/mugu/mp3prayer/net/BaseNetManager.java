package com.mugu.mp3prayer.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseNetManager {

	protected String getStringByUrl(String strUrl) {

		StringWriter result = new StringWriter();
		URL mUrl = null;
		try {
			mUrl = new URL(strUrl);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		HttpURLConnection httpConn = null;
		try {
			httpConn = (HttpURLConnection) mUrl.openConnection();
			if (httpConn.getResponseCode() != HttpURLConnection.HTTP_OK) {

				return null;
			}

			httpConn.connect();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream()));
			PrintWriter pw = new PrintWriter(result);

			String strTmp = new String();
			while ((strTmp = br.readLine()) != null) {
				pw.println(strTmp);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();
	}

}
