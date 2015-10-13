package cn.eaglefire.app.hists.controller

import cn.eaglefire.app.hists.util.network.http.SimpleHttpUtil

/**
 * 网络接口控制器
 */
class NetInterfaceController {

    /**
     * 首页
     */
    def index() {
    }

    /**
     * 测试网络接口
     * 通过ajax方式访问该方法, 以JSON的格式返回数据
     * JSON格式:
     */
    def testInterface() {

        // HTTP执行方式( GET / POST )
        String executeMethod = "";

        SimpleHttpUtil httpUtil = new SimpleHttpUtil.Builder()
                .build();

        String result;

        if( "GET".equals(executeMethod.trim().toUpperCase()) ){
            result = httpUtil.executeGet();
        }else if( "POST".equals(executeMethod.trim().toUpperCase()) ){
            result = httpUtil.executePost();
        }else{
            result = "NO Defined HTTP Method";
        }

    }

}
