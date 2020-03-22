package client;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handler for requests to Lambda function.
 */
public class SavePatientFunction implements RequestHandler<PatientRequest, Object> {

    private static final String DYNAMODB_TABLE_NAME = "patient_data";

    public Map<String, String> handleRequest(final PatientRequest input, final Context context) {
        final AmazonDynamoDB db = AmazonDynamoDBClientBuilder.defaultClient();
        final Map<String, AttributeValue> data = new HashMap<>();
        final Map<String, String> response = new HashMap<>();
        final UUID uuid = UUID.randomUUID();
        response.put("clientId", uuid.toString());
        data.put("patient_id", new AttributeValue().withS(uuid.toString()));
        data.put("contact", new AttributeValue().withBOOL(Boolean.valueOf(input.contact)));
        data.put("coughing", new AttributeValue().withBOOL(Boolean.valueOf(input.coughing)));
        data.put("fever", new AttributeValue().withBOOL(Boolean.valueOf(input.fever)));
        data.put("otherSymptoms", new AttributeValue().withBOOL(Boolean.valueOf(input.otherSymptoms)));
        data.put("regionAtRisk", new AttributeValue().withBOOL(Boolean.valueOf(input.regionAtRisk)));
        data.put("phoneNumber", new AttributeValue().withS(input.phoneNumber));
        db.putItem(new PutItemRequest(DYNAMODB_TABLE_NAME, data));

        return response;
    }
}
