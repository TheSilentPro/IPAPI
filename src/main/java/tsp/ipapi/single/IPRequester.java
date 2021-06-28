package tsp.ipapi.single;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tsp.ipapi.Field;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IPRequester {

    /**
     * Base URL for checking ips
     */
    public static final String BASE_URL = "http://ip-api.com/json/";

    private final String ip;
    private String lang = "en";
    private final List<Field> fields = new ArrayList<>();

    public IPRequester(String ip) {
        this.ip = ip;
    }

    public IPRequester withStatus() {
        fields.add(Field.STATUS);
        return this;
    }

    public IPRequester withMessage() {
        fields.add(Field.MESSAGE);
        return this;
    }

    public IPRequester withContinent() {
        fields.add(Field.CONTINENT);
        return this;
    }

    public IPRequester withContinentCode() {
        fields.add(Field.CONTINENT_CODE);
        return this;
    }

    public IPRequester withCountry() {
        fields.add(Field.COUNTRY);
        return this;
    }

    public IPRequester withCountryCode() {
        fields.add(Field.COUNTRY_CODE);
        return this;
    }

    public IPRequester withRegion() {
        fields.add(Field.REGION);
        return this;
    }

    public IPRequester withRegionName() {
        fields.add(Field.REGION_NAME);
        return this;
    }

    public IPRequester withCity() {
        fields.add(Field.CITY);
        return this;
    }

    public IPRequester withDistrict() {
        fields.add(Field.DISTRICT);
        return this;
    }

    public IPRequester withZip() {
        fields.add(Field.ZIP);
        return this;
    }

    public IPRequester withLatitude() {
        fields.add(Field.LATITUDE);
        return this;
    }

    public IPRequester withLongitude() {
        fields.add(Field.LONGITUDE);
        return this;
    }

    public IPRequester withTimezone() {
        fields.add(Field.TIMEZONE);
        return this;
    }

    public IPRequester withOffset() {
        fields.add(Field.OFFSET);
        return this;
    }

    public IPRequester withCurrency() {
        fields.add(Field.CURRENCY);
        return this;
    }

    public IPRequester withInternetServiceProvider() {
        fields.add(Field.INTERNET_SERVICE_PROVIDER);
        return this;
    }

    public IPRequester withOrganization() {
        fields.add(Field.ORGANIZATION);
        return this;
    }

    public IPRequester withAs() {
        fields.add(Field.AS);
        return this;
    }

    public IPRequester withAsName() {
        fields.add(Field.AS_NAME);
        return this;
    }

    public IPRequester withReverse() {
        fields.add(Field.REVERSE);
        return this;
    }

    public IPRequester withMobile() {
        fields.add(Field.MOBILE);
        return this;
    }

    public IPRequester withProxy() {
        fields.add(Field.PROXY);
        return this;
    }

    public IPRequester withHosting() {
        fields.add(Field.HOSTING);
        return this;
    }

    public IPRequester withQuery() {
        fields.add(Field.QUERY);
        return this;
    }

    public IPRequester all() {
        fields.addAll(Arrays.asList(Field.values()));
        return this;
    }

    public IPRequester language(String language) {
        this.lang = language;
        return this;
    }

    /**
     * Allows for specifying all needed fields at once
     *
     * Added in 3.0 for faster/cleaner chaining
     *
     * @param fields The fields to add for requesting
     */
    public IPRequester withFields(Field... fields) {
        this.fields.addAll(Arrays.asList(fields));
        return this;
    }

    public IPRequester withField(Field field) {
        this.fields.add(field);
        return this;
    }

    public String buildURL() throws IOException {
        String req = BASE_URL + ip + "?fields=";
        StringBuilder builder = new StringBuilder();
        int index = 0;
        for (Field field : fields) {
            index++;
            builder.append(field.getName());
            if (fields.size() > index) {
                builder.append(",");
            }
        }
        req = req + builder.toString() + "&lang=" + lang;
        return req;
    }

    /**
     * Executes the request with the given timeout
     *
     * @param timeout Time until the connection gives up
     * @return The response
     * @throws IOException  Thrown if something fails
     * @throws ParseException Thrown if the JSON parsing fails
     */
    public IPResponse execute(int timeout) throws IOException, ParseException {
        String line;
        int left;
        int reset;
        Map<Field, String> responses = new HashMap<>();

        StringBuilder response = new StringBuilder();
        URLConnection connection = new URL(buildURL()).openConnection();
        connection.setConnectTimeout(timeout);
        connection.setRequestProperty("User-Agent", "IPAPI-3.0");
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

        return new IPResponse(responses, left, reset);
    }

    /**
     * Executes the request with the default timeout
     *
     * @return The response
     * @throws IOException  Thrown if something fails
     * @throws ParseException Thrown if the JSON parsing fails
     */
    public IPResponse execute() throws IOException, ParseException {
        return execute(5000);
    }
    
}
