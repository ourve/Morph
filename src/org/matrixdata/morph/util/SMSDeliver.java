package  org.matrixdata.morph.util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;


public class SMSDeliver {
    static Logger logger = Logger.getLogger(SMSDeliver.class);

    public static String sendKey = "b102d402488842cca01b914c424854b4";
    public static String sendPath = "http://sms-api.luosimao.com/v1/send.json";

    public static void sendMessage(String number, String text) {

        // just replace key here
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api", sendKey));
        WebResource webResource = client.resource(sendPath);
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("mobile", number);
        formData.add("message", text);
        ClientResponse response =  webResource.type( MediaType.APPLICATION_FORM_URLENCODED ).
                post(ClientResponse.class, formData);
        int status = response.getStatus();
        logger.info(String.format("send message %s to %s, status = %d", text, number, status));
    }

    public static void main(String[] args) {
        sendMessage("13550154339", "测试信息【数据矩阵】");
    }
}
