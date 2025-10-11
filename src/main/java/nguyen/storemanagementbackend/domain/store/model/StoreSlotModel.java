package nguyen.storemanagementbackend.domain.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "store_slot")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StoreSlotModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_slot_id")
    private UUID storeSlotId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false)
    private StoreModel store;

    @Column(name = "slot_date", nullable = false)
    private LocalDate slotDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "storeSlot")
    private List<ReservationModel> reservations;
}
