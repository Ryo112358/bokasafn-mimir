package dev.koicreek.bokasafn.mimir.catalog;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import dev.koicreek.bokasafn.mimir.catalog.model.AuthorCM;
import dev.koicreek.bokasafn.mimir.catalog.model.BookCM;
import dev.koicreek.bokasafn.mimir.catalog.model.BookCM_;
import dev.koicreek.bokasafn.mimir.catalog.model.LanguageCM;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Root;
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
    SessionFactory sessionFactory;

    @BeforeAll
    final void init() throws FileNotFoundException {

        Session session;

        // --- Add Languages -----------------------

        List<LanguageCM> languages = new CsvToBeanBuilder(new FileReader("src/test/resources/Languages.csv"))
                .withType(LanguageCM.class).build().parse();

        session = this.sessionFactory.openSession();
        session.beginTransaction();

        for(LanguageCM language : languages) {
            session.save(language);
        }

        session.getTransaction().commit();
        session.close();

        // --- Add Authors -----------------------

        List<AuthorCM> authors = new CsvToBeanBuilder(new FileReader("src/test/resources/Authors.csv"))
                .withType(AuthorCM.class).build().parse();

        session = this.sessionFactory.openSession();
        session.beginTransaction();

        for(AuthorCM author : authors) {
            session.save(author);
        }

        session.getTransaction().commit();
        session.close();

        // --- Add Books -----------------------

        List<BookCM> books = new CsvToBeanBuilder(new FileReader("src/test/resources/Books.csv"))
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
    final void parseLanguagesCSV() throws FileNotFoundException {
        CsvToBean<LanguageCM> csv = new CsvToBeanBuilder(new FileReader("src/test/resources/Languages.csv"))
                .withType(LanguageCM.class).build();

        Iterator<LanguageCM> itr = csv.stream().iterator();

        assertTrue(itr.hasNext());

        while(itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    @Test
    final void parseAuthorsCSV() throws FileNotFoundException {
        CsvToBean<AuthorCM> csv = new CsvToBeanBuilder(new FileReader("src/test/resources/Authors.csv"))
                .withType(AuthorCM.class).build();

        Iterator<AuthorCM> itr = csv.stream().iterator();

        assertTrue(itr.hasNext());

        while(itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    @Test
    final void VerifyDataInitialized(){
        Session session = this.sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        // Join Books and Authors
        CriteriaQuery<BookCM> authorsCriteria = cb.createQuery(BookCM.class);
        authorsCriteria.from(BookCM.class).fetch(BookCM_.authors);
        session.createQuery(authorsCriteria).getResultList();

        // Join Books and Languages
        CriteriaQuery<BookCM> languagesCriteria = cb.createQuery(BookCM.class);
        Root<BookCM> bookRoot = languagesCriteria.from(BookCM.class);
        Fetch<BookCM, LanguageCM> languageFetch = bookRoot.fetch(BookCM_.languages);
        List<BookCM> books = session.createQuery(languagesCriteria).getResultList();

        session.close();

        assertTrue(books.size() > 0);

        System.out.println(BookCM.toString(books, true, true));
    }
}
