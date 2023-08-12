package tsp.ipapi;

import tsp.ipapi.implementation.*;

import java.util.EnumSet;
import java.util.concurrent.CompletableFuture;

/**
 * API for ip-api.com
 *
 * @author TheSilentPro
 */
public final class IPAPI {

    private IPAPI() {}

    public CompletableFuture<IPResponse> query(IPEntry... ips) {
        return new IPRequester().add(ips).fetch();
    }

    public CompletableFuture<IPResponse> query(String... ips) {
        IPRequester requester = new IPRequester();
        for (String ip : ips) {
            requester.add(new IPEntry(ip, IPResponseLang.ENGLISH, EnumSet.allOf(Field.class)));
        }
        return requester.fetch();
    }

}
