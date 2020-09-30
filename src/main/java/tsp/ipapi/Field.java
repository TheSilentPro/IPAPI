package tsp.ipapi;

/**
 * All available field types
 *
 * @author TheSilentPro
 */
public enum Field {

    STATUS("status"),
    MESSAGE("message"),
    CONTINENT("continent"),
    CONTINENT_CODE("continentCode"),
    COUNTRY("country"),
    COUNTRY_CODE("countryCode"),
    REGION("region"),
    REGION_NAME("regionName"),
    CITY("city"),
    DISTRICT("district"),
    ZIP("zip"),
    LATITUDE("lat"),
    LONGITUDE("lon"),
    TIMEZONE("timezone"),
    OFFSET("offset"),
    CURRENCY("currency"),
    INTERNET_SERVICE_PROVIDER("isp"),
    ORGANIZATION("org"),
    AS("as"),
    AS_NAME("asName"),
    REVERSE("reverse"),
    MOBILE("mobile"),
    PROXY("proxy"),
    HOSTING("hosting"),
    QUERY("query");

    private final String name;

    Field(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Matches a field by its name
     *
     * @param name The name of the field
     * @return Matched field, returns null if there was no match
     */
    public static Field matchByName(String name) {
        for (Field field : Field.values()) {
            if (field.getName().equals(name)) {
                return field;
            }
        }

        return null;
    }

}
