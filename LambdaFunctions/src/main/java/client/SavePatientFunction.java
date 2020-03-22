package client;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.commons.lang3.BooleanUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.nonNull;

/**
 * Handler for requests to Lambda function.
 */
public class SavePatientFunction implements RequestHandler<Object, Object> {

    private static final String DYNAMODB_TABLE_NAME = "patient_data";

    public Map<String, String> handleRequest(final Object input, final Context context) {
        final AmazonDynamoDB db = AmazonDynamoDBClientBuilder.defaultClient();
        final Map<String, AttributeValue> data = new HashMap<>();
        final Map<String, String> response = new HashMap<>();
        final UUID uuid = UUID.randomUUID();
        context.getLogger().log(String.valueOf(input));
        final JSONObject inputData = (JSONObject) input;

        response.put("clientId", uuid.toString());

        data.put("patient_id", new AttributeValue().withS(uuid.toString()));

        if (nonNull(inputData.get("contact"))) {
            data.put("contact", new AttributeValue().withBOOL(Boolean.valueOf(inputData.get("contact").toString())));
        }
        if (nonNull(inputData.get("coughing"))) {
            data.put("coughing", new AttributeValue().withBOOL(BooleanUtils.toBoolean(inputData.get("coughing").toString())));
        }
        if (nonNull(inputData.get("fever"))) {
            data.put("fever", new AttributeValue().withBOOL(Boolean.valueOf(inputData.get("fever").toString())));
        }
        if (nonNull(inputData.get("otherSymptoms"))) {
            data.put("otherSymptoms", new AttributeValue().withBOOL(Boolean.valueOf(inputData.get("otherSymptoms").toString())));
        }
        if (nonNull(inputData.get("regionAtRisk"))) {
            data.put("regionAtRisk", new AttributeValue().withBOOL(Boolean.valueOf(inputData.get("regionAtRisk").toString())));
        }
        if (nonNull(inputData.get("phoneNumber"))) {
            data.put("phoneNumber", new AttributeValue().withS(inputData.get("phoneNumber").toString()));
        }

        db.putItem(new PutItemRequest(DYNAMODB_TABLE_NAME, data));
        return response;
    }
}
