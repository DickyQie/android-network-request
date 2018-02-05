#### Android之封装好的异步网络请求框架 
  <p style="text-align:start"><strong>1.简介</strong>&nbsp;<br> Android中网络请求一般使用Apache HTTP Client或者采用HttpURLConnection，但是直接使用这两个类库需要写大量的代码才能完成网络post和get请求，而使用这个MyHttpUtils库可以大大的简化操作，它是基于HttpURLConnection，所有的请求都是独立在UI主线程之外，没有通过CommCallback回调方法处理请求结果， <span style="background-color:rgb(254, 254, 254); color:rgb(51, 51, 51)">没有了子线程、没有了handle，链式的变成使得代码更加清晰</span> 。</p> 
<p style="text-align:start"><strong>2.特性</strong>&nbsp;</p> 
<ol> 
 <li>支持get、post请求，文件下载，上传等；</li> 
 <li>支持http和https的协议；</li> 
 <li>支持设置连接、读取超时时间（可选）；</li> 
 <li>支持json格式的请求结果（无论json格式多复杂，都能搞定）；</li> 
 <li>支持传入JavaBean对象（解析之后的javabean对象）；</li> 
 <li>支持回调方法中反应传入javabean对象，这样可以在回调方法中直接拿到解析过后的javabean对象；</li> 
 <li>支持回调方法中更新UI（所以叫异步请求了）。</li> 
</ol> 
<p><span style="background-color:rgb(254, 254, 254); color:rgb(51, 51, 51)">说明：java中一切皆对象，这里的JavaBean对象就是你请求接口之后返回的json数据所对应的实体，使用时返回的json数据可根据你给的对象自动解析并返回对象。</span></p> 
<p><strong>3.使用</strong></p> 
<p>gradle添加依赖（添加完之后Sync一下）：</p> 
<pre><code class="language-java">compile 'com.huangdali:myhttputils:2.0.2'</code></pre> 

