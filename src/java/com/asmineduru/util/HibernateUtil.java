package com.asmineduru.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author ERHAN
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            Configuration configuration = new Configuration()
                    .configure(); // configures settings from hibernate.cfg.xml

            StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();

            // If you miss the below line then it will complaing about a missing dialect setting
            serviceRegistryBuilder.applySettings(configuration.getProperties());

            ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
