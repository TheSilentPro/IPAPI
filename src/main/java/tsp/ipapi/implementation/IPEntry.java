package tsp.ipapi.implementation;

import java.util.Arrays;
import java.util.EnumSet;

public record IPEntry(String ip, IPResponseLang lang, EnumSet<Field> fields) {

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(ip).append("?lang=").append(lang.getCode()).append("&fields=");
        int index = 0;
        for (Field field : fields) {
            index++;
            builder.append(field.getName());
            if (fields.size() > index) {
                builder.append(",");
            }
        }

        return builder.toString();
    }

    public static class Builder {
        
        private String ip;
        private IPResponseLang lang = IPResponseLang.ENGLISH;
        private final EnumSet<Field> fields = EnumSet.noneOf(Field.class);
        
        public Builder() {}

        public Builder(String ip) {
            this.ip = ip;
        }

        public Builder withIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder withStatus() {
            fields.add(Field.STATUS);
            return this;
        }

        public Builder withMessage() {
            fields.add(Field.MESSAGE);
            return this;
        }

        public Builder withContinent() {
            fields.add(Field.CONTINENT);
            return this;
        }

        public Builder withContinentCode() {
            fields.add(Field.CONTINENT_CODE);
            return this;
        }

        public Builder withCountry() {
            fields.add(Field.COUNTRY);
            return this;
        }

        public Builder withCountryCode() {
            fields.add(Field.COUNTRY_CODE);
            return this;
        }

        public Builder withRegion() {
            fields.add(Field.REGION);
            return this;
        }

        public Builder withRegionName() {
            fields.add(Field.REGION_NAME);
            return this;
        }

        public Builder withCity() {
            fields.add(Field.CITY);
            return this;
        }

        public Builder withDistrict() {
            fields.add(Field.DISTRICT);
            return this;
        }

        public Builder withZip() {
            fields.add(Field.ZIP);
            return this;
        }

        public Builder withLatitude() {
            fields.add(Field.LATITUDE);
            return this;
        }

        public Builder withLongitude() {
            fields.add(Field.LONGITUDE);
            return this;
        }

        public Builder withTimezone() {
            fields.add(Field.TIMEZONE);
            return this;
        }

        public Builder withOffset() {
            fields.add(Field.OFFSET);
            return this;
        }

        public Builder withCurrency() {
            fields.add(Field.CURRENCY);
            return this;
        }

        public Builder withInternetServiceProvider() {
            fields.add(Field.INTERNET_SERVICE_PROVIDER);
            return this;
        }

        public Builder withOrganization() {
            fields.add(Field.ORGANIZATION);
            return this;
        }

        public Builder withAs() {
            fields.add(Field.AS);
            return this;
        }

        public Builder withAsName() {
            fields.add(Field.AS_NAME);
            return this;
        }

        public Builder withReverse() {
            fields.add(Field.REVERSE);
            return this;
        }

        public Builder withMobile() {
            fields.add(Field.MOBILE);
            return this;
        }

        public Builder withProxy() {
            fields.add(Field.PROXY);
            return this;
        }

        public Builder withHosting() {
            fields.add(Field.HOSTING);
            return this;
        }

        public Builder withQuery() {
            fields.add(Field.QUERY);
            return this;
        }

        public Builder all() {
            fields.addAll(Arrays.asList(Field.values()));
            return this;
        }

        public Builder language(IPResponseLang language) {
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
        public Builder withFields(Field... fields) {
            this.fields.addAll(Arrays.asList(fields));
            return this;
        }

        public Builder withField(Field field) {
            this.fields.add(field);
            return this;
        }

        public IPEntry build() {
            return new IPEntry(ip, lang, fields);
        }
        
    }
    
}