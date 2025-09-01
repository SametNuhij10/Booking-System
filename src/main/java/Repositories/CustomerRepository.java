package Repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import Entity.Customer;

import java.util.List;

@ApplicationScoped
public class CustomerRepository {

    @Inject
    private EntityManager em;

    public Customer findById(Long id) {
        return em.find(Customer.class, id);
    }

    public List<Customer> findAll() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    @Transactional
    public Customer create(Customer customer) {
        em.persist(customer);
        return customer;
    }

    @Transactional
    public Customer update(Customer customer) {
        return em.merge(customer);
    }

    @Transactional
    public void delete(Customer customer) {
        Customer managed = em.contains(customer) ? customer : em.merge(customer);
        em.remove(managed);
    }
}
