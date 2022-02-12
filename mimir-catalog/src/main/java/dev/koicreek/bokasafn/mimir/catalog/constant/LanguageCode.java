package dev.koicreek.bokasafn.mimir.catalog.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum LanguageCode {
    ENGLISH("eng"),
    ESTONIAN("est"),
    ICELANDIC("isl"),
    NORWEGIAN_BOKMAL("nob"),
    NORWEGIAN_MACRO("nor"),
    SWEDISH("swe"),
    FINNISH("fin"),
    DANISH("dan"),
    GERMAN("deu"),
    RUSSIAN("rus"),
    HINDI("hin"),
    JAPANESE("jpn");

    @Getter
    private final String isoCode639_3;

    LanguageCode(String isoCode639_3) {
        this.isoCode639_3 = isoCode639_3;
    }

    //#region GetEnumByFields

    private static final Map<String, LanguageCode> languageCodeMap = new HashMap<>(values().length);

    static {
        for(LanguageCode code : values()) {
            languageCodeMap.put(code.getIsoCode639_3(), code);
        }
    }

    public static LanguageCode from(String isoCode639_3) {
        return languageCodeMap.get(isoCode639_3);
    }

    //#endRegion
}
