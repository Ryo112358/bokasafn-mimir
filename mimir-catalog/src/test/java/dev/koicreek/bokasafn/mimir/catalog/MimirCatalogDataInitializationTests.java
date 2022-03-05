package dev.koicreek.bokasafn.mimir.catalog;

import com.opencsv.bean.CsvToBeanBuilder;
import dev.koicreek.bokasafn.mimir.catalog.constants.Language;
import dev.koicreek.bokasafn.mimir.catalog.models.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.criteria.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ActiveProfiles("test")
@Order(0)
//@Disabled
public class MimirCatalogDataInitializationTests {

    @Autowired
    private SessionFactory sessionFactory;

    private List<BookCM> books;
    private List<AuthorCM> authors;
    private List<PublisherCM> publishers;
    private List<LanguageCM> languages;

    @BeforeAll
    final void init() throws FileNotFoundException {

        // --- Add Languages -----------------------

        this.languages = new CsvToBeanBuilder(new FileReader("src/test/resources/Languages.csv"))
                .withType(LanguageCM.class).build().parse();

        this.saveEntities(languages);

        // --- Add Authors -----------------------

        this.authors = new CsvToBeanBuilder(new FileReader("src/test/resources/Authors.csv"))
                .withType(AuthorCM.class).build().parse();

        this.saveEntities(authors);

        // --- Add Publishers -----------------------

        this.publishers = new CsvToBeanBuilder(new FileReader("src/test/resources/Publishers.csv"))
                .withType(PublisherCM.class).build().parse();

        this.saveEntities(publishers);

        // --- Add Books -----------------------

        this.books = new CsvToBeanBuilder(new FileReader("src/test/resources/Books.csv"))
                .withType(BookCM.class).build().parse();

        this.saveEntities(books);
    }

    @Test
    final void parseLanguagesCSV() {
        Iterator<LanguageCM> itr = languages.stream().iterator();

        assertTrue(itr.hasNext());

        while(itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    @Test
    final void parseAuthorsCSV() {
        Iterator<AuthorCM> itr = authors.stream().iterator();

        assertTrue(itr.hasNext());

        while(itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    @Test
    final void parsePublisherCSV() {
        Iterator<PublisherCM> itr = publishers.stream().iterator();

        assertTrue(itr.hasNext());

        while(itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    @Test
    final void parseBooksCSV() {
        // CsvToBean<BookCM> books = new CsvToBeanBuilder(new FileReader("src/test/resources/Books.csv"))
        //        .withType(BookCM.class).build();

        Iterator<BookCM> itr = books.stream().iterator();

        assertTrue(itr.hasNext());

        while(itr.hasNext()) {
            System.out.println(itr.next().toStringSimplified(true, true, true));
        }
    }

    @Test
    final void VerifyDataInitialized(){
        List<BookCM> books;

        try(Session session = this.sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // Populate Book Data - Publisher, Authors, & Additional Languages
            CriteriaQuery<BookCM> authorsQuery = cb.createQuery(BookCM.class);

            Root<BookCM> bookRoot = authorsQuery.from(BookCM.class);

            Fetch<BookCM, PublisherCM> publisherFetch = bookRoot.fetch(BookCM_.publisher);
            Fetch<BookCM, AuthorCM> authorFetch = bookRoot.fetch(BookCM_.authors);
            Fetch<BookCM, Language> languageFetch = bookRoot.fetch(BookCM_.additionalLanguages, JoinType.LEFT);

            books = session.createQuery(authorsQuery).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).getResultList();

            // Populate additional languages if present
            /* for(BookCM book : books) {
                if(book.isMultilingual())
                    Hibernate.initialize(book.getAdditionalLanguages());
            } */
        }

        assertTrue(books.size() > 0);

        System.out.println("Book Count: " + books.size());
        System.out.println(BookCM.toString(books, true, true,true));
    }

    //#region Helpers

    private <T> void saveEntities(List<T> entities) {
        try(Session session = this.sessionFactory.openSession()) {
            session.beginTransaction();

            for(T entity : entities) session.save(entity);

            session.getTransaction().commit();
        }
    }

    //#endRegion
}
