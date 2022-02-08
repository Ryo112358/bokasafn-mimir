package dev.koicreek.bokasafn.mimir.catalog.cfg;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile("test")
public class TestBeanConfig {

    @Bean()
    SessionFactory createSessionFactory() {
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .configure( "hibernate-test.cfg.xml" )
                .build();

        Metadata metadata = BeanConfig.buildSessionFactoryMetadata(standardRegistry);

        return metadata.getSessionFactoryBuilder().build();
    }

//    @Bean
//    static EntityManagerFactory getEntityManagerFactory() {
//        return Persistence.createEntityManagerFactory("dev.koicreek.bokasafn.mimir.catalog_test");
//    }

}
