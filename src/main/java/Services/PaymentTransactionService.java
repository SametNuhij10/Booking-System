package Services;

import Entity.PaymentTransaction;
import Repositories.PaymentTransactionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PaymentTransactionService {

    @Inject
    private PaymentTransactionRepository paymentTransactionRepository;

    public PaymentTransaction findById(Long id) {
        return paymentTransactionRepository.findById(id);
    }

    public List<PaymentTransaction> findAll() {
        return paymentTransactionRepository.findAll();
    }

    @Transactional
    public PaymentTransaction createTransaction(Long bookingId, double amount) {
        PaymentTransaction txn = new PaymentTransaction();
        txn.setBookingId(bookingId);
        txn.setAmount(BigDecimal.valueOf(amount));
        txn.setDate(LocalDate.from(LocalDateTime.now()));
        txn.setStatus("PENDING");

        return paymentTransactionRepository.create(txn);
    }

    @Transactional
    public PaymentTransaction updateStatus(Long txnId, String newStatus) {
        PaymentTransaction txn = paymentTransactionRepository.findById(txnId);
        if (txn != null) {
            txn.setStatus(newStatus);
            return paymentTransactionRepository.update(txn);
        }
        return null;
    }

    @Transactional
    public void delete(Long txnId) {
        PaymentTransaction txn = paymentTransactionRepository.findById(txnId);
        if (txn != null) {
            paymentTransactionRepository.delete(txn);
        }
    }
}
