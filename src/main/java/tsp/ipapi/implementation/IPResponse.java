package tsp.ipapi.implementation;

import java.util.Map;

/**
 * This holds data from a response.
 *
 * @param responses The field responses from the provided ips. Format: IP, FIELD, VALUE
 * @param left The requests you have left.
 * @param reset The time until your requests reset in seconds.
 */
public record IPResponse(Map<String, Map<Field, String>> responses, long left, long reset) {}