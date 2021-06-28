package tsp.ipapi.single;

import tsp.ipapi.Field;

import java.util.Map;

/**
 * Makes requests and gets the response
 *
 * @author TheSilentPro
 */
public class IPResponse {

    private final Map<Field, String> responses;
    private final int left;
    private final int reset;

    public IPResponse(Map<Field, String> responses, int left, int reset) {
        this.responses = responses;
        this.left = left;
        this.reset = reset;
    }

    /**
     * Gets the field responses as a map
     *
     * @return Responses
     */
    public Map<Field, String> getFields() {
        return responses;
    }

    /**
     * Gets a specific field value from the responses
     *
     * @param field The field value to get
     * @return The field value
     */
    public String getField(Field field) {
        if (!responses.containsKey(field)) throw new NullPointerException("That field was not requested.");
        return responses.get(field);
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
