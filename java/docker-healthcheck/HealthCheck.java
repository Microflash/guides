import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.stream.IntStream;

public class HealthCheck {

  public static void main(String[] args) throws InterruptedException, IOException {
    var client = HttpClient.newHttpClient();
    var request = HttpRequest.newBuilder()
        .uri(URI.create("http://localhost:8080/actuator/health"))
        .header("accept", "application/json")
        .build();

    var response = client.send(request, BodyHandlers.ofString());
    var ok = response.statusCode() == 200 && checkStatus(response.body());
    System.out.println(ok);

    if (!ok) {
      throw new RuntimeException("Healthcheck failed");
    }
  }

  private static boolean checkStatus(final String responseBody) {
    final var key = "status";
    final var startOffset = key.length() + 3;
    final var stopOffset = key.length() + 5;
    return IntStream
        .iterate(
            responseBody.indexOf(key),
            index -> index >= 0,
            index -> responseBody.indexOf(key, index + 1)
        ).boxed()
        .map(index -> responseBody.substring(index + startOffset, index + stopOffset))
        .allMatch(s -> s.equalsIgnoreCase("UP"));
  }
}
