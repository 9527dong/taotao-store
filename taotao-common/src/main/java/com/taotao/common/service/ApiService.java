package com.taotao.common.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.httpclient.HttpResult;


@Service
public class ApiService implements BeanFactoryAware {
	
	@Autowired(required = false)
	private RequestConfig requestConfig;
	
	private BeanFactory beanFactory;
	/**
	 * get请求地址，响应200，返回响应的内容
	 * 响应404、500返回null
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String doGet(String url) throws ClientProtocolException, IOException{
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(this.requestConfig);
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = gethttpClient().execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
	}
	/**
	 * 带有参数的get请求
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public String doGet(String url, Map<String, String> params) throws ClientProtocolException, IOException, URISyntaxException{
		URIBuilder builder = new URIBuilder(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.setParameter(entry.getKey(), entry.getValue());
		}
        return this.doGet(builder.build().toString());
	}
	
	/**
	 * 封装带有参数的post请求
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResult doPost(String url, Map<String, String> params) throws ClientProtocolException, IOException{
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        
        if(null != params){
        	List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
        	for (Map.Entry<String, String> entry : params.entrySet()) {
        		parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
    		}
        	// 构造一个form表单式的实体
        	UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
        	// 将请求实体设置到httpPost对象中
        	httpPost.setEntity(formEntity);
        }
        

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = gethttpClient().execute(httpPost);
            
            return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), "UTF-8"));
        } finally {
            if (response != null) {
                response.close();
            }
        }
	}
	/**
	 * 没有参数的post请求
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResult doPost(String url) throws ClientProtocolException, IOException{
		return this.doPost(url, null);
	}
	private CloseableHttpClient gethttpClient(){
		return this.beanFactory.getBean(CloseableHttpClient.class);
	}
	@Override
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		//该方法是在spring容器初始化时会调用该方法，传入BeanFactory
		this.beanFactory = arg0;
	}
}
