# Android之封装好的异步网络请求框架 
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
<p>get：</p> 
<pre><code class="language-java"> public void onGet() {
        String url = "http://gpj.zhangwoo.cn/app.php?platform=android&amp;appkey=5a379b5eed8aaae531df5f60b12100cfb6dff2c1&amp;c=member&amp;a=getdepartments";
        new MyHttpUtils()
                .url(url)//请求的url
                .setJavaBean(UserBean.class)//设置需要解析成的javabean对象
                .setReadTimeout(60000)//设置读取超时时间,不设置的话默认为30s(30000)
                .setConnTimeout(6000)//设置连接超时时间,不设置的话默认5s(5000)
                .onExecute(new CommCallback&lt;UserBean&gt;() {//开始执行异步请求,传入一个通用回调对象,泛型为返回的javabean对象
                    @Override
                    public void onSucess(UserBean bean) {//成功之后回调
                        Util.showMsg(MainActivity.this, bean.getData().get(0).getDepartname());
                    }

                    @Override
                    public void onFailed(String msg) {//失败时候回调
                        Util.showMsg(MainActivity.this, msg);
                    }
                });
    }</code></pre> 
<p>Post：</p> 
<pre><code class="language-java">public void onPost() {
        HashMap&lt;String, String&gt; param = new HashMap&lt;&gt;();
        param.put("c", "member");
        param.put("a", "getdepartments");
        new MyHttpUtils()
                .url(urls2)
                .addParam(param)
                .setJavaBean(UserBean.class)
                .onExecuteByPost(new CommCallback&lt;UserBean&gt;() {///实体类自动解析
                    @Override
                    public void onSucess(UserBean remarkBean) {
                        Log.i("tag",remarkBean.toString());
                        Util.showMsg(MainActivity.this, remarkBean.getData().get(0).getDepartname());
                    }
                    @Override
                    public void onFailed(String msg) {
                        Util.showMsg(MainActivity.this, msg);
                    }
                });
    }</code></pre> 
<p>文件下载：</p> 
<pre><code class="language-java">public void onDownload() {
        String url = "http://avatar.csdn.net/8/6/0/2_dickyqie.jpg";
        new MyHttpUtils()
                .url(url)
                .setFileSavePath("/sdcard/downloadtest")//不要这里只是填写文件保存的路径，不包括文件名哦
                .setReadTimeout(5 * 60 * 1000)//由于下载文件耗时比较大，所以设置读取时间为5分钟
                .downloadFile(new CommCallback&lt;String&gt;() {
                    @Override
                    public void onSucess(String msg) {
                        Util.showMsg(MainActivity.this, msg);
                    }

                    @Override
                    public void onFailed(String s) {

                    }
                    /**
                     * 可以重写进度回调方法
                     * @param total
                     * @param current
                     */
                    @Override
                    public void onDownloading(long total, long current) {
                        tvProgress.setText("当前进度：" + new DecimalFormat("######0.00").format(((double) current / total) * 100) + "%");
                    }
                });
    }</code></pre> 
<p><strong>别忘加网络权限</strong></p> 
<pre><code class="language-html">&lt;uses-permission android:name="android.permission.INTERNET" /&gt;</code></pre> 
<p><strong>文件上传和下载也需要添加权限</strong></p> 
<pre><code class="language-html">&lt;uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /&gt;
&lt;uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /&gt;</code></pre> 
