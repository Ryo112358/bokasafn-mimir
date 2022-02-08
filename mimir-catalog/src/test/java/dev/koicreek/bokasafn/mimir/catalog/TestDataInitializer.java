package dev.koicreek.bokasafn.mimir.catalog;

import dev.koicreek.bokasafn.mimir.catalog.model.AuthorCM;
import dev.koicreek.bokasafn.mimir.catalog.model.BookCM;
import dev.koicreek.bokasafn.mimir.catalog.model.BookDetails;
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
import java.util.Arrays;
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
    final void init() {

        // --- Languages -----------------------

        LanguageCM english = new LanguageCM("eng", "English");
        LanguageCM estonian = new LanguageCM("est", "Estonian");

        List<LanguageCM> languages = Arrays.asList(
                english, estonian
        );

        // --- Authors -----------------------

        AuthorCM hill = new AuthorCM("Napoleon Hill","Hill", "Napoleon");
        AuthorCM paolini = new AuthorCM("Christopher Paolini","Paolini", "Christopher");
        AuthorCM hunter = new AuthorCM("Erin Hunter", "Hunter", "Erin");
        AuthorCM macHale = new AuthorCM("J. D. MacHale", "MacHale", "Donald", "James");
        AuthorCM riordan = new AuthorCM("Rick Riordan", "Riordan", "Richard", "Russell");
        AuthorCM rowling = new AuthorCM("J. K. Rowling", "Rowling", "Joanne");
        AuthorCM kiyosaki = new AuthorCM("Robert Kiyosaki", "Kiyosaki", "Robert", "Toru");
        AuthorCM ferrazzi = new AuthorCM("Keith Ferrazzi", "Ferrazzi", "Keith");
        AuthorCM george = new AuthorCM("Zak George", "George", "Zak");
        AuthorCM partanen = new AuthorCM("Anu Partanen", "Partanen", "Anu");

        List<AuthorCM> authors = Arrays.asList(
                hill,
                paolini,
                hunter,
                macHale,
                riordan,
                rowling,
                kiyosaki,
                ferrazzi,
                george,
                partanen
        );

        // --- Books -----------------------

        BookCM eragon = new BookCM(9780375826696L, "Eragon", paolini, english);
        eragon.setDetails(new BookDetails(2005, 528, "Knopf Books"));

        BookCM eldest = new BookCM(9780375840401L, "Eldest", paolini, english);
        eldest.setDetails(new BookDetails(2007, 704, "Knopf Books"));

        BookCM brisingr = new BookCM(9780375826740L, "Brisingr", paolini, english);
        brisingr.setDetails(new BookDetails(2010, 800, "Knopf Books"));

        BookCM inheritance = new BookCM(9780375846311L, "Inheritance", paolini, english);
        inheritance.setDetails(new BookDetails(2012, 880, "Knopf Books"));

        BookCM forkWitchWorm = new BookCM(9780593209226L, "The Fork, the Witch, and the Worm", paolini, english);
        forkWitchWorm.setSubtitle("Tales from Alagaësia, Vol. 1");
        forkWitchWorm.setDetails(new BookDetails(2019, 226, "Random House Print"));

        List<BookCM> books = Arrays.asList(
                eragon, eldest, brisingr, inheritance, forkWitchWorm
        );

        // --- Save test data to db -----------------------------

        Session session = this.sessionFactory.openSession();
        session.beginTransaction();

        for(LanguageCM language : languages) {
            session.save(language);
        }

        for(AuthorCM author : authors) {
            session.save(author);
        }

        for(BookCM book : books) {
            session.persist(book);
        }

        session.getTransaction().commit();
        session.close();
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
