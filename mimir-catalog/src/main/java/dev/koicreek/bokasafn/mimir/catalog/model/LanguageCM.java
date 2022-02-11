package dev.koicreek.bokasafn.mimir.catalog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import dev.koicreek.bokasafn.mimir.catalog.constants.LanguageCode;
import dev.koicreek.bokasafn.mimir.catalog.model.converter.csv.ISOCode639ToLanguageCodeEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.util.List;

import static dev.koicreek.bokasafn.mimir.catalog.util.Stringify.toIndentedString;
import static dev.koicreek.bokasafn.mimir.catalog.util.Stringify.wrapInQuotations;


@Entity(name = "Languages")
@Table(name = "LANGUAGES")
@Cacheable
@Cache(usage= CacheConcurrencyStrategy.READ_ONLY)
public class LanguageCM {

    @Id
    @Column(name="isocode_639_3")
    @JsonProperty("isoCode639_3")
    @CsvCustomBindByName(column = "Isocode639_3", converter = ISOCode639ToLanguageCodeEnum.class)
    private LanguageCode languageCode;  // ISO Code 639-3

    @Column(name="language_name", nullable = false)
    @JsonProperty("languageName")
    @CsvBindByName(column = "Language")
    private String name;

    @Column(name = "language_name_native")
    @JsonProperty("languageNameNative")
    @CsvBindByName(column = "NativeName")
    private String nameNative;

    //#region Constructors

    public LanguageCM() {}

    public LanguageCM(String isoCode639_3) {
        this.languageCode = LanguageCode.from(isoCode639_3);
    }

    public LanguageCM(LanguageCode languageCode) {
        this.languageCode = languageCode;
    }

    //#endRegion

    //#region GettersSetters

    public LanguageCode getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(LanguageCode languageCode) {
        this.languageCode = languageCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameNative() {
        return nameNative;
    }

    public void setNameNative(String nameNative) {
        if(nameNative.equals("")) nameNative = null;
        this.nameNative = nameNative;
    }

    //#endRegion

    //#region Stringify

    public String toString() {
        StringBuilder sb = new StringBuilder("LanguageCM {\n");

        sb.append(String.format("\tisoCode639_3: %s,\n", wrapInQuotations(this.languageCode.getIsoCode639_3())));
        sb.append(String.format("\tname: %s,\n", wrapInQuotations(this.name)));
        if(nameNative != null)
            sb.append(String.format("\tnativeName: %s,\n", wrapInQuotations(this.nameNative)));
        sb.append("}");

        return sb.toString();
    }

    public String toStringSimplified() {
        return String.format("\"%s (%s)\"", this.name, this.languageCode.getIsoCode639_3());
    }

    public static String toString(List<LanguageCM> languageList) {
        if(languageList.size() == 0) return "[ <empty> ]";

        StringBuilder sb = new StringBuilder("[\n\t");
        sb.append(toIndentedString(languageList.get(0).toStringSimplified()));

        for(int i=1; i < languageList.size(); ++i) {
            sb.append(",\n\t").append(toIndentedString(languageList.get(i).toStringSimplified()));
        }
        sb.append("\n]");

        return sb.toString();
    }

    //#endRegion
}
