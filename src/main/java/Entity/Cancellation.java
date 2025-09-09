package Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cancellations")
public class Cancellation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cancelId;

    @Column(nullable = false)
    private Long bookingId;

    @Column(nullable = false)
    private LocalDate cancelDate;

    private String reason;

    @Column(nullable = false)
    private Boolean refunded = false;

    public Cancellation() {}

    public Cancellation(Long bookingId, LocalDate cancelDate, String reason, Boolean refunded) {
        this.bookingId = bookingId;
        this.cancelDate = cancelDate;
        this.reason = reason;
        this.refunded = refunded;
    }

    public Long getCancelId() { return cancelId; }
    public void setCancelId(Long cancelId) { this.cancelId = cancelId; }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public LocalDate getCancelDate() { return cancelDate; }
    public void setCancelDate(LocalDate cancelDate) { this.cancelDate = cancelDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Boolean getRefunded() { return refunded; }
    public void setRefunded(Boolean refunded) { this.refunded = refunded; }
}
