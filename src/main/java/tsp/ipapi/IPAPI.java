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

}
