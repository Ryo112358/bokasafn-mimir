package dev.koicreek.bokasafn.mimir.catalog;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import dev.koicreek.bokasafn.mimir.catalog.model.AuthorCM;
import dev.koicreek.bokasafn.mimir.catalog.model.BookCM;
import dev.koicreek.bokasafn.mimir.catalog.model.LanguageCM;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
public class TestDataInitializer {

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

        while(itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    @Test
    final void VerifyDataInitialized(){
        Session session = this.sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<BookCM> criteria = cb.createQuery(BookCM.class);
        Root<BookCM> book = criteria.from(BookCM.class);
        List<BookCM> books = session.createQuery(criteria).getResultList();

        session.close();

        assertTrue(books.size() > 0);
    }
}
