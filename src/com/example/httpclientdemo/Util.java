package com.example.httpclientdemo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Util {
	/**
	 * 图片加载
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getImageBitmap(String url) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		try {
			HttpResponse resp = httpclient.execute(httpget);
			// 判断是否正确执行
			if (HttpStatus.SC_OK == resp.getStatusLine().getStatusCode()) {
				// 将返回内容转换为bitmap
				HttpEntity entity = resp.getEntity();
				InputStream in = entity.getContent();
				Bitmap mBitmap = BitmapFactory.decodeStream(in);
				// 向handler发送消息，执行显示图片操作
				return mBitmap;
			}

		} catch (Exception e) {
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return null;
	}

	/***
	 * 上传文件
	 */
	public static void addFile(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(url);

			FileBody bin = new FileBody(new File(url));
			StringBody comment = new StringBody("A binary file of some kind",
					ContentType.TEXT_PLAIN);

			HttpEntity reqEntity = MultipartEntityBuilder.create()
					.addPart("bin", bin).addPart("comment", comment).build();

			httppost.setEntity(reqEntity);

			System.out
					.println("executing request " + httppost.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				System.out.println(response.getStatusLine());
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					System.out.println("Response content length: "
							+ resEntity.getContentLength());
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
