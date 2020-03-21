package client;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Handler for requests to Lambda function.
 */
public class CreateClientIdTestFunction implements RequestHandler<Object, Object> {

    public Map<String, String> handleRequest(final Object input, final Context context) {
        Map<String, String> result = new HashMap<>();
        final UUID clientId = UUID.randomUUID();
        result.put("clientId", clientId.toString());
        return result;
    }
}
