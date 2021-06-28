package tsp.ipapi.batch;

import tsp.ipapi.Field;

import java.util.Map;

public class BatchIPResponse {

    // Map Formatting: <IP, <Field, Value>>
    private final Map<String, Map<Field, String>> responses;
    private final int left = -1;
    private final int reset = -1;

    public BatchIPResponse(Map<String, Map<Field, String>> responses) {
        this.responses = responses;
        //this.left = left;
        //this.reset = reset;
    }

    /**
     * Gets the responses as a map
     *
     * @return Responses
     */
    public Map<String, Map<Field, String>> getResponses() {
        return responses;
    }

    /**
     * Gets the field responses of a specific ip
     *
     * @param ip The ip to get the responses for
     * @return Responses
     */
    public Map<Field, String> getFields(String ip) {
        return responses.get(ip);
    }

    /**
     * Gets a specific field value from an ip responses
     *
     * @param ip The ip to get the field from
     * @param field The field value to get
     * @return The field value
     */
    public String getField(String ip, Field field) {
        return responses.get(ip).get(field);
    }

    /**
     * Gets the requests left for this ip
     *
     * @return The requests left
     */
    public int getRequestsLeft() {
        return left;
    }

    /**
     * Gets the time until the requests left for this ip reset
     *
     * @return The time until reset
     */
    public int getResetTime() {
        return reset;
    }

}
