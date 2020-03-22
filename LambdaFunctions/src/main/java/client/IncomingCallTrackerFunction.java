package client;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Handler for requests to Lambda function.
 */
public class IncomingCallTrackerFunction implements RequestHandler<LinkedHashMap<Object, Object>, Object> {

    public static final String PUT_URI = "http://mcc1-dev.eu-central-1.elasticbeanstalk.com/call-statistics?CALL_STATS_API_TOKEN=YmTDcWxM9cM2c2RpPyf4Q8YtpM8vRS";

    public Map<String, String> handleRequest(final LinkedHashMap input, final Context context) {
        final Map<String, String> result = new HashMap<>();
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        final HttpPut request = new HttpPut(PUT_URI);
        request.addHeader("content-type", "application/json");
        try {
            request.setEntity(new StringEntity(createRequestEntity(input)));
            HttpResponse response = httpClient.execute(request);
            result.put("message", String.valueOf(response.getStatusLine().getStatusCode()));
        } catch (IOException e) {
            context.getLogger().log(e.toString());
            result.put("message", "error");
        }
        closeClient(context, httpClient);

        return result;
    }

    private String createRequestEntity(final LinkedHashMap input) {
        LinkedHashMap<Object, Object> details = (LinkedHashMap) input.get("Details");
        LinkedHashMap<Object, Object> contactData = (LinkedHashMap) details.get("ContactData");
        LinkedHashMap<Object, Object> attributes = (LinkedHashMap) contactData.get("Attributes");
        return String.format("{ \"coronaCase\": %s, \"callCenterRedirection\": %s}", attributes.get("coronaCase"), attributes.get("callCenterRedirection"));
    }

    private void closeClient(final Context context, final CloseableHttpClient httpClient) {
        try {
            httpClient.close();
        } catch (IOException e) {
            context.getLogger().log(e.toString());
        }
    }
}
