### IPAPI

Allows you to make requests to ip-api.com easily <br>

### Dependencies
[json-simple](https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple)
[OkHttpClient](https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp)

### Installation
You may download it from the [Releases Page](https://github.com/TheSilentPro/IPAPI/releases) <br>
You can also you [JitPack](https://jitpack.io/#TheSilentPro/IPAPI)

### Example Usage
```
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
```

### [LICENSE](https://github.com/TheSilentPro/IPAPI/blob/master/LICENSE)
This API is licensed under GNU General Public License v3.0
