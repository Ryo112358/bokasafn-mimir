package dev.koicreek.bokasafn.mimir.catalog.reference;

import dev.koicreek.bokasafn.mimir.catalog.models.*;
import dev.koicreek.bokasafn.mimir.catalog.util.Stringify;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("test")
public class GeneralHibernateTests {

    @Autowired
    SessionFactory sessionFactory;

    @Test
    final void GetBookByIsbn13() {
        BookCM forkWitchWorm;

        try(Session session = this.sessionFactory.openSession()) {

            forkWitchWorm = session.get(BookCM.class, 9780593209226L);

            assertEquals("The Fork, the Witch, and the Worm", forkWitchWorm.getTitle());
            assertEquals(2019, forkWitchWorm.getDetails().getDatePublished().getYear());
            assertEquals(1, forkWitchWorm.getAuthors().size());
            assertEquals("Paolini", forkWitchWorm.getAuthors().iterator().next().getLastName());
            assertEquals("English", forkWitchWorm.getPrimaryLanguage().getName());
        }

        System.out.println(forkWitchWorm);
    }

    @Test
    final void GetAuthors_Page2_5PerPage() {
        List<AuthorCM> authors;

        try(Session session = this.sessionFactory.openSession()) {

            Query<AuthorCM> query = session.createQuery("FROM Authors a");
            query.setFirstResult(5);
            query.setMaxResults(5);

            authors = query.getResultList();
            assertEquals(5, authors.size());
        }

        System.out.println(Stringify.toString(authors));
    }

    /* Notes:
     *   @Qualifier("beanName") - Autowire a specific bean to field
     */
}
