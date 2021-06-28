package tsp.ipapi;

import org.json.simple.parser.ParseException;
import tsp.ipapi.batch.BatchIPRequester;
import tsp.ipapi.batch.BatchIPResponse;
import tsp.ipapi.single.IPRequester;
import tsp.ipapi.single.IPResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * API for ip-api.com
 * Dependency: json-simple -> https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple
 *
 * @author TheSilentPro
 */
public final class IPAPI {

    private IPAPI() {}

    public static IPResponse query(String ip) throws IOException, ParseException {
        return new IPRequester(ip).execute();
    }

    public static BatchIPResponse queryBatch(Map<String, List<Field>> ips) throws IOException, ParseException {
        return new BatchIPRequester(ips).execute();
    }

    public static void main(String[] args) throws IOException, ParseException {
        IPResponse ipResponse = new IPRequester("8.8.8.8")
                .withCurrency()
                .withZip()
                .execute();
        BatchIPResponse batchIPResponse = new BatchIPRequester()
                .addIP("8.8.4.4", Field.CITY, Field.CONTINENT)
                .addIP("24.48.0.1", Field.STATUS, Field.COUNTRY)
                .execute();

        System.out.println(" > Single IP");
        for (Map.Entry<Field, String> entry : ipResponse.getFields().entrySet()) {
            System.out.println(entry.getKey().getName() + ": " + entry.getValue());
        }

        System.out.println("\n > Batch IPs");
        for (Map.Entry<String, Map<Field, String>> ip : batchIPResponse.getResponses().entrySet()) {
            Map<Field, String> entries = batchIPResponse.getFields(ip.getKey());
            System.out.println(" IP: " + ip.getKey());
            entries.forEach((field, value) -> System.out.println(field.getName() +  ": " + value));
            System.out.println(" ");
        }
    }

}
