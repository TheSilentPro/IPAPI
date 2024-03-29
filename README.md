### IPAPI

Allows you to make requests to ip-api.com easily <br>

****

### Dependencies
[GSON](hhttps://mvnrepository.com/artifact/com.google.code.gson/gson) <br>
[Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

****

### Installation
#### Maven
```
	<dependency>
	    <groupId>com.github.TheSilentPro</groupId>
	    <artifactId>IPAPI</artifactId>
	    <version>4.0.0</version>
	</dependency>
```
**Make sure you use the latest version!**

You can download it from the [Releases Page](https://github.com/TheSilentPro/IPAPI/releases) or use [JitPack](https://jitpack.io/#TheSilentPro/IPAPI)

****

### Example Usage
```
        new IPRequester()
                .add(new IPEntry.Builder("0.0.0.0")
                        .language(IPResponseLang.ENGLISH)
                        .all()
                        .build())
                .add(new IPEntry.Builder("1.1.1.1").all().build()).fetch().whenComplete((ipResponse, throwable) -> {
                    if (throwable != null) {
                        throwable.printStackTrace();
                    } else {
                        ipResponse.responses().forEach((ip, fieldMap) -> {
                            System.out.println("INFORMATION FOR IP: " + ip);
                            fieldMap.forEach((field, value) -> System.out.println(field.getName() + " = " + value));
                            System.out.println("------------------------------");
                        });
                        System.out.println("Left: " + ipResponse.left());
                        System.out.println("Reset (seconds): " + ipResponse.reset());
                    }
        });
```

****

### [LICENSE](https://github.com/TheSilentPro/IPAPI/blob/master/LICENSE)
[This API is licensed under GNU General Public License v3.0](https://github.com/TheSilentPro/IPAPI/blob/master/LICENSE)