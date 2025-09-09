package Config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {

    @Produces
    @ApplicationScoped
    public EntityManagerFactory emf() {
        return Persistence.createEntityManagerFactory("default");
    }

    @Produces
    @RequestScoped
    public EntityManager em(EntityManagerFactory emf) {
        return emf.createEntityManager();
    }

    public void closeEm(@Disposes EntityManager em) {
        if (em.isOpen()) em.close();
    }

    public void closeEmf(@Disposes EntityManagerFactory emf) {
        if (emf.isOpen()) emf.close();
    }
}
