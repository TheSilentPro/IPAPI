package tsp.ipapi;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Makes requests and gets the response
 *
 * @author TheSilentPro
 */
public class Response {

    private final URL url;
    private final Map<Field, String> responses = new HashMap<>();
    private int left;
    private int reset;

    /**
     * Creates a new Response Object
     *
     * @param url URL used for requests
     */
    public Response(URL url) {
        this.url = url;
    }

    /**
     * Gets the responses as a map
     *
     * @return Responses
     */
    public Set<Map.Entry<Field, String>> getResponses() {
        if (responses.isEmpty()) throw new NullPointerException("You must use makeRequest() first.");
        return responses.entrySet();
    }

    /**
     * Gets a specific field value from the responses
     *
     * @param field The field value to get
     * @return The field value
     */
    public String getValue(Field field) {
        if (responses.isEmpty()) throw new NullPointerException("You must use makeRequest() first.");
        if (!responses.containsKey(field)) throw new NullPointerException("That field was not requested.");
        return responses.get(field);
    }

    /**
     * Gets the requests left for this ip
     *
     * @return The requests left
     */
    public int getRequestsLeft() {
        if (responses.isEmpty()) throw new NullPointerException("You must use makeRequest() first.");
        return left;
    }

    /**
     * Gets the time until the requests left for this ip reset
     *
     * @return The time until reset
     */
    public int getResetTime() {
        if (responses.isEmpty()) throw new NullPointerException("You must use makeRequest() first.");
        return reset;
    }

    /**
     * Makes the request to the URL with the given timeout
     *
     * @return The response
     * @throws IOException  Thrown if something fails
     * @throws ParseException Thrown if the JSON parsing fails
     */
    public Response makeRequest() throws IOException, ParseException {
        return makeRequest(5000);
    }

    /**
     * Makes the request to the URL with the given timeout
     *
     * @param timeout Time until the connection gives up
     * @return The response
     * @throws IOException  Thrown if something fails
     * @throws ParseException Thrown if the JSON parsing fails
     */
    public Response makeRequest(int timeout) throws IOException, ParseException {
        String line;
        StringBuilder response = new StringBuilder();
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(timeout);
        connection.setRequestProperty("User-Agent", "IPAPI-v2.0");
        left = connection.getHeaderFieldInt("X-Rl", -1);
        reset = connection.getHeaderFieldInt("X-Ttl", -1);
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()))) {
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        }
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(response.toString());
        for (Object entry : obj.entrySet()) {
            String str = entry.toString();
            String k = str.substring(0, str.indexOf("="));
            String v = str.substring(str.indexOf("=") + 1);
            responses.put(Field.matchByName(k), v);
        }
        return this;
    }

}
