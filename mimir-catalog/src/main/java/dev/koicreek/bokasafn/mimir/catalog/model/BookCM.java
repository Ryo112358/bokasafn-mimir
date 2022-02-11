package dev.koicreek.bokasafn.mimir.catalog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvRecurse;
import dev.koicreek.bokasafn.mimir.catalog.model.converter.csv.TextIdToAuthorCM;
import dev.koicreek.bokasafn.mimir.catalog.model.converter.csv.ISOCode639ToLanguageCM;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static dev.koicreek.bokasafn.mimir.catalog.util.Stringify.*;

@Entity(name = "Books")
@Table(name = "BOOKS")
@Cacheable
@Cache(usage= CacheConcurrencyStrategy.READ_ONLY)
public class BookCM {

    @Id
    @CsvBindByName(column = "ISBN13")
    private long isbn13;

    @Column(name="book_title")
    @JsonProperty("bookTitle")
    @CsvBindByName(column = "Title")
    private String title;

    @Column(name="book_subtitle")
    @JsonProperty("bookSubtitle")
    @CsvBindByName(column = "Subtitle")
    private String subtitle;

    @Embedded
    @CsvRecurse
    private BookDetails details;

    @ManyToMany(cascade=CascadeType.PERSIST)
    @JoinTable(name = "BOOKS_AUTHORS",
        joinColumns = @JoinColumn(name = "isbn13"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @CsvBindAndSplitByName(column = "Authors", splitOn = "\\|+", elementType = AuthorCM.class, converter = TextIdToAuthorCM.class)
    private List<AuthorCM> authors = new ArrayList<>();

    @ManyToMany(cascade=CascadeType.PERSIST)
    @JoinTable(name = "BOOKS_LANGUAGES",
        joinColumns = @JoinColumn(name = "isbn13"),
        inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    @CsvBindAndSplitByName(column = "Languages", splitOn = "\\|+", elementType = LanguageCM.class, converter = ISOCode639ToLanguageCM.class)
    private List<LanguageCM> languages =  new ArrayList<>();

    //#region Constructors -----------------------------------------------

    public BookCM() {
    }

    public BookCM(long ISBN13, String title) {
        this.isbn13 = ISBN13;
        this.title = title;
    }

    public BookCM(long ISBN13, String title, String subtitle) {
        this.isbn13 = ISBN13;
        this.title = title;
        this.subtitle = subtitle;
    }

    public BookCM(long ISBN13, String title, AuthorCM author, LanguageCM language) {
        this.isbn13 = ISBN13;
        this.title = title;
        this.authors.add(author);
        this.languages.add(language);
    }

    public BookCM(long ISBN13, String title, String languageCode, long authorId) {
        this.isbn13 = ISBN13;
        this.title = title;
        this.authors.add(new AuthorCM(authorId));
        this.languages.add(new LanguageCM(languageCode));
    }

    public BookCM(long ISBN13, String title, String subtitle, LanguageCM language, AuthorCM author) {
        this.isbn13 = ISBN13;
        this.title = title;
        this.subtitle = subtitle;
        this.authors.add(author);
        this.languages.add(language);
    }

    public BookCM(long ISBN13, String title, String subtitle, String languageCode, long authorId) {
        this.isbn13 = ISBN13;
        this.title = title;
        this.subtitle = subtitle;
        this.authors.add(new AuthorCM(authorId));
        this.languages.add(new LanguageCM(languageCode));
    }

    //#endRegion

    //#region GettersSetters --------------------------------------------

    public long getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(long isbn13) {
        this.isbn13 = isbn13;
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
        if(subtitle.equals("")) subtitle = null;
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

    //#region Stringify

    public String toString() {
        return this.toString(true, true);
    }

    public String toString(boolean[] printNested) {
        return printNested.length == 2 ? this.toString(printNested[0], printNested[1]) : this.toString(false, false);
    }

    public String toString(boolean includeAuthors, boolean includeLanguages) {
        StringBuilder sb = new StringBuilder("BookCM {");

        sb.append(String.format("\n\tisbn13: %d", this.isbn13));
        sb.append(String.format(",\n\ttitle: %s", wrapInQuotations(this.title)));
        if(this.subtitle != null)
            sb.append(String.format(",\n\tsubtitle: %s", wrapInQuotations(this.subtitle)));
        if(includeAuthors)
            sb.append(String.format(",\n\tauthors: %s", toIndentedString(listToString(this.authors))));
        if(includeLanguages)
            sb.append(String.format(",\n\tlanguages: %s", toIndentedString(listToString(this.languages))));
        sb.append(String.format(",\n\tdetails: %s", toIndentedString(this.details)));
        sb.append("\n}");

        return sb.toString();
    }

    public String toStringSimplified(boolean includeAuthors, boolean includeLanguages) {
        StringBuilder sb = new StringBuilder("BookCM {");

        sb.append(String.format("\n\tisbn13: %d", this.isbn13));
        sb.append(String.format(",\n\ttitle: %s", wrapInQuotations(this.title)));
        if(this.subtitle != null)
            sb.append(String.format(",\n\tsubtitle: %s", wrapInQuotations(this.subtitle)));
        if(includeAuthors)
            sb.append(String.format(",\n\tauthors: %s", toIndentedString(AuthorCM.toString(this.authors))));
        if(includeLanguages)
            sb.append(String.format(",\n\tlanguages: %s", toIndentedString(LanguageCM.toString(this.languages))));
        sb.append(String.format(",\n\tdetails: %s", toIndentedString(this.details)));
        sb.append("\n}");

        return sb.toString();
    }

    public static String toString(List<BookCM> bookList, boolean includeAuthors, boolean includeLanguages) {
        if(bookList.size() == 0) return "[ <empty> ]";

        StringBuilder sb = new StringBuilder("[\n\t");
        sb.append(toIndentedString(bookList.get(0).toStringSimplified(includeAuthors, includeLanguages)));

        for(int i=1; i < bookList.size(); ++i) {
            sb.append(",\n\t").append(toIndentedString(bookList.get(i).toStringSimplified(includeAuthors, includeLanguages)));
        }
        sb.append("\n]");

        return sb.toString();
    }

    //#endRegion

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
