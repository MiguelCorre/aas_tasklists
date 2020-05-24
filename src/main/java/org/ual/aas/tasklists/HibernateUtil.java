package org.ual.aas.tasklists;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.CriteriaQuery;


public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create registry
                registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build(); // É PRECISO MUDAR DIRECTORY NO POM PARA A DIRECTORY DO HIBERNATE.CFG.XML !

                // Create MetadataSources
                MetadataSources sources = new MetadataSources(registry);

                // Create Metadata
                Metadata metadata = sources.getMetadataBuilder().build();

                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

//    private static <T> List<T> loadAllData(Class<T> type, Session session) {
//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery<T> criteria;
//        criteria = builder.createQuery(type);
//        criteria.from(type);
//        List<T> data = session.createQuery(criteria).getResultList();
//        return data;
//  }
    
    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
