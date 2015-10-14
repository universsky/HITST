package cn.eaglefire.app.hitst.util.network.http;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eagle on 15/10/14.
 */
public class SimpleHttpUtilTests {

    /**
     *
     */
    SimpleHttpUtil simpleHttpUtil;

    @Before
    public void testBefore(){

        // 设置URL
        simpleHttpUtil = new SimpleHttpUtil.Builder()
                .baseUrl("http://www.baidu.com")
                .build();

        // 设置头部参数
        Map<String, String> headerParameters = new HashMap<String, String>();
        headerParameters.put("token","eagletoken");
        simpleHttpUtil.setHeaderParameter(headerParameters);

        // 设置内容请求参数
        Map<String, String> contentParameters = new HashMap<String, String>();
        contentParameters.put("username","eagle");
        simpleHttpUtil.setContentParameter(contentParameters);

    }

    @Test
    public void testExecuteGet(){

        String result = simpleHttpUtil.executeGet();
        System.out.println("Get - The result is: \n"+result);

    }

    @Test
    public void testExecutePost(){

        String result = simpleHttpUtil.executePost();
        System.out.println("Post - The result is: \n"+result);

    }

}
