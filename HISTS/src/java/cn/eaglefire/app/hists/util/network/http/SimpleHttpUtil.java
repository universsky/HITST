package cn.eaglefire.app.hists.util.network.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 简单的HTTP工具
 *
 * Created by eagle on 15/10/13.
 */
public class SimpleHttpUtil {

    /**
     * 请求的URL
     */
    private String baseUrl;

    /**
     * 请求的头部参数
     */
    private Map<String, String> headerParameter;

    /**
     * 请求的内容参数
     */
    private Map<String, String> contentParameter;

    /**
     * HttpClient
     */
    private static CloseableHttpClient httpClient;

    /**
     * 返回数据的编码格式
     */
    private static final String RESPONSE_CHARSET = "UTF-8";

    /**
     * 静态全局初始化
     */
    static {
        httpClient = HttpClients.createDefault();
    }

    /**
     * 初始化HTTP头部参数
     * @return
     */
    private void initialHeaderParameter(HttpRequest httpRequest){
        if( null != this.headerParameter ){
            Set<String> keySet = this.headerParameter.keySet();
            for (String key: keySet){
                httpRequest.addHeader(key, this.headerParameter.get(key));
            }
        }
    }

    /**
     * 执行HTTP请求
     * @param httpUriRequest
     * @return
     */
    private String execute(HttpUriRequest httpUriRequest){
        String result = "";
        // 执行请求
        try{
            //
            CloseableHttpResponse httpResponse = httpClient.execute(httpUriRequest);
            // 获取返回实体ResponseEntity
            HttpEntity responseEntity = httpResponse.getEntity();
            // 将获取实体内容数据
            result = EntityUtils.toString(responseEntity, RESPONSE_CHARSET);
            //
            EntityUtils.consume(responseEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 以GET的形式发送HTTP请求
     * @return
     */
    public String executeGet(){
        // 初始化GET请求参数衍生的URL
        StringBuilder urlStrBuilder = new StringBuilder(this.baseUrl);
        if( null != this.contentParameter ){
            Set<String> keySet = this.contentParameter.keySet();
            int i = 1, keySetSize = keySet.size();
            urlStrBuilder.append("?");
            for(String key: keySet){
                urlStrBuilder.append(key+"="+this.contentParameter.get(key));
                if( i != keySetSize ) urlStrBuilder.append("&");
                i++;
            }
        }
        // 创建请求
        HttpGet httpGet = new HttpGet(urlStrBuilder.toString());
        // 设置头部
        this.initialHeaderParameter(httpGet);
        // 执行请求
        return this.execute(httpGet);
    }

    /**
     * 以POST的形式发送HTTP请求
     * @return
     */
    public String executePost(){
        // 初始化POST请求表单
        HttpEntity httpEntity = null;
        if( null != this.contentParameter ){
            Set<String> keySet = this.contentParameter.keySet();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for(String key: keySet){
                nameValuePairs.add(new BasicNameValuePair(key, this.contentParameter.get(key)));
            }
            try {
                httpEntity = new UrlEncodedFormEntity(nameValuePairs);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "Post Method Initial Entity Failed";
            }
        }
        // 创建请求
        HttpPost httpPost = new HttpPost(this.baseUrl);
        // 设置头部
        this.initialHeaderParameter(httpPost);
        // 设置请求表单
        if( null != httpEntity ) httpPost.setEntity(httpEntity);
        // 执行请求
        return this.execute(httpPost);
    }

    /**
     * 内置构建器
     */
    public static class Builder {

        /**
         * 请求的URL
         */
        private String baseUrl;

        /**
         * 请求的头部参数
         */
        private Map<String, String> headerParameter;

        /**
         * 请求的内容参数
         */
        private Map<String, String> contentParameter;

        /**
         * 空构造建造方法
         */
        public Builder(){}

        /**
         *
         * @param baseUrl
         * @return
         */
        public Builder baseUrl(String baseUrl){
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         *
         * @param headerParameter
         * @return
         */
        public Builder headerParameter(Map<String, String> headerParameter){
            this.headerParameter = headerParameter;
            return this;
        }

        /**
         *
         * @param contentParameter
         * @return
         */
        public Builder contentParameter(Map<String, String> contentParameter){
            this.contentParameter = contentParameter;
            return this;
        }

        /**
         *
         * @return
         */
        public SimpleHttpUtil build(){
            return new SimpleHttpUtil(this);
        }

    }

    /**
     * 空构造方法
     */
    public SimpleHttpUtil(){}

    /**
     * 建造构造方法
     * @param builder
     */
    public SimpleHttpUtil(Builder builder){
        this.baseUrl = builder.baseUrl;
        this.headerParameter = builder.headerParameter;
        this.contentParameter = builder.contentParameter;
    }

    /** Getter and Setter **/

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Map<String, String> getHeaderParameter() {
        return headerParameter;
    }

    public void setHeaderParameter(Map<String, String> headerParameter) {
        this.headerParameter = headerParameter;
    }

    public Map<String, String> getContentParameter() {
        return contentParameter;
    }

    public void setContentParameter(Map<String, String> contentParameter) {
        this.contentParameter = contentParameter;
    }
}
