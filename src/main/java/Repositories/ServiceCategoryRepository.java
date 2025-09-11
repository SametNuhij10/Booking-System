package Repositories;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import Entity.ServiceCategory;

import java.util.List;

@ApplicationScoped
public class ServiceCategoryRepository {

    @Inject
    private EntityManager em;

    public ServiceCategory findById(Long id) {
        return em.find(ServiceCategory.class, id);
    }

    public List<ServiceCategory> findAll() {
        return em.createQuery("SELECT c FROM ServiceCategory c", ServiceCategory.class).getResultList();
    }

    @Transactional
    public ServiceCategory create(ServiceCategory category) {
        em.persist(category);
        return category;
    }

    @Transactional
    public ServiceCategory update(ServiceCategory category) {
        return em.merge(category);
    }


    @Transactional
    public void delete(ServiceCategory category) {
        ServiceCategory managed;
        if (em.contains(category)) {
            managed=category;
        }
        else {
            managed=em.merge(category);
        }
        em.remove(managed);
    }
}
