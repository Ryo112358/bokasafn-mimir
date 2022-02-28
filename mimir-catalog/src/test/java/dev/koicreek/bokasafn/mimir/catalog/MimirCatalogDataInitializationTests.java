package dev.koicreek.bokasafn.mimir.catalog;

import com.opencsv.bean.CsvToBean;
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

        Session session;

        // --- Add Languages -----------------------

        this.languages = new CsvToBeanBuilder(new FileReader("src/test/resources/Languages.csv"))
                .withType(LanguageCM.class).build().parse();

        session = this.sessionFactory.openSession();
        session.beginTransaction();

        for(LanguageCM language : languages) {
            session.save(language);
        }

        session.getTransaction().commit();
        session.close();

        // --- Add Authors -----------------------

        this.authors = new CsvToBeanBuilder(new FileReader("src/test/resources/Authors.csv"))
                .withType(AuthorCM.class).build().parse();

        session = this.sessionFactory.openSession();
        session.beginTransaction();

        for(AuthorCM author : authors) {
            session.save(author);
        }

        session.getTransaction().commit();
        session.close();

        // --- Add Publishers -----------------------

        this.publishers = new CsvToBeanBuilder(new FileReader("src/test/resources/Publishers.csv"))
                .withType(PublisherCM.class).build().parse();

        session = this.sessionFactory.openSession();
        session.beginTransaction();

        for(PublisherCM publisher : publishers) {
            session.save(publisher);
        }

        session.getTransaction().commit();
        session.close();

        // --- Add Books -----------------------

        this.books = new CsvToBeanBuilder(new FileReader("src/test/resources/Books.csv"))
                .withType(BookCM.class).build().parse();

        session = this.sessionFactory.openSession();
        session.beginTransaction();

        for(BookCM book : books) {
            session.save(book);
        }

        session.getTransaction().commit();
        session.close();
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
    final void parseBooksCSV() throws FileNotFoundException {
//        CsvToBean<BookCM> books = new CsvToBeanBuilder(new FileReader("src/test/resources/Books.csv"))
//                .withType(BookCM.class).build();

        Iterator<BookCM> itr = books.stream().iterator();

        assertTrue(itr.hasNext());

        while(itr.hasNext()) {
            System.out.println(itr.next().toStringSimplified(true, true));
        }
    }

    @Test
    final void VerifyDataInitialized(){
        Session session = this.sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        List<BookCM> books;

        // Join Books and Authors
        CriteriaQuery<BookCM> authorsQuery = cb.createQuery(BookCM.class);
        Root<BookCM> bookRoot = authorsQuery.from(BookCM.class);
        Fetch<BookCM, AuthorCM> authorFetch = bookRoot.fetch(BookCM_.authors);
//        Fetch<BookCM, Language> languageFetch = bookRoot.fetch(BookCM_.additionalLanguages);
        books = session.createQuery(authorsQuery).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).getResultList();

        for(BookCM book : books) {
            if(book.isMultilingual()) {
                System.out.println(book.getAdditionalLanguages());
            }
        }

        // Join Books and Languages
//        CriteriaQuery<BookCM> languagesCriteria = cb.createQuery(BookCM.class);
//        Root<BookCM> bookRoot = languagesCriteria.from(BookCM.class);
//        Fetch<BookCM, Language> languageFetch = bookRoot.fetch(BookCM_.additionalLanguages);
//        books = session.createQuery(languagesCriteria).getResultList();

        session.close();


        assertTrue(books.size() > 0);

        System.out.println("Book Count: " + books.size());
        System.out.println(BookCM.toString(books, true, true));
    }
}
