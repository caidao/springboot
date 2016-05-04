package paner.util;

import com.google.gson.Gson;
import org.apache.http.*;
import org.apache.http.annotation.Obsolete;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.entity.StrictContentLengthStrategy;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import paner.model.QQRespModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Administrator on 2016/4/26.
 */
public class ConqqUtil {

    private static String code_cache=null;
    private static String user_info_cache =null;

    private static final String APP_ID="101307287";
    private static final String APP_KEY="7021f2edb9a62c6c48fc35e436a04cce";

    private static final String QQ_CON_STEP_2="https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id="+APP_ID +
            "&client_secret="+APP_KEY+"&code=@@@@@&redirect_uri=http%3a%2f%2fwww.debugpan.com%2fexamples%2f";
    private static final String QQ_CON_STEP_3="https://graph.qq.com/oauth2.0/me?";
    private static final String QQ_CON_STEP_4 ="https://graph.qq.com/user/get_user_info?@token@&oauth_consumer_key="+APP_ID +"&openid=@openid@";

    public static String getQqInfo(String code,String state){
        HttpGet httpGet = new HttpGet();
        if (code_cache!=null &&code_cache.equals(code) ){
            return user_info_cache;
        }
        QQRespModel model =null;
        try {
            httpGet.setURI(new URI(QQ_CON_STEP_2.replaceFirst("@@@@@",code)));
            String token = httpGetContent(httpGet);//F7B641090171AFEE2A8531385BA45AA8

            httpGet.setURI(new URI(QQ_CON_STEP_3+token));
            String openidInfo = httpGetContent(httpGet);//901C51444925555D05191B3058F20318
            if ((model=responseParse(openidInfo))!=null){

            }

            httpGet.setURI(new URI(QQ_CON_STEP_4.replaceFirst("@token@",token).replaceFirst("@openid@",model.getOpenid())));
            String userInfo = httpGetContent(httpGet);
            user_info_cache =userInfo;
            code_cache = code;
            return userInfo;

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private static String httpGetContent(HttpGet httpGet){
        StringBuffer content =new StringBuffer();
        try {
            HttpResponse response = HttpClientBuilder.create().build().execute(httpGet);
            HttpEntity entity =response.getEntity();
            BufferedReader in = new BufferedReader(new InputStreamReader(entity.getContent()));
            String line="";
            while ((line=in.readLine())!=null){
                content.append(line);
            }
            in.close();
        }catch (Exception e){

        }
       return content.toString();
    }

    private static QQRespModel responseParse(String content){
        if (content!=null && content.contains("callback")){
            content = content.trim().substring(9,content.length()-2);
            QQRespModel model = new Gson().fromJson(content.trim(),QQRespModel.class);
            return model;
        }
        return null;
    }
}
