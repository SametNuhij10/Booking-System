package Repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import Entity.Booking;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class BookingRepository {

    @Inject
    private EntityManager em;

    public Booking findById(Long id) {
        return em.find(Booking.class, id);
    }

    public List<Booking> findAll() {
        return em.createQuery("SELECT b FROM Booking b", Booking.class).getResultList();
    }

    @Transactional
    public Booking create(Booking booking) {
        em.persist(booking);
        return booking;
    }

    @Transactional
    public Booking update(Booking booking) {
        return em.merge(booking);
    }

    @Transactional
    public void delete(Booking booking) {
        Booking managed;
        if (em.contains(booking)) {
            managed = booking;
        } else {
            managed = em.merge(booking);
        }
        em.remove(managed);
    }

    public List<Booking> findByProviderAndDate(Long providerId, LocalDate date) {
        return em.createQuery(
                        "SELECT b FROM Booking b WHERE b.providerId = :providerId AND b.bookingDate = :date",
                        Booking.class)
                .setParameter("providerId", providerId)
                .setParameter("date", date)
                .getResultList();
    }
}

