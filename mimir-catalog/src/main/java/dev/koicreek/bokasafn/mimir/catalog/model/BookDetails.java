package dev.koicreek.bokasafn.mimir.catalog.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static dev.koicreek.bokasafn.mimir.catalog.util.Stringify.wrapInQuotations;

@Data
@Embeddable
public class BookDetails {

    @CsvBindByName(column = "PageCount")
    @Column(name="page_count")
    private int pageCount;

    @CsvBindByName(column = "YearPublished")
    @Column(name="year_published")
    private int yearPublished;

    @CsvBindByName(column = "Publisher")
    @CsvBindByName(column = "PageCount")
    @Column(name="publisher")
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
        StringBuilder sb = new StringBuilder("{\n");

        sb.append(String.format("\tpageCount: %d,\n", this.pageCount));
        sb.append(String.format("\tyearPublished: %s,\n", this.yearPublished));
        sb.append(String.format("\tpublisher: %s\n", wrapInQuotations(this.publisher)));
        sb.append("}");

        return sb.toString();
    }
}
