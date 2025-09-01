package Services;

import Entity.Booking;
import Entity.Cancellation;
import Entity.PaymentTransaction;
import Repositories.BookingRepository;
import Repositories.CancellationRepository;
import Repositories.PaymentTransactionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ReportingService {

    @Inject
    private BookingRepository bookingRepository;

    @Inject
    private CancellationRepository cancellationRepository;

    @Inject
    private PaymentTransactionRepository paymentTransactionRepository;


    public List<Booking> getBookingsByDay(LocalDate date) {
        return bookingRepository.findAll()
                .stream()
                .filter(b -> date.equals(b.getBookingDate()))
                .toList();
    }


    public List<Booking> getBookingsByProvider(Long providerId) {
        return bookingRepository.findAll()
                .stream()
                .filter(b -> providerId.equals(b.getProviderId()))
                .toList();
    }

    public List<Cancellation> getAllCancellations() {
        return cancellationRepository.findAll();
    }


    public double getTotalRevenue() {
        return paymentTransactionRepository.findAll()
                .stream()
                .filter(txn -> "SUCCESS".equalsIgnoreCase(txn.getStatus()))
                .mapToDouble(txn -> txn.getAmount().doubleValue())
                .sum();
    }


    public List<PaymentTransaction> getFailedTransactions() {
        return paymentTransactionRepository.findAll()
                .stream()
                .filter(txn -> !"SUCCESS".equalsIgnoreCase(txn.getStatus()))
                .toList();
    }
}
