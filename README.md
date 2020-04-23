### IPAPI

Allows you to make requests to ip-api.com easily <br>
Returns a `JSONObject` with response from the API <br>

### Dependencies
[json-simple](https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple)

### Downloading
You may download it from the [Releases Page](https://github.com/TheSilentPro/IPAPI/releases)

### Usage
```java
JsonObject response = IPAPI.check(String ip);

JsonObject response = IPAPI.check(String ip, int timeout);

JsonObject response = IPAPI.check(String ip, String fields, String lang, int timeout);
```

**fields** - The fields you want the API to return *Default: 33292287 (all)* <br>
**lang** - The language-code you want the API to respond in (Example: en, de) *Default: en* <br>
**timeout** - Time until the connection gives up *Default: 5000* <br>

Any one of **fields** and **lang** can be set as **null** and the defaults will be used

### [LICENSE](https://github.com/TheSilentPro/IPAPI/blob/master/LICENSE)
This API is licensed under GNU General Public License v3.0
