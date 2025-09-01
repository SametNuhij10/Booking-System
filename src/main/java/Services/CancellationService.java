package Services;

import Entity.Cancellation;
import Repositories.CancellationRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class CancellationService {

    @Inject
    private CancellationRepository cancellationRepository;

    public Cancellation findById(Long id) {
        return cancellationRepository.findById(id);
    }

    public List<Cancellation> findAll() {
        return cancellationRepository.findAll();
    }

    @Transactional
    public Cancellation createCancellation(Long bookingId, String reason, boolean refunded) {
        Cancellation cancellation = new Cancellation();
        cancellation.setBookingId(bookingId);
        cancellation.setCancelDate(LocalDate.now());
        cancellation.setReason(reason);
        cancellation.setRefunded(refunded);

        return cancellationRepository.create(cancellation);
    }

    @Transactional
    public Cancellation updateCancellation(Cancellation cancellation) {
        return cancellationRepository.update(cancellation);
    }

    @Transactional
    public void deleteCancellation(Long id) {
        Cancellation c = cancellationRepository.findById(id);
        if (c != null) {
            cancellationRepository.delete(c);
        }
    }
}
