package Repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import Entity.Provider;

import java.util.List;

@ApplicationScoped
public class ProviderRepository {

    @Inject
    private EntityManager em;

    public Provider findById(Long id) {
        return em.find(Provider.class, id);
    }

    public List<Provider> findAll() {
        return em.createQuery("SELECT p FROM Provider p", Provider.class).getResultList();
    }

    @Transactional
    public Provider create(Provider provider) {
        em.persist(provider);
        return provider;
    }

    @Transactional
    public Provider update(Provider provider) {
        return em.merge(provider);
    }

    @Transactional
    public void delete(Provider provider) {
        Provider managed = em.contains(provider) ? provider : em.merge(provider);
        em.remove(managed);
    }
}
