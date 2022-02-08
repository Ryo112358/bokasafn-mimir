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
@Disabled
public class TestDataInitializer {

    @Autowired
    SessionFactory sessionFactory;

    @BeforeAll
    final void init() {
        LanguageCM english = new LanguageCM("eng", "English");

        AuthorCM hill = new AuthorCM("Napoleon Hill","Hill", "Napoleon");
        AuthorCM paolini = new AuthorCM("Christopher Paolini","Paolini", "Christopher");
        AuthorCM hunter = new AuthorCM("Erin Hunter", "Hunter", "Erin");
        AuthorCM macHale = new AuthorCM("J. D. MacHale", "MacHale", "Donald", "James");
        AuthorCM riordan = new AuthorCM("Rick Riordan", "Riordan", "Richard", "Russell");
        AuthorCM rowling = new AuthorCM("J. K. Rowling", "Rowling", "Joanne");

        List<AuthorCM> authors = Arrays.asList(
                hunter,
                macHale,
                riordan,
                rowling,
                new AuthorCM("Robert Kiyosaki", "Kiyosaki", "Robert", "Toru"),
                new AuthorCM("Keith Ferrazzi", "Ferrazzi", "Keith"),
                new AuthorCM("Zak George", "George", "Zak"),
                new AuthorCM("Anu Partanen", "Partanen", "Anu")
        );


        BookDetails eragonDetails = new BookDetails(2005, 528, "Knopf Books");
        BookCM eragon = new BookCM(9780375826696L, "Eragon", eragonDetails);

        eragon.getAuthors().add(paolini);
        eragon.getLanguages().add(english);

        BookDetails eldestDetails = new BookDetails(2007, 704, "Knopf Books");
        BookCM eldest = new BookCM(9780375840401L, "Eldest", eldestDetails);

        eldest.addAuthor(paolini);
        eldest.addLanguage(english);

        BookDetails forkWitchWormDetails = new BookDetails(2019, 226, "Random House Print");
        BookCM forkWitchWorm = new BookCM(9780593209226L, "The Fork, the Witch, and the Worm", "Tales from Alagaësia, Vol. 1", forkWitchWormDetails);

        forkWitchWorm.addAuthor(paolini);
        forkWitchWorm.addLanguage(english);


        Session session = this.sessionFactory.openSession();
        session.beginTransaction();

        session.save(hill);

        session.persist(eragon);
        session.persist(eldest);
        session.persist(forkWitchWorm);

        for(AuthorCM author : authors) {
            session.save(author);
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
