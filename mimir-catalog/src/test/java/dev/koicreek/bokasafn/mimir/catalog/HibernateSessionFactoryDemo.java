package dev.koicreek.bokasafn.mimir.catalog;

import dev.koicreek.bokasafn.mimir.catalog.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.criteria.*;
import java.util.List;

import static dev.koicreek.bokasafn.mimir.catalog.util.Stringify.listToString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class HibernateSessionFactoryDemo {

    @Autowired
    SessionFactory sessionFactory;

    @Test
    final void GetBookByISBN13() {
        BookCM forkWitchWorm;

        Session session = this.sessionFactory.openSession();

        forkWitchWorm = session.get(BookCM.class, 9780593209226L);

        assertEquals("The Fork, the Witch, and the Worm", forkWitchWorm.getTitle());
        assertEquals(2019, forkWitchWorm.getDetails().getYearPublished());
        assertEquals(1, forkWitchWorm.getAuthors().size());
        assertEquals("Paolini", forkWitchWorm.getAuthors().get(0).getLastName());
        assertEquals(1, forkWitchWorm.getLanguages().size());
        assertEquals("English", forkWitchWorm.getLanguages().get(0).getName());

        session.close();

        System.out.println(forkWitchWorm);
    }

    @Test
    final void GetAuthorsPage2of2_5PerPage() {
        Session session = this.sessionFactory.openSession();

        Query<AuthorCM> query = session.createQuery("FROM Author a");
        query.setFirstResult(5);
        query.setMaxResults(5);

        List<AuthorCM> authors = query.list();
        assertEquals(5, authors.size());

        session.close();

        System.out.println(listToString(authors));
    }

    @Test
    final void GetAuthorByName_HQL() {
        Session session = this.sessionFactory.openSession();

        Query<AuthorCM> query = session.createQuery(
                "SELECT a " +
                        "FROM Author a " +
                        "WHERE a.lastName='Paolini' AND a.firstName='Christopher'");

        List<AuthorCM> authors = query.list();
        assertEquals(1, authors.size());

        AuthorCM paolini = authors.get(0);
        assertEquals("Christopher Paolini", paolini.getPenName());

        session.close();

        System.out.println(authors.get(0));
    }

    @Test
    final void GetBooksByAuthor_HQL() {
        Session session = this.sessionFactory.openSession();

        Query<BookCM> query = session.createQuery(
                "SELECT b " +
                        "FROM Book b JOIN b.authors a " +
                        "WHERE a.lastName='Paolini'");

        List<BookCM> books = query.list();
        assertEquals(5, books.size());

        session.close();

        System.out.println(listToString(books));
    }

    @Test
    final void GetBooksByAuthor_CriteriaBuilder() {
        Session session = this.sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<BookCM> criteria = cb.createQuery(BookCM.class);

        Root<BookCM> book = criteria.from(BookCM.class);

        // To investigate: JPA Static Metamodel config w/ Gradle
        Join<BookCM, AuthorCM> author = book.join("authors");
        criteria.where( cb.equal(author.get("lastName"), "Paolini") );

        List<BookCM> books = session.createQuery(criteria).getResultList();
        assertEquals(5, books.size());

        session.close();

        System.out.println(listToString(books));
    }

    @Test
    final void GetBooksByAuthor_CriteriaBuilder_StaticMetamodels() {
        Session session = this.sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<BookCM> criteria = cb.createQuery(BookCM.class);

        Root<BookCM> book = criteria.from(BookCM.class);
        Join<BookCM, AuthorCM> author = book.join(BookCM_.authors);
        criteria.where( cb.equal(author.get(AuthorCM_.lastName), "Paolini") );

        List<BookCM> books = session.createQuery(criteria).getResultList();
        assertEquals(5, books.size());

        session.close();

        System.out.println(listToString(books));
    }

    @Test
    final void GetBooksByPublisher_CriteriaBuilder_Embeddable() {
        Session session = this.sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<BookCM> criteria = cb.createQuery(BookCM.class);

        Root<BookCM> book = criteria.from(BookCM.class);
        criteria.where( cb.equal(book.get(BookCM_.details).get(BookDetails_.publisher), "Knopf Books") );

        List<BookCM> books = session.createQuery(criteria).getResultList();
        assertEquals(4, books.size());

        session.close();

        System.out.println(listToString(books));
    }

    @Test
    final void GetAuthorById_MaxId_Aggregate() {
        Session session = this.sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);

        Root<AuthorCM> authorRoot = criteria.from(AuthorCM.class);
        criteria.select( cb.max(authorRoot.get(AuthorCM_.id)) );

        Long maxAuthorId = session.createQuery(criteria).getSingleResult();
        assertTrue(maxAuthorId > 0);

        AuthorCM author = session.get(AuthorCM.class, maxAuthorId);

        session.close();

        System.out.println(author);
    }

    /* Notes:
     *   @Qualifier("beanName") - Autowire a specific bean to field
     */
}
