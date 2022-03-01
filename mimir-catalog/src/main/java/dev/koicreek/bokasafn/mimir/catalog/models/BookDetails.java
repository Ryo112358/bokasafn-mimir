package dev.koicreek.bokasafn.mimir.catalog.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import dev.koicreek.bokasafn.mimir.catalog.constants.BookFormat;
import dev.koicreek.bokasafn.mimir.catalog.models.converters.csv.abf.ToBookFormatEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static dev.koicreek.bokasafn.mimir.catalog.util.Stringify.wrap;


@Embeddable
@Data
@AllArgsConstructor
public class BookDetails {

    @Column(name="year_published")
    @CsvBindByName
    private int yearPublished;

    @CsvCustomBindByName(converter = ToBookFormatEnum.class)
    private BookFormat format;

    @Column(name="page_count")
    @CsvBindByName
    private int pageCount;

    @CsvBindByName
    private String edition;

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

    public String toString() {
        StringBuilder sb = new StringBuilder("{");

        sb.append(String.format("\n\tformat: %s", wrap(this.format.getFormat())));
        sb.append(String.format(",\n\tpageCount: %d", this.pageCount));
        sb.append(String.format(",\n\tyearPublished: %s", this.yearPublished));
        if(edition != null)
            sb.append(String.format(",\n\tedition: %s", wrap(this.edition)));
        sb.append("\n}");

        return sb.toString();
    }

    //#endRegion
}
