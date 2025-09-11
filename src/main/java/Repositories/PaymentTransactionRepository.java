package Repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import Entity.PaymentTransaction;

import java.util.List;

@ApplicationScoped
public class PaymentTransactionRepository {

    @Inject
    private EntityManager em;

    public PaymentTransaction findById(Long id) {
        return em.find(PaymentTransaction.class, id);
    }

    public List<PaymentTransaction> findAll() {
        return em.createQuery("SELECT p FROM PaymentTransaction p", PaymentTransaction.class).getResultList();
    }

    @Transactional
    public PaymentTransaction create(PaymentTransaction txn) {
        em.persist(txn);
        return txn;
    }

    @Transactional
    public PaymentTransaction update(PaymentTransaction txn) {
        return em.merge(txn);
    }

    @Transactional
    public void delete(PaymentTransaction txn) {
        PaymentTransaction managed;
        if(em.contains(txn)) {
            managed=txn;
        }
        else {
            managed=em.merge(txn);
        }
        em.remove(managed);
    }
}

