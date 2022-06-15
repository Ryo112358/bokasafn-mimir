package dev.koicreek.bokasafn.mimirs.catalog.reference;

import dev.koicreek.bokasafn.mimirs.catalog.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class CriteriaApiTests {

    @Autowired
    SessionFactory sessionFactory;

    @Test
    final void GetBooksByAuthor() {
        List<BookCM> books;

        try(Session session = this.sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<BookCM> criteria = cb.createQuery(BookCM.class);

            Root<BookCM> book = criteria.from(BookCM.class);

            // To investigate: JPA Static Metamodel config w/ Gradle
            Join<BookCM, AuthorCM> author = book.join("authors");
            criteria.where( cb.equal(author.get("lastName"), "Paolini") );

            books = session.createQuery(criteria).getResultList();
            assertEquals(5, books.size());
        }

        System.out.println("Book Count: " + books.size());
        System.out.println(BookCM.toString(books, false, false, false));
    }

    @Test
    final void GetBooksByAuthor_StaticMetamodels() {
        List<BookCM> books;

        try(Session session = this.sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<BookCM> criteria = cb.createQuery(BookCM.class);

            Root<BookCM> book = criteria.from(BookCM.class);
            Join<BookCM, AuthorCM> author = book.join(BookCM_.authors);
            criteria.where( cb.equal(author.get(AuthorCM_.lastName), "Paolini") );

            books = session.createQuery(criteria).getResultList();
            assertEquals(5, books.size());
        }

        System.out.println("Book Count: " + books.size());
        System.out.println(BookCM.toString(books, false, false, false));
    }

    @Test
    final void GetBooksByPageCount_Embeddable() {
        List<BookCM> books;

        try(Session session = this.sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<BookCM> criteria = cb.createQuery(BookCM.class);

            Root<BookCM> book = criteria.from(BookCM.class);
            criteria.where( cb.greaterThan(book.get(BookCM_.details).get(BookDetails_.pageCount), 500) );

            books = session.createQuery(criteria).getResultList();
            assertTrue(books.size() > 0);
        }

        System.out.println("Book Count: " + books.size());
        System.out.println(BookCM.toString(books, false, false, false));
    }

    @Test
    final void GetBooksByDatePublished_Embeddable() {
        List<BookCM> books;

        try(Session session = this.sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<BookCM> criteria = cb.createQuery(BookCM.class);

            Root<BookCM> book = criteria.from(BookCM.class);
            criteria.where( cb.between(book.get(BookCM_.details).get(BookDetails_.datePublished), LocalDate.of(2012, 1, 1), LocalDate.of(2012, 12, 31)) );

            books = session.createQuery(criteria).getResultList();
            assertTrue(books.size() > 0);
        }

        System.out.println("Book Count: " + books.size());
        System.out.println(BookCM.toString(books, false, false, false));
    }

    @Test
    final void GetAuthorWithMaxId_Aggregation() {
        AuthorCM author;

        try(Session session = this.sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<Long> criteria = cb.createQuery(Long.class);

            Root<AuthorCM> authorRoot = criteria.from(AuthorCM.class);
            criteria.select( cb.max(authorRoot.get(AuthorCM_.id)) );

            Long maxAuthorId = session.createQuery(criteria).getSingleResult();
            assertTrue(maxAuthorId > 0);

            author = session.get(AuthorCM.class, maxAuthorId);
        }

        System.out.println(author);
    }
}
