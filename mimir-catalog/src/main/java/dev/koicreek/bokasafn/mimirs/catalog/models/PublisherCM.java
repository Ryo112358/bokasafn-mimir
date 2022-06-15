package dev.koicreek.bokasafn.mimirs.catalog.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import dev.koicreek.bokasafn.mimirs.catalog.util.Stringify;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Publishers")
@Table(name = "PUBLISHERS")
@SequenceGenerator(
        name = "publisher-id-generator",
        sequenceName = "publisher_id_sequence"
)
@Cacheable
@Cache(usage= CacheConcurrencyStrategy.READ_ONLY)
public class PublisherCM {

    @Id
    @GeneratedValue(generator = "publisher-id-generator")
    @JsonProperty("publisherId")
    @Column(name = "publisher_id")
    private long id;

    @Column(name = "publisher_name", nullable = false)
    @CsvBindByName
    private String name;

    @OneToMany(mappedBy = "publisher")
    private List<BookCM> books = new ArrayList<>();

    //#region Constructors -------------------------------------

    public PublisherCM() {
    }

    public PublisherCM(long publisherId) {
        this.id = publisherId;
    }

    //#endRegion

    //#region GettersSetters -------------------------------------------------------

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookCM> getBooks() {
        return books;
    }

    public void setBooks(List<BookCM> books) {
        this.books = books;
    }

    //#endRegion

    //#region Helpers

    //#endRegion

    //#region Stringify

    public String toString() {
        return this.toString(false);
    }

    public String toString(boolean includeBooks) {
        StringBuilder sb = new StringBuilder("PublisherCM {");

        sb.append(String.format("\n\tid: %d", this.id));
        sb.append(String.format(",\n\tname: %s", Stringify.wrap(this.name)));
        if(includeBooks)
            sb.append(String.format(",\n\tbooks: %s", Stringify.indent(Stringify.toString(books))));
        sb.append("\n}");

        return sb.toString();
    }

    public String toStringSimplified() {
        return String.format("\"%s (%d)\"", this.name, this.id);
    }

    public static String toString(List<PublisherCM> publisherList) {
        if(publisherList.size() == 0) return "[ <empty> ]";

        StringBuilder sb = new StringBuilder("[\n\t");
        sb.append(Stringify.indent(publisherList.get(0).toStringSimplified()));

        for(int i=1; i < publisherList.size(); ++i) {
            sb.append(",\n\t").append(Stringify.indent(publisherList.get(i).toStringSimplified()));
        }
        sb.append("\n]");

        return sb.toString();
    }

    //#endRegion
}
