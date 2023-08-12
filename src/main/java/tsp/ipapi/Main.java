package tsp.ipapi;

import tsp.ipapi.implementation.IPEntry;
import tsp.ipapi.implementation.IPRequester;
import tsp.ipapi.implementation.IPResponseLang;

public class Main {

    public static void main(String[] args) {
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
    }

}
