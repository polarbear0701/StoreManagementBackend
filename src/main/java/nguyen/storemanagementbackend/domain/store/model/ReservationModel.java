package nguyen.storemanagementbackend.domain.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReservationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reservation_id")
    private UUID reservationId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false)
    private StoreModel store;

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_slot_id", referencedColumnName = "store_slot_id", nullable = false)
    private StoreSlotModel storeSlot;

    @Column(name = "status", length = 20, nullable = false)
    private String status; // 'BOOKED' | 'CANCELLED'

    @Column(name = "party_size", nullable = false)
    private int partySize;

    @Column(name = "guest_name", length = 100)
    private String guestName;

    @Column(name = "guest_phone", length = 15)
    private String guestPhone;

    @Column(name = "booked_at", nullable = false)
    private LocalDateTime bookedAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        bookedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
