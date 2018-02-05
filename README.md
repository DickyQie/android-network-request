#### 网络请求框架----AsyncHttpClient的get,post和图片上传服务器

<p>&nbsp;async-http-client库是一个基于回调函数的Http异步通信客户端Android组件，是在Apache的HttpClient库的基础上开发构建而成的。</p> 
<p>Eclipse使用：导入android-async-http-1.4.4.jar包&nbsp;<a href="http://download.csdn.net/detail/dickyqie/9662215" rel="nofollow">http://download.csdn.net/detail/dickyqie/9662215</a></p> 
<p>AndroidStudio: gradle中引入&nbsp;<span style="color:#B22222">compile&nbsp;'com.loopj.android:android-async-http:1.4.8'</span></p> 
<span id="OSC_h3_1"></span>
<h5><strong>功能特色</h5> 
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
