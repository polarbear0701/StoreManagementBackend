package nguyen.storemanagementbackend.domain.store.model;

import jakarta.persistence.*;
import lombok.*;
import nguyen.storemanagementbackend.domain.user.model.Users;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StockModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "stock_id")
    private UUID stockId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false)
    private StoreModel store;

    @Column(name = "stock_name", length = 30, nullable = false)
    private String stockName;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @Column(name = "stock_description", length = 300)
    private String stockDescription;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "user_id")
    private Users updatedBy;
    
    @Column(name = "is_active")
    private boolean isActive;
}