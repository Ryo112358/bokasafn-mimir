package dev.koicreek.bokasafn.mimir.catalog.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static dev.koicreek.bokasafn.mimir.catalog.util.Stringify.indent;
import static dev.koicreek.bokasafn.mimir.catalog.util.Stringify.wrap;

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

    //#endRegion
}
