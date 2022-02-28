package dev.koicreek.bokasafn.mimir.catalog;

import dev.koicreek.bokasafn.mimir.catalog.models.*;
import dev.koicreek.bokasafn.mimir.catalog.util.Stringify;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.criteria.*;
import java.util.List;

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
        assertEquals("Paolini", forkWitchWorm.getAuthors().iterator().next().getLastName());
        assertEquals("English", forkWitchWorm.getPrimaryLanguage().getName());

        session.close();

        System.out.println(forkWitchWorm);
    }

    @Test
    final void GetAuthors_Page2_5PerPage() {
        Session session = this.sessionFactory.openSession();

        Query<AuthorCM> query = session.createQuery("FROM Authors a");
        query.setFirstResult(5);
        query.setMaxResults(5);

        List<AuthorCM> authors = query.list();
        assertEquals(5, authors.size());

        session.close();

        System.out.println(Stringify.toString(authors));
    }

    @Test
    final void GetAuthorByName_HQL() {
        Session session = this.sessionFactory.openSession();

        Query<AuthorCM> query = session.createQuery(
                "SELECT a " +
                "FROM Authors a " +
                "WHERE a.lastName='Paolini' AND a.firstName='Christopher'", AuthorCM.class);

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
                "FROM Books b JOIN b.authors a " +
                "WHERE a.lastName='Paolini'", BookCM.class);

        List<BookCM> books = query.list();
        assertEquals(5, books.size());

        session.close();

        System.out.println(BookCM.toString(books, false, false));
    }

    @Test
    final void GetBooksByAuthor_CriteriaAPI() {
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

        System.out.println(BookCM.toString(books, false, false));
    }

    @Test
    final void GetBooksByAuthor_StaticMetamodels_CriteriaAPI() {
        Session session = this.sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<BookCM> criteria = cb.createQuery(BookCM.class);

        Root<BookCM> book = criteria.from(BookCM.class);
        Join<BookCM, AuthorCM> author = book.join(BookCM_.authors);
        criteria.where( cb.equal(author.get(AuthorCM_.lastName), "Paolini") );

        List<BookCM> books = session.createQuery(criteria).getResultList();
        assertEquals(5, books.size());

        session.close();

        System.out.println(BookCM.toString(books, false, false));
    }

    @Test
    final void GetBooksByYearPublished_Embeddable_CriteriaAPI() {
        Session session = this.sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<BookCM> criteria = cb.createQuery(BookCM.class);

        Root<BookCM> book = criteria.from(BookCM.class);
        criteria.where( cb.equal(book.get(BookCM_.details).get(BookDetails_.yearPublished), 2012) );

        List<BookCM> books = session.createQuery(criteria).getResultList();
        assertTrue(books.size() > 0);

        session.close();

        System.out.println(BookCM.toString(books, false, false));
    }

    @Test
    final void GetAuthorWithMaxId_Aggregate_HQL() {
        Session session = this.sessionFactory.openSession();

        Query<AuthorCM> query = session.createQuery(
                "SELECT a " +
                "FROM Authors a " +
                "WHERE a.id=(SELECT MAX(a.id) FROM Authors a)", AuthorCM.class);

        AuthorCM author = query.getSingleResult();
        assertTrue(author.getId() > 0);

        session.close();

        System.out.println(author);
    }

    @Test
    final void GetAuthorWithMaxId_Aggregate_CriteriaAPI() {
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
