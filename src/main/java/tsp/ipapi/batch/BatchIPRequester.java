package tsp.ipapi.batch;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tsp.ipapi.Field;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BatchIPRequester {

    /**
     * Base URL for checking ips
     */
    public static final String BASE_URL = "http://ip-api.com/batch/";

    private Map<String, List<Field>> ips;

    public BatchIPRequester() {
        ips = new HashMap<>();
    }

    public BatchIPRequester(Map<String, List<Field>> ips) {
        this.ips = ips;
    }

    public BatchIPRequester addIP(String ip, Field... fields) {
        ips.put(ip, Arrays.asList(fields));
        return this;
    }

    public BatchIPRequester addIPs(Map<String, List<Field>> ips) {
        this.ips = ips;
        return this;
    }

    public BatchIPResponse execute(int timeout) throws IOException, ParseException {
        // Format everything in json
        JSONArray array = new JSONArray();
        for (Map.Entry<String, List<Field>> entry : ips.entrySet()) {
            JSONObject obj = new JSONObject();
            obj.put("query", entry.getKey());
            obj.put("fields", formatFields(entry.getValue()));
            array.add(obj);
        }

        Map<String, Map<Field, String>> responses = new HashMap<>();

        // Make the request
        String response = makeRequest(array);
        JSONParser parser = new JSONParser();
        JSONArray arrayResponse = (JSONArray) parser.parse(response.toString());
        for (int i = 0; i < arrayResponse.size(); i++) {
            JSONObject obj = (JSONObject) arrayResponse.get(i);
            Map<Field, String> fields = new HashMap<>();
            Iterator<?> keys = obj.keySet().iterator();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                fields.put(Field.matchByName(key), obj.get(key).toString());
            }

            responses.put(obj.get("query").toString(), fields);
        }

        return new BatchIPResponse(responses);
    }

    public BatchIPResponse execute() throws IOException, ParseException {
        return execute(5000);
    }

    private String makeRequest(JSONArray array) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(array.toJSONString(), MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(new URL(BASE_URL))
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private List<Field> getFields(String fieldsObject) {
        List<Field> fields = new ArrayList<>();
        String[] args = fieldsObject.split(",");
        for (String arg : args) {
            fields.add(Field.matchByName(arg));
        }

        return fields;
    }

    private String formatFields(List<Field> fields) {
        StringBuilder builder = new StringBuilder();
        int index = 0;
        for (Field field : fields) {
            index++;
            builder.append(field.getName());
            if (fields.size() > index) {
                builder.append(",");
            }
        }

        // The response must have the query field for the api to identify it
        if (!builder.toString().contains("query")) {
            builder.append(",query");
        }

        return builder.toString();
    }

}
