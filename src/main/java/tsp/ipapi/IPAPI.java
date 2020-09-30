package tsp.ipapi;

import tsp.ipapi.v1.IPAPILegacy;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Version 2 of the {@link IPAPILegacy}
 *
 * API for ip-api.com
 * Dependency: json-simple -> https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple
 *
 * @author TheSilentPro
 * @version 2.0
 */
public class IPAPI {

    /**
     * Base URL for checking ips
     */
    private static final String BASE_URL = "http://ip-api.com/json/";

    public static class Builder {

        private final String ip;
        private String lang = "en";
        private final Map<Field, Boolean> fields = new HashMap<>();

        public Builder(String ip) {
            this.ip = ip;
        }

        public Builder withStatus() {
            fields.put(Field.STATUS, true);
            return this;
        }

        public Builder withMessage() {
            fields.put(Field.MESSAGE, true);
            return this;
        }

        public Builder withContinent() {
            fields.put(Field.CONTINENT, true);
            return this;
        }

        public Builder withContinentCode() {
            fields.put(Field.CONTINENT_CODE, true);
            return this;
        }

        public Builder withCountry() {
            fields.put(Field.COUNTRY, true);
            return this;
        }

        public Builder withCountryCode() {
            fields.put(Field.COUNTRY_CODE, true);
            return this;
        }

        public Builder withRegion() {
            fields.put(Field.REGION, true);
            return this;
        }

        public Builder withRegionName() {
            fields.put(Field.REGION_NAME, true);
            return this;
        }

        public Builder withCity() {
            fields.put(Field.CITY, true);
            return this;
        }

        public Builder withDistrict() {
            fields.put(Field.DISTRICT, true);
            return this;
        }

        public Builder withZip() {
            fields.put(Field.ZIP, true);
            return this;
        }

        public Builder withLatitude() {
            fields.put(Field.LATITUDE, true);
            return this;
        }

        public Builder withLongitude() {
            fields.put(Field.LONGITUDE, true);
            return this;
        }

        public Builder withTimezone() {
            fields.put(Field.TIMEZONE, true);
            return this;
        }

        public Builder withOffset() {
            fields.put(Field.OFFSET, true);
            return this;
        }

        public Builder withCurrency() {
            fields.put(Field.CURRENCY, true);
            return this;
        }

        public Builder withInternetServiceProvider() {
            fields.put(Field.INTERNET_SERVICE_PROVIDER, true);
            return this;
        }

        public Builder withOrganization() {
            fields.put(Field.ORGANIZATION, true);
            return this;
        }

        public Builder withAs() {
            fields.put(Field.AS, true);
            return this;
        }

        public Builder withAsName() {
            fields.put(Field.AS_NAME, true);
            return this;
        }

        public Builder withReverse() {
            fields.put(Field.REVERSE, true);
            return this;
        }

        public Builder withMobile() {
            fields.put(Field.MOBILE, true);
            return this;
        }

        public Builder withProxy() {
            fields.put(Field.PROXY, true);
            return this;
        }

        public Builder withHosting() {
            fields.put(Field.HOSTING, true);
            return this;
        }

        public Builder withQuery() {
            fields.put(Field.QUERY, true);
            return this;
        }

        public Builder all() {
            for (Field field : Field.values()) {
                fields.put(field, true);
            }
            return this;
        }

        public Builder withLanguage(String language) {
            this.lang = language;
            return this;
        }

        public Response build() throws IOException {
            String req = BASE_URL + ip + "?fields=";
            StringBuilder builder = new StringBuilder();
            int index = 0;
            for (Map.Entry<Field, Boolean> map : fields.entrySet()) {
                if (map.getValue()) {
                    index++;
                    builder.append(map.getKey().getName());
                    if (fields.keySet().size() > index) {
                        builder.append(",");
                    }

                }
            }
            req = req + builder.toString() + "&lang=" + lang;
            return new Response(new URL(req));
        }

    }

}
