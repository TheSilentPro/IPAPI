package tsp.ipapi.implementation;

public enum IPResponseLang {

    ENGLISH("en"),
    GERMAN("de"),
    SPANISH("es"),
    PORTUGUESE("pt-BR"),
    FRENCH("fr"),
    JAPANESE("ja"),
    CHINESE("zh_CN"),
    RUSSIAN("ru");

    private final String code;

    IPResponseLang(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
