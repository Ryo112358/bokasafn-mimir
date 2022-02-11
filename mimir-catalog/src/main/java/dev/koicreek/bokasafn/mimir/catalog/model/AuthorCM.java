package dev.koicreek.bokasafn.mimir.catalog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static dev.koicreek.bokasafn.mimir.catalog.util.Stringify.*;


@Entity(name = "Authors")
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
    protected long id;

    @Column(name = "pen_name", nullable = false)
    protected String penName;

    @Column(name = "author_last_name", nullable = false)
    protected String lastName;

    @Column(name = "author_first_name")
    protected String firstName;

    @Column(name = "author_middle_name")
    protected String middleName;

    @Column(name = "is_illustrator")
    protected boolean isIllustrator;

    @ManyToMany(mappedBy="authors")
    protected List<BookCM> books = new ArrayList<>();

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

    public boolean isIllustrator() {
        return this.isIllustrator;
    }

    public void setIllustrator(boolean illustrator) {
        this.isIllustrator = illustrator;
    }

    //#endRegion

    //#region Stringify

    public String toString() {
        return this.toString(false);
    }

    public String toString(boolean includeBooks) {
        StringBuilder sb = new StringBuilder("AuthorCM {");

        sb.append(String.format("\n\tid: %d", this.id));
        sb.append(String.format(",\n\tpenName: %s", wrapInQuotations(this.penName)));
        if(isIllustrator)
            sb.append(String.format(",\n\tisIllustrator: %b", true));
        sb.append(String.format(",\n\tlastName: %s", wrapInQuotations(this.lastName)));
        if(firstName != null)
        sb.append(String.format(",\n\tfirstName: %s", wrapInQuotations(this.firstName)));
        if(middleName != null)
            sb.append(String.format(",\n\tmiddleName: %s", wrapInQuotations(this.middleName)));
        if(includeBooks)
            sb.append(String.format(",\n\tbooks: %s", toIndentedString(listToString(books))));
        sb.append("\n}");

        return sb.toString();
    }

    public String toStringSimplified() {
        return String.format("\"%s (%d)%s\"", this.penName, this.id, this.isIllustrator ? " -> Illustrator" : "");
    }

    public static String toString(List<AuthorCM> authorList) {
        if(authorList.size() == 0) return "[ <empty> ]";

        StringBuilder sb = new StringBuilder("[\n\t");
        sb.append(toIndentedString(authorList.get(0).toStringSimplified()));

        for(int i=1; i < authorList.size(); ++i) {
            sb.append(",\n\t").append(toIndentedString(authorList.get(i).toStringSimplified()));
        }
        sb.append("\n]");

        return sb.toString();
    }

    //#endRegion
}
