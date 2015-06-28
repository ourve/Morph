package  org.matrixdata.morph.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.servlet.rest.exception.MorphRestException;

import java.util.ArrayList;
import java.util.List;


public class SMSDeliver {
   static Logger logger = Logger.getLogger(SMSDeliver.class);

    public static String sendName = "api";
    public static String sendKey = "b102d402488842cca01b914c424854b4";
    public static String sendPath = "http://sms-api.luosimao.com/v1/send.json";

    public static String authorization = "Basic YXBpOmtleS1iMTAyZDQwMjQ4ODg0MmNjYTAxYjkxNGM0MjQ4NTRiNA==";

    public static void sendMessage(String number, String text) throws MorphRestException {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(sendPath);
        post.addHeader("Authorization", authorization);
        int status;
        String body;

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("mobile", number));
        nvps.add(new BasicNameValuePair("message", text));

        try {
            post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            HttpResponse response = client.execute(post);
            body = EntityUtils.toString(response.getEntity(), "UTF-8");
            status = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            EntityUtils.consume(entity);
        }
        catch (Exception e) {
            logger.info(String.format("send message %s to %s fail, exception = %s", text, number, e.toString()));
            throw new MorphRestException(Constant.STATUS_UNKNOWN_ERROR, "send message fail.");
        }

        logger.info(String.format("send message %s to %s, status = %d, body = %s", text, number, status, body));
    }

    public static void main(String[] args) {
        try {
            sendMessage("13550154339", "测试信息【数据矩阵】");
        }
        catch (MorphRestException e) {
            e.printStackTrace();
        }
    }
}
