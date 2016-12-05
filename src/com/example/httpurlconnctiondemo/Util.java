package com.example.httpurlconnctiondemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Util {
	
	/***
	 * get请求方式
	 * @param urlPath
	 * @return
	 */
	public static String get(String urlPath) {
		HttpURLConnection conn = null; // 连接对象
		InputStream is = null;
		String resultData = "";
		try {
			URL url = new URL(urlPath); // URL对象
			conn = (HttpURLConnection) url.openConnection(); // 使用URL打开一个链接
			conn.setDoInput(true); // 允许输入流，即允许下载
			conn.setDoOutput(true); // 允许输出流，即允许上传
			conn.setUseCaches(false); // 不使用缓冲
			conn.setRequestMethod("GET"); // 使用get请求
			is = conn.getInputStream(); // 获取输入流，此时才真正建立链接
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader bufferReader = new BufferedReader(isr);
			String inputLine = "";
			while ((inputLine = bufferReader.readLine()) != null) {
				resultData += inputLine + "\n";
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		Log.i("get", resultData.toString());
		return resultData;
	}
	/***
	 * post请求方式
	 * @param urlPath
	 * @param params
	 * @return
	 */
	public static String post(String urlPath, Map<String, String> params) {
		if (params == null || params.size() == 0) {
			return get(urlPath);
		}
		OutputStream os = null;
		InputStream is = null;
		HttpURLConnection connection = null;
		StringBuffer body = getParamString(params);
		byte[] data = body.toString().getBytes();
		try {
			URL url = new URL(urlPath);
			// 获得URL对象
			connection = (HttpURLConnection) url.openConnection();
			// 获得HttpURLConnection对象
			connection.setRequestMethod("POST");
			// 设置请求方法为post
			connection.setUseCaches(false);
			// 不使用缓存
			connection.setConnectTimeout(10000);
			// 设置超时时间
			connection.setReadTimeout(10000);
			// 设置读取超时时间
			connection.setDoInput(true);
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			connection.setDoOutput(true);
			// 设置为true后才能写入参数
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length",
					String.valueOf(data.length));
			os = connection.getOutputStream();
			os.write(data);
			// 写入参数
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// 相应码是否为200
				is = connection.getInputStream();
				// 获得输入流
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				// 包装字节流为字符流
				StringBuilder response = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				Log.i("post", response.toString());
				return response.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				connection.disconnect();
				connection = null;
			}
		}
		return null;
	}

	public static StringBuffer getParamString(Map<String, String> params) {
		StringBuffer result = new StringBuffer();
		Iterator<Map.Entry<String, String>> iterator = params.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> param = iterator.next();
			String key = param.getKey();
			String value = param.getValue();
			result.append(key).append('=').append(value);
			if (iterator.hasNext()) {
				result.append('&');
			}
		}
		return result;
	}

	/**
	 * 
	 * 图片加载
	 * @param url
	 * @return
	 */
	public static Bitmap getImageBitmap(String url) {
		URL imgUrl = null;
		Bitmap bitmap = null;
		try {
			imgUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imgUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

}
