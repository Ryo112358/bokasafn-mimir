package dev.koicreek.bokasafn.mimirs.catalog.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;
import dev.koicreek.bokasafn.mimirs.catalog.constants.BookFormat;
import dev.koicreek.bokasafn.mimirs.catalog.models.converters.csv.abf.ToBookFormatEnum;
import dev.koicreek.bokasafn.mimirs.catalog.util.Stringify;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Embeddable
@Data
@AllArgsConstructor
public class BookDetails {

    @CsvBindByName
    private String edition;

    @CsvCustomBindByName(converter = ToBookFormatEnum.class)
    private BookFormat format;

    @Column(name="page_count")
    @CsvBindByName
    private int pageCount;

    @Column(name="date_published")
    @CsvDate("MMM d, yyyy")
    @CsvBindByName
    private LocalDate datePublished;

    //#region Constructors -----------------------------

    public BookDetails() {
    }

    //#endRegion

    //#region GettersSetters -----------------------------

    public void setEdition(String edition) {
        this.edition = edition.equals("") ? null : edition;
    }

    //#endRegion

    //#region Stringify

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM d, yyyy");

    public String toString() {
        StringBuilder sb = new StringBuilder("{");

        sb.append(String.format("\n\tformat: %s", Stringify.wrap(this.format.getFormat())));
        sb.append(String.format(",\n\tpageCount: %d", this.pageCount));
        if(edition != null)
            sb.append(String.format(",\n\tedition: %s", Stringify.wrap(this.edition)));
        sb.append(String.format(",\n\tdatePublished: %s", Stringify.wrap(this.datePublished.format(dtf))));

        sb.append("\n}");

        return sb.toString();
    }

    //#endRegion
}
