package dev.koicreek.bokasafn.mimirs.catalog.cfg;

import dev.koicreek.bokasafn.mimirs.catalog.models.AuthorCM;
import dev.koicreek.bokasafn.mimirs.catalog.models.BookCM;
import dev.koicreek.bokasafn.mimirs.catalog.models.LanguageCM;
import dev.koicreek.bokasafn.mimirs.catalog.models.PublisherCM;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class DBConfig {

    @Bean
    static SessionFactory getSessionFactory() {
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
            .configure( "cfg/hibernate.cfg.xml" )
            .build();

        Metadata metadata = buildSessionFactoryMetadata(standardRegistry);

        return metadata.getSessionFactoryBuilder().build();
    }

    //#region Helpers

    static Metadata buildSessionFactoryMetadata(StandardServiceRegistry standardRegistry) {

        return new MetadataSources( standardRegistry )
                .addAnnotatedClass( BookCM.class )
                .addAnnotatedClass( AuthorCM.class )
                .addAnnotatedClass( LanguageCM.class )
                .addAnnotatedClass( PublisherCM.class )
                .getMetadataBuilder()
                .build();
    }

    //#endRegion

//    @Bean
//    static EntityManagerFactory getEntityManagerFactory() {
//        return Persistence.createEntityManagerFactory("dev.koicreek.bokasafn.mimir.catalog");
//    }

}
