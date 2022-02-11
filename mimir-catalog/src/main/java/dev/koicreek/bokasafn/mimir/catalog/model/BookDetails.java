package dev.koicreek.bokasafn.mimir.catalog.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static dev.koicreek.bokasafn.mimir.catalog.util.Stringify.wrapInQuotations;


@Data
@Embeddable
public class BookDetails {

    @Column(name="page_count")
    @CsvBindByName
    private int pageCount;

    @Column(name="year_published")
    @CsvBindByName
    private int yearPublished;

    @Column(name="publisher")
    @CsvBindByName
    private String publisher;

    //#region Constructors -----------------------------

    public BookDetails() {
    }

    public BookDetails(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public BookDetails(int yearPublished, int pageCount, String publisher) {
        this.pageCount = pageCount;
        this.yearPublished = yearPublished;
        this.publisher = publisher;
    }

    //#endRegion

    public String toString() {
        StringBuilder sb = new StringBuilder("{");

        sb.append(String.format("\n\tpageCount: %d", this.pageCount));
        sb.append(String.format(",\n\tyearPublished: %s", this.yearPublished));
        sb.append(String.format(",\n\tpublisher: %s", wrapInQuotations(this.publisher)));
        sb.append("\n}");

        return sb.toString();
    }
}
