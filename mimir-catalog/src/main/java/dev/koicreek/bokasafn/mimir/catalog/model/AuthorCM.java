package dev.koicreek.bokasafn.mimir.catalog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static dev.koicreek.bokasafn.mimir.catalog.util.Stringify.*;


@Entity(name = "Author")
@Table(name = "AUTHORS", indexes = @Index(columnList = "author_last_name"))
@SequenceGenerator(
        name = "author-id-generator",
        sequenceName = "author_id_sequence"
)
@Cacheable
@Cache(usage= CacheConcurrencyStrategy.READ_ONLY)
public class AuthorCM {

    @Id
    @GeneratedValue(generator = "author-id-generator")
    @JsonProperty("authorId")
    @CsvBindByName(column = "AuthorId")
    @Column(name = "author_id")
    private long id;

    @CsvBindByName(column = "penName")
    @Column(name = "pen_name", nullable = false)
    private String penName;

    @CsvBindByName(column = "LastName")
    @Column(name = "author_last_name", nullable = false)
    private String lastName;

    @CsvBindByName(column = "FirstName")
    @Column(name = "author_first_name")
    private String firstName;

    @CsvBindByName(column = "MiddleName")
    @Column(name = "author_middle_name")
    private String middleName;

    @ManyToMany(mappedBy="authors")
    private List<BookCM> books = new ArrayList<>();

    //#region Constructors -------------------------------------

    public AuthorCM() {}

    public AuthorCM(long authorId) {
        if(authorId < 1) throw new IllegalArgumentException("Invalid author id: " + authorId);
        this.id = authorId;
    }

    public AuthorCM(String penName, String lastName) {
        this.penName = penName;
        this.lastName = lastName;
    }

    public AuthorCM(String penName, String lastName, String firstName) {
        this.penName = penName;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public AuthorCM(String penName, String lastName, String firstName, String middleName) {
        this.penName = penName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    //#endRegion

    //#region GettersSetters -------------------------------------------------------

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if(id < 1) throw new IllegalArgumentException("Invalid author id: " + id);
        this.id = id;
    }

    public String getPenName() {
        return penName;
    }

    public void setPenName(String penName) {
        this.penName = penName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(lastName.equals("")) lastName = null;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if(firstName.equals("")) firstName = null;
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        if(middleName.equals("")) middleName = null;
        this.middleName = middleName;
    }

    public List<BookCM> getBooks() {
        return books;
    }

    public void setBooks(List<BookCM> books) {
        this.books = books;
    }

    //#endRegion
    public String toString() {
        return this.toString(false);
    }

    public String toString(boolean includeBooks) {
        StringBuilder sb = new StringBuilder("AuthorCM {\n");

        sb.append(String.format("\tid: %d, \n", this.id));
        sb.append(String.format("\tpenName: %s,\n", wrapInQuotations(this.penName)));
        sb.append(String.format("\tlastName: %s,\n", wrapInQuotations(this.lastName)));
        if(firstName != null)
        sb.append(String.format("\tfirstName: %s,\n", wrapInQuotations(this.firstName)));
        if(middleName != null)
            sb.append(String.format("\tmiddleName: %s,\n", wrapInQuotations(this.middleName)));
        if(includeBooks)
            sb.append(String.format("\tbooks: %s\n", toIndentedString(listToString(books))));
        sb.append("}");

        return sb.toString();
    }
}
