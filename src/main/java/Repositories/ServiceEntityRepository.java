package Repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import Entity.ServiceEntity;

import java.util.List;

@ApplicationScoped
public class ServiceEntityRepository {

    @Inject
    private EntityManager em;

    public ServiceEntity findById(Long id) {
        return em.find(ServiceEntity.class, id);
    }

    public List<ServiceEntity> findAll() {
        return em.createQuery("SELECT s FROM ServiceEntity s", ServiceEntity.class).getResultList();
    }

    @Transactional
    public ServiceEntity create(ServiceEntity service) {
        em.persist(service);
        return service;
    }

    @Transactional
    public ServiceEntity update(ServiceEntity service) {
        return em.merge(service);
    }

    @Transactional
    public void delete(ServiceEntity service) {
        ServiceEntity managed;
        if(em.contains(service)) {
         managed = service;        }
        else {
            managed = em.merge(em.merge(service));
        }
        em.remove(managed);
    }
}
