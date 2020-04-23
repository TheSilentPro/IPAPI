package tsp.ipapi;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Utility class for IP-API.com
 * Responses are formatted in JSON
 * Dependency: json-simple -> https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple
 *
 * @author Silent
 * @version 1.0
 */
public class IPAPI {

    /**
     * Base URL for checking ips
     */
    private static String BASE_URL = "http://ip-api.com/json/";

    /**
     * Check an IP address with default return values
     *
     * @param ip The ip to check
     * @return The results
     * @throws IOException Something went wrong
     * @since 1.0
     */
    public static JSONObject check(String ip) throws IOException, ParseException {
        return check(ip, null, null, 5000);
    }

    /**
     * Check an IP address with specified timeout
     *
     * @param ip The ip to check
     * @param timeout Time until connection gives up
     * @return The results
     * @throws IOException Something went wrong
     * @since 1.0
     */
    public static JSONObject check(String ip, int timeout) throws IOException, ParseException {
        return check(ip, null, null, timeout);
    }

    /**
     * Check an IP address with specified
     * timeout, fields, lang
     *
     * @param ip The ip to check
     * @param fields The fields you want the ip to return. List of fields: https://ip-api.com/docs/api:json
     * @param lang Lang code
     * @param timeout Time until connection gives up
     * @return The results
     * @throws IOException Something went wrong
     * @since 1.0
     */
    public static JSONObject check(String ip, String fields, String lang, int timeout) throws IOException, ParseException {
        if (fields == null) {
            fields = "33292287";
        }
        if (lang == null) {
            lang = "en";
        }
        StringBuilder response = new StringBuilder();
        URL website = new URL(BASE_URL + ip + "?fields=" + fields  + "&lang=" + lang);
        URLConnection connection = website.openConnection();
        connection.setConnectTimeout(timeout);
        connection.setRequestProperty("User-Agent", "IPAPI-v1.0");
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()))) {
            while ((BASE_URL = in.readLine()) != null) {
                response.append(BASE_URL);
            }
        }
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(response.toString());
    }

}
