package dev.koicreek.bokasafn.mimir.catalog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
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
    @CsvBindByName(column = "Isocode639_3")
    private String isoCode639_3;

    @CsvBindByName(column = "Language")
    @JsonProperty("languageName")
    @Column(name="language_name", nullable = false)
    private String name;

    @CsvBindByName(column = "NativeName")
    @JsonProperty("languageNameNative")
    @Column(name = "language_name_native")
    private String nameNative;

    //#region Constructors

    public LanguageCM() {}

    public LanguageCM(String isoCode639_3) {
        if(isoCode639_3.length() != 3) throw new IllegalArgumentException("Invalid language code: " + isoCode639_3);
        this.isoCode639_3 = isoCode639_3;
    }

    public LanguageCM(String isoCode639_3, String name) {
        this.isoCode639_3 = isoCode639_3;
        this.name = name;
    }

    public LanguageCM(String isoCode639_3, String name, String nameNative) {
        this.isoCode639_3 = isoCode639_3;
        this.name = name;
        this.nameNative = nameNative;
    }

    //#endRegion

    //#region GettersSetters

    public String getIsoCode639_3() {
        return isoCode639_3;
    }

    public void setIsoCode639_3(String isoCode639_3) {
        if(isoCode639_3.length() != 3) throw new IllegalArgumentException("ISO 639-3 codes must be 3 chars in length.");
        this.isoCode639_3 = isoCode639_3;
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

        sb.append(String.format("\tisoCode639_3: %s,\n", wrapInQuotations(this.isoCode639_3)));
        sb.append(String.format("\tname: %s,\n", wrapInQuotations(this.name)));
        if(nameNative != null)
            sb.append(String.format("\tnativeName: %s,\n", wrapInQuotations(this.nameNative)));
        sb.append("}");

        return sb.toString();
    }

    public String toStringSimplified() {
        return String.format("\"%s (%s)\"", this.name, this.isoCode639_3);
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
