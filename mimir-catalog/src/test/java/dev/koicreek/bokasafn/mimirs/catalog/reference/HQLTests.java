package dev.koicreek.bokasafn.mimirs.catalog.reference;

import dev.koicreek.bokasafn.mimirs.catalog.models.AuthorCM;
import dev.koicreek.bokasafn.mimirs.catalog.models.BookCM;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class HQLTests {

    @Autowired
    SessionFactory sessionFactory;

    @Test
    final void GetAuthorByName() {
        AuthorCM paolini;

        try(Session session = this.sessionFactory.openSession()) {

            Query<AuthorCM> query = session.createQuery(
                    "SELECT a " +
                            "FROM Authors a " +
                            "WHERE a.lastName='Paolini' AND a.firstName='Christopher'", AuthorCM.class);

            paolini = query.getSingleResult();
        }

        System.out.println(paolini);
        assertEquals("Christopher Paolini", paolini.getPenName());
    }

    @Test
    final void GetBooksByAuthor() {
        List<BookCM> books;

        try(Session session = this.sessionFactory.openSession()) {

            Query<BookCM> query = session.createQuery(
                    "SELECT b " +
                            "FROM Books b JOIN b.authors a " +
                            "WHERE a.lastName='Paolini'", BookCM.class);

            books = query.getResultList();
            assertEquals(5, books.size());
        }

        System.out.println("Book Count: " + books.size());
        System.out.println(BookCM.toString(books, false, false, false));
    }

    @Test
    final void GetAuthorWithMaxId_Aggregation() {
        AuthorCM author;

        try(Session session = this.sessionFactory.openSession()) {

            Query<AuthorCM> query = session.createQuery(
                    "SELECT a " +
                            "FROM Authors a " +
                            "WHERE a.id=(SELECT MAX(a.id) FROM Authors a)", AuthorCM.class);

            author = query.getSingleResult();
            assertTrue(author.getId() > 0);
        }

        System.out.println(author);
    }
}
