package dev.koicreek.bokasafn.mimir.catalog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static dev.koicreek.bokasafn.mimir.catalog.util.Stringify.toIndentedString;
import static dev.koicreek.bokasafn.mimir.catalog.util.Stringify.wrapInQuotations;

@Entity(name = "Book")
@Table(name = "BOOKS")
@Cacheable
@Cache(usage= CacheConcurrencyStrategy.READ_ONLY)
public class BookCM {

    @Id
    private long ISBN13;

    @JsonProperty("bookTitle")
    @Column(name="book_title")
    private String title;

    @JsonProperty("bookSubtitle")
    @Column(name="book_subtitle")
    private String subtitle;

    @Embedded
    private BookDetails details;

    @ManyToMany(cascade=CascadeType.PERSIST)
    @JoinTable(name = "BOOKS_AUTHORS",
        joinColumns = @JoinColumn(name = "isbn13"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<AuthorCM> authors = new ArrayList<>();

    @ManyToMany(cascade=CascadeType.PERSIST)
    @JoinTable(name = "BOOKS_LANGUAGES",
        joinColumns = @JoinColumn(name = "isbn13"),
        inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private List<LanguageCM> languages =  new ArrayList<>();

    //#region Constructors -----------------------------------------------

    public BookCM() {
    }

    public BookCM(long ISBN13, String title) {
        this.ISBN13 = ISBN13;
        this.title = title;
    }

    public BookCM(long ISBN13, String title, String subtitle) {
        this.ISBN13 = ISBN13;
        this.title = title;
        this.subtitle = subtitle;
    }

    public BookCM(long ISBN13, String title, BookDetails details) {
        this.ISBN13 = ISBN13;
        this.title = title;
        this.details = details;
    }

    public BookCM(long ISBN13, String title, String subtitle, BookDetails details) {
        this.ISBN13 = ISBN13;
        this.title = title;
        this.subtitle = subtitle;
        this.details = details;
    }

    //#endRegion

    //#region GettersSetters --------------------------------------------

    public long getISBN13() {
        return ISBN13;
    }

    public void setISBN13(long ISBN13) {
        this.ISBN13 = ISBN13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<AuthorCM> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorCM> authors) {
        this.authors = authors;
    }

    public List<LanguageCM> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LanguageCM> languages) {
        this.languages = languages;
    }

    public BookDetails getDetails() {
        return details;
    }

    public void setDetails(BookDetails details) {
        this.details = details;
    }

    //#endRegion

    //#region Helpers

    public void addAuthor(AuthorCM author) {
        this.authors.add(author);
    }

    public void addLanguage(LanguageCM language) {
        this.languages.add(language);
    }

    //#endRegion

    public String toString() {
        StringBuilder sb = new StringBuilder("\nBookCM {\n");

        sb.append(String.format("\tisbn13: %d,\n", this.ISBN13));
        sb.append(String.format("\ttitle: %s,\n", wrapInQuotations(this.title)));
        if(this.subtitle != null)
            sb.append(String.format("\tsubtitle: %s,\n", wrapInQuotations(this.subtitle)));
        sb.append(String.format("\tdetails: %s\n", toIndentedString(this.details)));
        sb.append("}");

        return sb.toString();
    }

    /* JPA Notes:
     *   @Basic(fetch = FetchType.LAZY) - data retrieved when needed, as opposed to default [EAGER] retrieval
     *   @Transient - used to ignore fields, so they aren't included in db
     *   @Temporal(TemporalType.DATE) - used to specify time info stored
     *      - TemporalTypes: DATE, TIME, [TIMESTAMP]
     *   @Lob - save as large object, e.g. when String may exceed 255 chars
     *   @Embedded - optional annotation to specify an embeddable object
     *      - @AttributeOverrides - Container for configuring multiple attributes for an embedded object
     *          - @AttributeOverride - Configure an attribute for an embedded object
     *              - name="fieldName"
     *              - column=@Column(name="new_col_name")
     *    @EmbeddedId - for an object to be used as a primary key
     *    @ElementCollection - creates a new table for collection with reference to current
     *                        object id as foreign key (i.e. reverse ManyToOne mapping)
     *      - @JoinTable(name="JOIN_TABLE_NAME", joinColumns=@JoinColumn(name="desired_col_name"))
     *    @OneToMany
     *      - cascade=CascadeType.PERSIST - used to save all nested objects (deps) before persisting main object
     *
     *    @Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
     *      - default strategy is SINGLE_TABLE
     *          @DiscriminatorColumn
     *          @DiscriminatorValue("") - annotation to change value of subclass
     *      - strategy=InheritanceType.JOINED
     *          - Most normalized
     */

    /* Hibernate Notes:
     *    @GenericGenerator(name="gen-name", strategy="hilo")
     *    @CollectionId(columns = { @Column(name="id_col_name") }, generator="gen-name", type=@Type(type="long"))
     *    @NotFound(action=NotFoundAction.IGNORE)
     *    @SelectBeforeUpdate - check for changes before running "update" query
     */
}
