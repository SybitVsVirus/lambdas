package client;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.commons.lang3.BooleanUtils;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.nonNull;

/**
 * Handler for requests to Lambda function.
 */
public class SavePatientFunction implements RequestHandler<LinkedHashMap<Object, Object>, Object> {

    private static final String DYNAMODB_TABLE_NAME = "patient_data";

    public Map<String, String> handleRequest(final LinkedHashMap<Object, Object> input, final Context context) {
        final AmazonDynamoDB db = AmazonDynamoDBClientBuilder.defaultClient();
        final Map<String, AttributeValue> data = new HashMap<>();
        final Map<String, String> response = new HashMap<>();
        final UUID uuid = UUID.randomUUID();
        LinkedHashMap<Object, Object> details = (LinkedHashMap) input.get("Details");
        LinkedHashMap<Object, Object> contactData = (LinkedHashMap) details.get("ContactData");
        LinkedHashMap<Object, Object> attributes = (LinkedHashMap) contactData.get("Attributes");
        context.getLogger().log(String.valueOf(details));
        context.getLogger().log(String.valueOf(attributes));

        response.put("clientId", uuid.toString());

        data.put("patient_id", new AttributeValue().withS(uuid.toString()));

        if (nonNull(attributes.get("contact"))) {
            data.put("contact", new AttributeValue().withBOOL(Boolean.valueOf(attributes.get("contact").toString())));
        }
        if (nonNull(attributes.get("coughing"))) {
            data.put("coughing", new AttributeValue().withBOOL(BooleanUtils.toBoolean(attributes.get("coughing").toString())));
        }
        if (nonNull(attributes.get("fever"))) {
            data.put("fever", new AttributeValue().withBOOL(Boolean.valueOf(attributes.get("fever").toString())));
        }
        if (nonNull(attributes.get("otherSymptoms"))) {
            data.put("otherSymptoms", new AttributeValue().withBOOL(Boolean.valueOf(attributes.get("otherSymptoms").toString())));
        }
        if (nonNull(attributes.get("regionAtRisk"))) {
            data.put("regionAtRisk", new AttributeValue().withBOOL(Boolean.valueOf(attributes.get("regionAtRisk").toString())));
        }
        if (nonNull(attributes.get("phoneNumber"))) {
            data.put("phoneNumber", new AttributeValue().withS(attributes.get("phoneNumber").toString()));
        }

        db.putItem(new PutItemRequest(DYNAMODB_TABLE_NAME, data));
        return response;
    }
}
