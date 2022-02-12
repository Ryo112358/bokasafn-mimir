package dev.koicreek.bokasafn.mimir.catalog;

import dev.koicreek.bokasafn.mimir.catalog.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class MimirCatalogApplicationTests {

	@Autowired
	SessionFactory sessionFactory;

	@Test
	final void FindAuthorByNameAndGetBooks_HQL() {
		Session session = this.sessionFactory.openSession();

		Query<AuthorCM> query = session.createQuery(
				"SELECT a " +
					"FROM Authors a " +
					"WHERE a.lastName='Paolini' AND a.firstName='Christopher'", AuthorCM.class);

		List<AuthorCM> authors = query.list();
		assertEquals(1, authors.size());

		AuthorCM paolini = authors.get(0);
		assertEquals(5, paolini.getBooks().size());

		session.close();

		System.out.println(paolini.toString(true));
	}

	/* Notes:
	 *   @Qualifier("beanName") - Autowire a specific bean to field
	 */
}