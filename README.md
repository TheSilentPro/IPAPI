### IPAPI

Allows you to make requests to ip-api.com easily <br>

### Dependencies
[json-simple](https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple)

### Installation
You may download it from the [Releases Page](https://github.com/TheSilentPro/IPAPI/releases) <br>
You can also you [JitPack](https://jitpack.io/#TheSilentPro/IPAPI)

### Usage
```
Response response = new Builder("1.1.1.1")
      .all() // Adds all fields to the request
      .build() // builds the request
      .makeRequest(); // Makes the request to the API with the builded request
Set<Map.Entry<Field, String>> list = response.getResponses(); // Get a list of responses for the requested fields
String value = response.getValue(field); // Get the value of a field
int left = response.getRequestsLeft(); // Gets the requests left for this ip
int reset = response.getResetTime(); // Gets the time until the requests left for this ip reset
```

### Example
Getting the status, country, and city
```
        Response response = new Builder("1.1.1.1")
                .withStatus()
                .withCountry()
                .withCity()
                .build().makeRequest();
        String status = response.getValue(Field.STATUS);
        String country = response.getValue(Field.COUNTRY);
        String city = response.getValue(Field.CITY);
        System.out.println("Status: " + status);
        System.out.println("Country: " + country);
        System.out.println("City: " + city);
```

Getting all fields and printing them
```java
    public static void main(String[] args) throws IOException, ParseException {
        Response response = new Builder("1.1.1.1")
                .all()
                .build().makeRequest();

        for (Map.Entry<Field, String> f : response.getResponses()) {
            System.out.println(f.getKey().getName() + ": " + f.getValue());
        }
        System.out.println("Requests Left: " + response.getRequestsLeft());
        System.out.println("Reset Time: " + response.getResetTime());
    }
```

### [LICENSE](https://github.com/TheSilentPro/IPAPI/blob/master/LICENSE)
This API is licensed under GNU General Public License v3.0
