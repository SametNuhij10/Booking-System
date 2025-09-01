package Services;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import Entity.Booking;
import Repositories.BookingRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@ApplicationScoped
public class BookingService {

    @Inject
    private BookingRepository bookingRepository;


    @Transactional
    public Booking createBooking(Booking booking) {
        if (!isSlotAvailable(
                booking.getProviderId(),
                booking.getBookingDate(),
                booking.getStartTime(),
                booking.getEndTime())) {
            throw new IllegalStateException("Slot is already booked");
        }
        booking.setStatus("CONFIRMED");
        return bookingRepository.create(booking);
    }


    @Transactional
    public void cancelBooking(Long bookingId) {
        Booking b = bookingRepository.findById(bookingId);
        if (b == null) throw new IllegalArgumentException("Booking not found");
        b.setStatus("CANCELLED");
        bookingRepository.update(b);
    }


    @Transactional
    public Booking rescheduleBooking(Long bookingId, LocalDate newDate,
                                     LocalTime newStart, LocalTime newEnd) {
        Booking b = bookingRepository.findById(bookingId);
        if (b == null) throw new IllegalArgumentException("Booking not found");
        if (!isSlotAvailable(b.getProviderId(), newDate, newStart, newEnd)) {
            throw new IllegalStateException("New slot not available");
        }
        b.setBookingDate(newDate);
        b.setStartTime(newStart);
        b.setEndTime(newEnd);
        b.setStatus("RESCHEDULED");
        return bookingRepository.update(b);
    }


    public List<Booking> getBookingsForProviderOnDate(Long providerId, LocalDate date) {
        return bookingRepository.findByProviderAndDate(providerId, date);
    }


    public boolean isSlotAvailable(Long providerId, LocalDate date,
                                   LocalTime start, LocalTime end) {
        List<Booking> bookings = bookingRepository.findByProviderAndDate(providerId, date);
        return bookings.stream().noneMatch(b ->
                !(b.getEndTime().isBefore(start) || b.getStartTime().isAfter(end)) &&
                        !"CANCELLED".equals(b.getStatus()));
    }
}
