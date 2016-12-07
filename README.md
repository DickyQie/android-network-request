#网络请求框架----AsyncHttpClient的get,post和图片上传服务器
  <div class="blog-body" id="blogBody">
                                    <val data-name="blog_content_type" data-value="richtext"></val>
                    <div class='BlogContent'>
                        <p>&nbsp;async-http-client库是一个基于回调函数的Http异步通信客户端Android组件，是在Apache的HttpClient库的基础上开发构建而成的。</p> 
<p>Eclipse使用：导入android-async-http-1.4.4.jar包&nbsp;<a href="http://download.csdn.net/detail/dickyqie/9662215" rel="nofollow">http://download.csdn.net/detail/dickyqie/9662215</a></p> 
<p>AndroidStudio: gradle中引入&nbsp;<span style="color:#B22222">compile&nbsp;'com.loopj.android:android-async-http:1.4.8'</span></p> 
<span id="OSC_h3_1"></span>
<h3><strong>功能特色</strong></h3> 
<ul> 
 <li>利用版4.3.6上游HttpClient代替Android提供defaulthttpclient</li> 
 <li>兼容Android<strong>API 23</strong>高</li> 
 <li>做<strong>异步</strong>HTTP请求处理的响应<strong>匿名回调</strong></li> 
 <li>HTTP请求发生<strong>UI线程之外</strong></li> 
 <li>请求使用<strong>线程池</strong>限制并发资源使用情况</li> 
 <li>get /后<strong>参数生成器</strong>（ RequestParams ）</li> 
 <li><strong>多文件上传</strong>没有额外的第三方库</li> 
 <li><strong>JSON上传流</strong>没有额外的图书馆</li> 
 <li>处理循环和相对重定向</li> 
 <li>小的开销给你的应用程序只<strong>90kb</strong>一切</li> 
 <li>自动智能<strong>请求重试次数</strong>质量不一的移动连接优化</li> 
 <li>自动<strong>gzip</strong>响应解码速度超快的请求支持</li> 
 <li>二进制协议通信<code>binaryhttpresponsehandler</code></li> 
 <li>内置的响应分析<strong>JSON</strong>与<code>jsonhttpresponsehandler</code></li> 
 <li>节能反应直接进入文件<code>fileasynchttpresponsehandler</code></li> 
 <li><strong>大的持久性Cookie</strong>，保存cookie到你的应用程序的SharedPreferences</li> 
 <li>杰克逊JSON集成，gson或其他JSON序列化库（德）<code>basejsonhttpresponsehandler</code></li> 
 <li>与SAX解析器支持<code>saxasynchttpresponsehandler</code></li> 
 <li>语言和内容编码的支持，不仅仅是UTF-8</li> 
</ul> 
<p>效果图：</p> 
<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<img alt="" height="233" src="https://static.oschina.net/uploads/space/2016/1205/151459_Wjie_2945455.gif" width="368"></p> 
<p>案例如下：</p> 
<pre><code class="language-java">public class MainActivity extends Activity implements OnClickListener {

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
				for (int i = 0; i &lt; length; i++) {
					result.append((char) (responseBody[i] &amp; 0xff));
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
						for (int i = 0; i &lt; length; i++) {
							result.append((char) (responseBody[i] &amp; 0xff));
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
						for (int i = 0; i &lt; length; i++) {
							result.append((char) (responseBody[i] &amp; 0xff));
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
		File myFile = new File("filePath");// filePath---&gt;文件路径
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
	public void uploadFile(ArrayList&lt;String&gt; sendFilesPath) {
		if (sendFilesPath.size() == 0)
			return;

		String strUploadFile = "";
		AsyncHttpClient client = new AsyncHttpClient();
		client.setURLEncodingEnabled(false);

		RequestParams params = new RequestParams();
		params.put("time", "20160511151954");
		params.put("sign", "bb1d15ab7ce646ec87cc89d684ca4bcb");
		// 批量上传
		for (int i = 0; i &lt; sendFilesPath.size(); i++) {
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
</code></pre> 
<p>记得加网络权限</p> 
<pre><code class="language-html">&lt;uses-permission android:name="android.permission.INTERNET"/&gt;</code></pre> 
<p>&nbsp;</p> 
