package Repositories;

import Entity.Availability;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class AvailabilityRepository {

    @Inject
    private EntityManager em;

    public Availability findById(Long id) {
        return em.find(Availability.class, id);
    }

    public List<Availability> findAll() {
        return em.createQuery("SELECT a FROM Availability a", Availability.class)
                .getResultList();
    }

    @Transactional
    public Availability create(Availability availability) {
        em.persist(availability);
        return availability;
    }

    @Transactional
    public Availability update(Availability availability) {
        return em.merge(availability);
    }

    @Transactional
    public void delete(Availability availability) {
        Availability managed;
        if (em.contains(availability)) {
            managed = availability;
        } else {
            managed = em.merge(availability);
        }
        em.remove(managed);
    }
}
