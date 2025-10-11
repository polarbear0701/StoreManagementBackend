package nguyen.storemanagementbackend.domain.store.model;

import jakarta.persistence.*;
import lombok.*;
import nguyen.storemanagementbackend.domain.user.model.Users;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "store")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StoreModel {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "store_id")
	private UUID storeId;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "store_owner", referencedColumnName = "user_id", nullable = false)
    private Users storeOwner;

    @OneToOne
    @JoinColumn(name = "store_info_id", referencedColumnName = "store_info_id", unique = true)
    private StoreInfoModel storeInfo;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Memberships via join tables
    @ManyToMany
    @JoinTable(
        name = "store_admins",
        joinColumns = @JoinColumn(name = "store_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Users> admins;

    @ManyToMany
    @JoinTable(
        name = "store_staff",
        joinColumns = @JoinColumn(name = "store_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Users> staff;

    // Relations
    @OneToMany(mappedBy = "store")
    private List<StockModel> stocks;

    @OneToMany(mappedBy = "store")
    private List<StoreSlotModel> slots;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}