package dev.koicreek.bokasafn.mimir.catalog.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvRecurse;
import dev.koicreek.bokasafn.mimir.catalog.constants.Language;
import dev.koicreek.bokasafn.mimir.catalog.models.converters.csv.acc.ToAuthorCM;
import dev.koicreek.bokasafn.mimir.catalog.models.converters.csv.abf.ToLanguageEnum;
import dev.koicreek.bokasafn.mimir.catalog.models.converters.csv.abf.ToPublisherCM;
import dev.koicreek.bokasafn.mimir.catalog.models.converters.enums.LanguageCodeConverter;
import dev.koicreek.bokasafn.mimir.catalog.util.Stringify;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.*;

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

    @Column(name="primary_language")
    @Convert(converter = LanguageCodeConverter.class)
    @CsvCustomBindByName(column = "PrimaryLanguage", converter = ToLanguageEnum.class)
    private Language primaryLanguage;

    @Column(name="is_multilingual")
    @CsvBindByName(column = "IsMultilingual")
    private boolean isMultilingual;

    @ElementCollection
    @CollectionTable(name = "BOOKS_LANGUAGES")
    @Convert(converter = LanguageCodeConverter.class)
    @CsvBindAndSplitByName(column = "AdditionalLanguages", splitOn = "\\|+", elementType = Language.class, converter = dev.koicreek.bokasafn.mimir.catalog.models.converters.csv.acc.ToLanguageEnum.class)
    private List<Language> additionalLanguages = new ArrayList<>();

    @ManyToMany(cascade=CascadeType.PERSIST)
    @JoinTable(name = "BOOKS_AUTHORS",
            joinColumns = @JoinColumn(name = "isbn13"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @CsvBindAndSplitByName(column = "Authors", splitOn = "\\|+", elementType = AuthorCM.class, converter = ToAuthorCM.class)
    private Set<AuthorCM> authors = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "publisher")
    @CsvCustomBindByName(converter = ToPublisherCM.class)
    private PublisherCM publisher;

//    @ManyToMany(cascade=CascadeType.PERSIST)
//    @JoinTable(name = "BOOKS_LANGUAGES",
//        joinColumns = @JoinColumn(name = "isbn13"),
//        inverseJoinColumns = @JoinColumn(name = "language_id")
//    )
//    @CsvBindAndSplitByName(column = "AdditionalLanguages", splitOn = "\\|+", elementType = LanguageCM.class, converter = ToLanguageCM.class)
//    private List<LanguageCM> languages =  new ArrayList<>();

    //#region Constructors -----------------------------------------------

    public BookCM() {
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

    public Language getPrimaryLanguage() {
        return primaryLanguage;
    }

    public void setPrimaryLanguage(Language primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
    }

    public boolean isMultilingual() {
        return isMultilingual;
    }

    public void setMultilingual(boolean multilingual) {
        isMultilingual = multilingual;
    }

    public Set<AuthorCM> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorCM> authors) {
        this.authors = authors;
    }

    public PublisherCM getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherCM publisher) {
        this.publisher = publisher;
    }

    public List<Language> getAdditionalLanguages() {
        return additionalLanguages;
    }

    public void setAdditionalLanguages(List<Language> additionalLanguages) {
        this.additionalLanguages = additionalLanguages;
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

    public void addLanguage(Language language) {
        this.additionalLanguages.add(language);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCM bookCM = (BookCM) o;
        return isbn13 == bookCM.isbn13 && title.equals(bookCM.title) && primaryLanguage == bookCM.primaryLanguage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn13, title, primaryLanguage);
    }

    //#endRegion

    //#region Stringify

    public String toString() {
        return this.toString(false, false);
    }

    public String toString(boolean[] printNested) {
        return printNested.length == 2 ? this.toString(printNested[0], printNested[1]) : this.toString(false, false);
    }

    public String toString(boolean includeAuthors, boolean includeAdditionalLanguages) {
        StringBuilder sb = new StringBuilder("BookCM {");

        sb.append(String.format("\n\tisbn13: %d", this.isbn13));
        sb.append(String.format(",\n\ttitle: %s", wrap(this.title)));
        if(this.subtitle != null)
            sb.append(String.format(",\n\tsubtitle: %s", wrap(this.subtitle)));
        sb.append(String.format(",\n\tprimaryLanguage: %s", this.primaryLanguage.toStringSimplified()));
        if(includeAuthors)
            sb.append(String.format(",\n\tauthors: %s", indent(Stringify.toString(this.authors))));
        if(this.isMultilingual && includeAdditionalLanguages)
            sb.append(String.format(",\n\tadditionalLanguages: %s", indent(Stringify.toString(this.additionalLanguages))));
        sb.append(String.format(",\n\tdetails: %s", indent(this.details)));
        sb.append("\n}");

        return sb.toString();
    }

    public String toStringSimplified(boolean includeAuthors, boolean includeAdditionalLanguages) {
        StringBuilder sb = new StringBuilder("BookCM {");

        sb.append(String.format("\n\tisbn13: %d", this.isbn13));
        sb.append(String.format(",\n\ttitle: %s", wrap(this.title)));
        if(this.subtitle != null)
            sb.append(String.format(",\n\tsubtitle: %s", wrap(this.subtitle)));
        sb.append(String.format(",\n\tprimaryLanguage: %s", this.primaryLanguage.toStringSimplified()));
        if(includeAuthors)
            sb.append(String.format(",\n\tauthors: %s", indent(AuthorCM.toString(this.authors))));
        if(this.isMultilingual && includeAdditionalLanguages)
            sb.append(String.format(",\n\tadditionalLanguages: %s", indent(Language.toStringSimplified(this.additionalLanguages))));
        sb.append(String.format(",\n\tdetails: %s", indent(this.details)));
        sb.append("\n}");

        return sb.toString();
    }

    public static String toString(List<BookCM> bookList, boolean includeAuthors, boolean includeAdditionalLanguages) {
        if(bookList.size() == 0) return "[ <empty> ]";

        StringBuilder sb = new StringBuilder("[\n\t");
        sb.append(indent(bookList.get(0).toStringSimplified(includeAuthors, includeAdditionalLanguages)));

        for(int i=1; i < bookList.size(); ++i) {
            sb.append(",\n\t").append(indent(bookList.get(i).toStringSimplified(includeAuthors, includeAdditionalLanguages)));
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
