package Repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import Entity.Cancellation;

import java.util.List;

@ApplicationScoped
public class CancellationRepository {

    @Inject
    private EntityManager em;

    public Cancellation findById(Long id) {
        return em.find(Cancellation.class, id);
    }

    public List<Cancellation> findAll() {
        return em.createQuery("SELECT c FROM Cancellation c", Cancellation.class).getResultList();
    }

    @Transactional
    public Cancellation create(Cancellation cancellation) {
        em.persist(cancellation);
        return cancellation;
    }

    @Transactional
    public Cancellation update(Cancellation cancellation) {
        return em.merge(cancellation);
    }

    @Transactional
    public void delete(Cancellation cancellation) {
        Cancellation managed;
        if (em.contains(cancellation)) {
            managed = cancellation;
        } else {
            managed = em.merge(cancellation);
        }
        em.remove(managed);
    }
}
