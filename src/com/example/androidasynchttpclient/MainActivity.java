package com.example.androidasynchttpclient;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.Future;

import org.apache.http.Header;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/****
 * AsyncHttpClient请求
 * 
 * @author zq     
 * 
 * 使用AndroidStudio则   在Gradle build 脚本中增加即可使用
 * compile 'com.loopj.android:android-async-http:1.4.8'  
 * 
 */
public class MainActivity extends Activity implements OnClickListener {

	public static AsyncHttpClient mHttpc = new AsyncHttpClient();

	private TextView mTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asynchttpclict);
		initView();
	}

	private void initView() {
		findViewById(R.id.btn1).setOnClickListener(this);
		findViewById(R.id.btn2).setOnClickListener(this);
		findViewById(R.id.btn3).setOnClickListener(this);
		findViewById(R.id.btn4).setOnClickListener(this);
		mTextView=(TextView) findViewById(R.id.Text);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			showHttpGet1();
			break;
		case R.id.btn2:
			showHttpGet2("https://www.baidu.com");
			break;
		case R.id.btn3:
			showHttpGet3();
			break;
		case R.id.btn4:
			showHttpPost();
			break;
		case R.id.btn5:
			
		case R.id.btn6:
			
		default:
			break;
		}

	}

	private void showHttpGet1() {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("https://www.baidu.com", new AsyncHttpResponseHandler() {
			@Override
			public void onStart() { // 请求启动 请求前
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) { // 请求成功
				StringBuffer result = new StringBuffer("");
				int length = responseBody.length;
				for (int i = 0; i < length; i++) {
					result.append((char) (responseBody[i] & 0xff));
				}
				Toast.makeText(MainActivity.this, "结果:" + result.toString(), 2)
						.show();
				mTextView.setText("结果:" +result.toString());
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) // 请求失败
			{

			}

			public void onRetry() { // 重试

			}

			@Override
			public void onProgress(int bytesWritten, int totalSize) { // 请求进度

			}

			@Override
			public void onFinish() { // 请求完成

			}
		});
	}

	public void showHttpGet2(String uri) {
		mHttpc.get(uri, null, new AsyncHttpResponseHandler() { // 请求失败
					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1,
							byte[] responseBody) {
						StringBuffer result = new StringBuffer("");
						int length = responseBody.length;
						for (int i = 0; i < length; i++) {
							result.append((char) (responseBody[i] & 0xff));
						}
						Toast.makeText(MainActivity.this,
								"结果:" + result.toString(), 2).show();
						mTextView.setText("结果:" +result.toString());
					}
				});
	}

	private void showHttpGet3() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("q", "test");
		params.put("showapi_appid", "11548");
		// 当前时间
		params.put("showapi_timestamp", "20160511151954");
		params.put("showapi_sign", "bb1d15ab7ce646ec87cc89d684ca4bcb");
		client.get("https://route.showapi.com/32-9", params,
				new TextHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String response) {
						Toast.makeText(MainActivity.this,
								"结果:" + response.toString(), 2).show();
						mTextView.setText("结果:" +response.toString());
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseBody, Throwable error) {
						Log.i("ERROR", error.toString());
					}
				});
	}

	/*
	 * 获得字符串
	 */
	public void showHttpPost() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("q", "test");
		params.put("showapi_appid", "11548");
		// 当前时间
		params.put("showapi_timestamp", "20160511151954");
		params.put("showapi_sign", "bb1d15ab7ce646ec87cc89d684ca4bcb");
		client.post("https://route.showapi.com/32-9", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(int arg0, Header[] arg1,
							byte[] responseBody, Throwable arg3) {

					}

					@Override
					public void onSuccess(int arg0, Header[] arg1,
							byte[] responseBody) {
						StringBuffer result = new StringBuffer("");
						int length = responseBody.length;
						for (int i = 0; i < length; i++) {
							result.append((char) (responseBody[i] & 0xff));
						}
						Toast.makeText(MainActivity.this,
								"结果:" + result.toString(), 2).show();
						mTextView.setText("结果:" +result.toString());
					}
				});

	}

	

	/**
	 * 上传单个文件
	 */
	private void showFile() {
		File myFile = new File("filePath");// filePath--->文件路径
		RequestParams params = new RequestParams();
		try {
			params.put("time", "20160511151954");
			params.put("sign", "bb1d15ab7ce646ec87cc89d684ca4bcb");
			params.put("filename", myFile);
			AsyncHttpClient client = new AsyncHttpClient();
			client.post("url", params, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {

				}

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {

				}
			});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/***
	 * 多个文件上传
	 * 
	 * @param sendFilesPath
	 */
	public void uploadFile(ArrayList<String> sendFilesPath) {
		if (sendFilesPath.size() == 0)
			return;

		String strUploadFile = "";
		AsyncHttpClient client = new AsyncHttpClient();
		client.setURLEncodingEnabled(false);

		RequestParams params = new RequestParams();
		params.put("time", "20160511151954");
		params.put("sign", "bb1d15ab7ce646ec87cc89d684ca4bcb");
		// 批量上传
		for (int i = 0; i < sendFilesPath.size(); i++) {
			File myFile = new File(sendFilesPath.get(i));
			try {
				params.put(myFile.getName(), myFile);
			} catch (FileNotFoundException e1) {
				continue;
			}
		}

		client.setTimeout(10000);
		client.post(strUploadFile, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable arg3) {
				Log.i("Show", "上传失败");
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				Log.i("Show", "上传成功");
			}

			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				super.onProgress(bytesWritten, totalSize);
				int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
				// 上传进度显示
				Log.i("Show", "上传进度显示:" + count);
				
			}

			@Override
			public void onRetry(int retryNo) {
				super.onRetry(retryNo);
				// 返回重试次数
			}
		});
	}

}
