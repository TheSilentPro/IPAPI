package tsp.ipapi.implementation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IPRequester {

    public static final String BASE_URL = "http://ip-api.com/";
    private List<IPEntry> entries = new ArrayList<>();
    private ExecutorService executor;

    public IPRequester(ExecutorService executor) {
        this.executor = executor;
    }

    public IPRequester() {
        this(Executors.newSingleThreadExecutor());
    }

    public IPRequester add(IPEntry... ips) {
        this.entries.addAll(Arrays.asList(ips));
        return this;
    }

    public IPRequester add(String address, IPResponseLang lang, EnumSet<Field> fields) {
        this.entries.add(new IPEntry(address, lang, fields));
        return this;
    }

    public IPRequester add(String address, EnumSet<Field> fields) {
        this.entries.add(new IPEntry(address, IPResponseLang.ENGLISH, fields));
        return this;
    }

    public List<IPEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<IPEntry> entries) {
        this.entries = entries;
    }

    public String buildURL() {
        String req;
        if (entries.size() == 1) {
            req = BASE_URL + "json/" + entries.get(0).toString();
        } else {
            req = BASE_URL + "batch/";
        }
        return req;
    }

    public CompletableFuture<IPResponse> fetch(Duration timeout) {
        return CompletableFuture.supplyAsync(() -> {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(timeout)
                    .build();

            if (entries.size() == 1) {
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URI(buildURL()))
                            .header("User-Agent", "IPAPI/4.0.0")
                            .GET()
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    return parseSingle(
                            JsonParser.parseString(response.body()).getAsJsonObject(),
                            response.headers().firstValueAsLong("X-Rl").orElse(-1),
                            response.headers().firstValueAsLong("X-Ttl").orElse(-1)
                    );
                } catch (URISyntaxException | IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                JsonArray array = new JsonArray();
                for (IPEntry entry : entries) {
                    JsonObject obj = new JsonObject();
                    obj.addProperty("query", entry.ip());
                    obj.addProperty("lang", entry.lang().getCode());

                    EnumSet<Field> fields = entry.fields();
                    // QUERY field is required for BATCH requests!
                    if (!fields.contains(Field.QUERY)) {
                        fields.add(Field.QUERY);
                    }

                    StringBuilder builder = new StringBuilder();
                    int index = 0;
                    for (Field field : fields) {
                        index++;
                        builder.append(field.getName());
                        if (fields.size() > index) {
                            builder.append(",");
                        }
                    }
                    obj.addProperty("fields", builder.toString());

                    array.add(obj);
                }

                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URI(buildURL()))
                            .header("User-Agent", "IPAPI/4.0.0")
                            .POST(HttpRequest.BodyPublishers.ofString(array.toString()))
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    return parseBatch(
                            JsonParser.parseString(response.body()).getAsJsonArray(),
                            response.headers().firstValueAsLong("X-Rl").orElse(-1),
                            response.headers().firstValueAsLong("X-Ttl").orElse(-1)
                    );
                } catch (URISyntaxException | IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, executor);
    }

    public CompletableFuture<IPResponse> fetch() {
        return fetch(Duration.ofSeconds(5));
    }

    private IPResponse parseSingle(JsonObject main, long left, long reset) {
        return new IPResponse(Collections.singletonMap(entries.get(0).ip(), parseFields(main)), left, reset);
    }

    private IPResponse parseBatch(JsonArray main, long left, long reset) {
        Map<String, Map<Field, String>> values = new HashMap<>();
        for (JsonElement entry : main) {
            JsonObject obj = entry.getAsJsonObject();
            values.put(obj.get("query").getAsString(), parseFields(obj));
        }
        return new IPResponse(values, left, reset);
    }

    private Map<Field, String> parseFields(JsonObject main) {
        Map<Field, String> responses = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : main.entrySet()) {
            responses.put(Field.matchByName(entry.getKey()), entry.getValue().getAsString());
        }
        return responses;
    }

}