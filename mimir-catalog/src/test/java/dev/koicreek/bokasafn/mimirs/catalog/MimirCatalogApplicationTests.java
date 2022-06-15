package dev.koicreek.bokasafn.mimirs.catalog;

import dev.koicreek.bokasafn.mimirs.catalog.models.AuthorCM;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class MimirCatalogApplicationTests {

	@Autowired
	SessionFactory sessionFactory;

	@Test
	final void FindAuthorByNameAndGetBooks_HQL() {
		AuthorCM paolini;

		try(Session session = this.sessionFactory.openSession()) {

			Query<AuthorCM> query = session.createQuery(
					"SELECT a " +
							"FROM Authors a " +
							"WHERE a.lastName='Paolini' AND a.firstName='Christopher'", AuthorCM.class);

			paolini = query.getSingleResult();
			assertEquals(5, paolini.getBooks().size());
		}

		System.out.println(paolini.toString(true));
	}
}