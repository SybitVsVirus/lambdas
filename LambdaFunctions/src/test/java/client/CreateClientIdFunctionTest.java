package client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CreateClientIdFunctionTest {

  @Test
  public void successfulResponse() {
    CreateClientIdFunction createClientIdFunction = new CreateClientIdFunction();
    GatewayResponse result = (GatewayResponse) createClientIdFunction.handleRequest(null, null);
    assertEquals(result.getStatusCode(), 200);
    assertEquals(result.getHeaders().get("Content-Type"), "application/json");
    String content = result.getBody();
    assertNotNull(content);
    assertTrue(content.contains("\"clientId\""));
  }
}
