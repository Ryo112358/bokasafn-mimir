package dev.koicreek.bokasafn.mimir.catalog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static dev.koicreek.bokasafn.mimir.catalog.util.Stringify.toIndentedString;
import static dev.koicreek.bokasafn.mimir.catalog.util.Stringify.wrapInQuotations;


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
    @Column(name = "author_id")
    private long id;

    @Column(name = "pen_name", nullable = false)
    private String penName;

    @Column(name = "author_last_name", nullable = false)
    private String lastName;

    @Column(name = "author_first_name")
    private String firstName;

    @Column(name = "author_middle_name")
    private String middleName;

    @ManyToMany(mappedBy="authors")
    private List<BookCM> books = new ArrayList<>();

    //#region Constructors -------------------------------------

    public AuthorCM() {}

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
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
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
        StringBuilder sb = new StringBuilder("\nAuthorCM {\n");

        sb.append(String.format("\tid: %d, \n", this.id));
        sb.append(String.format("\tpenName: %s,\n", wrapInQuotations(this.penName)));
        sb.append(String.format("\tlastName: %s,\n", wrapInQuotations(this.lastName)));
        if(firstName != null)
        sb.append(String.format("\tfirstName: %s,\n", wrapInQuotations(this.firstName)));
        if(middleName != null)
            sb.append(String.format("\tmiddleName: %s,\n", wrapInQuotations(this.middleName)));
        sb.append("}");

        return sb.toString();
    }
}
