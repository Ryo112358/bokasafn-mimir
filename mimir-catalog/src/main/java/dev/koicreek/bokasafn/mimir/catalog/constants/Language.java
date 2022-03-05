package dev.koicreek.bokasafn.mimir.catalog.constants;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.koicreek.bokasafn.mimir.catalog.util.Stringify.indent;

public enum Language {
    ENGLISH("eng", "English", "english"),
    ESTONIAN("est", "Estonian", "eesti"),
    ICELANDIC("isl", "Icelandic", "íslenska"),
    NORWEGIAN_BOKMAL("nob", "Bokmål", "bokmål"),
    MACRO_NORWEGIAN("nor", "Norwegian", "norsk"),
    SWEDISH("swe", "Swedish", "svenska"),
    FINNISH("fin", "Finnish", "suomen"),
    DANISH("dan", "Danish", "dansk"),
    GERMAN("deu", "German", "deutsch"),
    RUSSIAN("rus", "Russian", "pусский"),
    HINDI("hin", "Hindi", "हिंदी"),
    JAPANESE("jpn", "Japanese", "日本語");

    @Getter
    private final String isoCode639_3;

    @Getter
    private final String name;

    @Getter
    private final String nativeName;

    Language(String isoCode639_3, String languageName, String nativeLanguageName) {
        this.isoCode639_3 = isoCode639_3;
        this.name = languageName;
        this.nativeName = nativeLanguageName;
    }

    public String toStringSimplified() {
        return String.format("\"%s (%s)\"", this.name, this.isoCode639_3);
    }

    //#region GetLanguageByIsoCode

    private static final Map<String, Language> LANGUAGE_CODE_MAP = new HashMap<>(values().length);

    static {
        for(Language code : values()) {
            LANGUAGE_CODE_MAP.put(code.getIsoCode639_3(), code);
        }
    }

    public static Language from(String isoCode639_3) {
        return LANGUAGE_CODE_MAP.get(isoCode639_3);
    }

    //#endRegion

    //#region Stringify

    public static String toStringSimplified(List<Language> languageList) {
        if(languageList.size() == 0) return "[ <empty> ]";

        StringBuilder sb = new StringBuilder("[\n\t");
        sb.append(indent(languageList.get(0).toStringSimplified()));

        for(int i=1; i < languageList.size(); ++i) {
            sb.append(",\n\t").append(indent(languageList.get(i).toStringSimplified()));
        }
        sb.append("\n]");

        return sb.toString();
    }

    //#endRegion
}
