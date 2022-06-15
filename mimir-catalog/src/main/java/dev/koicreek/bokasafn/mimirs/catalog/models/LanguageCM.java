package dev.koicreek.bokasafn.mimirs.catalog.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.util.List;

import static dev.koicreek.bokasafn.mimirs.catalog.util.Stringify.indent;
import static dev.koicreek.bokasafn.mimirs.catalog.util.Stringify.wrap;


@Entity(name = "Languages")
@Table(name = "LANGUAGES")
@Cacheable
@Cache(usage= CacheConcurrencyStrategy.READ_ONLY)
public class LanguageCM {

    @Id
    @Column(name="isocode_639_3")
    @JsonProperty("isoCode639_3")
    @CsvBindByName(column = "Isocode639_3")
    private String isoCode639_3;

    @Column(name="language_name", nullable = false)
    @JsonProperty("languageName")
    @CsvBindByName(column = "Language")
    private String name;

    @Column(name = "language_name_native")
    @JsonProperty("languageNameNative")
    @CsvBindByName(column = "NativeName")
    private String nameNative;

    //#region Constructors

    public LanguageCM() {
    }

    public LanguageCM(String isoCode639_3) {
        this.isoCode639_3 = isoCode639_3;
    }

    //#endRegion

    //#region GettersSetters


    public String getIsoCode639_3() {
        return isoCode639_3;
    }

    public void setIsoCode639_3(String isoCode639_3) {
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
        StringBuilder sb = new StringBuilder("LanguageCM {");

        sb.append(String.format("\n\tisoCode639_3: %s", wrap(this.isoCode639_3)));
        sb.append(String.format(",\n\tname: %s", wrap(this.name)));
        if(nameNative != null)
            sb.append(String.format(",\n\tnativeName: %s", wrap(this.nameNative)));
        sb.append("\n}");

        return sb.toString();
    }

    public String toStringSimplified() {
        return String.format("\"%s (%s)\"", this.name, this.isoCode639_3);
    }

    public static String toString(List<LanguageCM> languageList) {
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
