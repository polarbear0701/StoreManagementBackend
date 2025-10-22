package nguyen.storemanagementbackend.domain.store.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import nguyen.storemanagementbackend.domain.user.model.Users;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "store")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class StoreModel {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "store_id")
	private UUID storeId;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "store_owner", referencedColumnName = "user_id", nullable = false)
    private Users storeOwner;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
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
    @Builder.Default
    private Set<Users> admins = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "store_staff",
        joinColumns = @JoinColumn(name = "store_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
    private Set<Users> staff = new HashSet<>();

    // Relations
    @OneToMany(mappedBy = "store")
    @Builder.Default
    private List<StockModel> stocks = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    @Builder.Default
    private List<StoreSlotModel> slots = new ArrayList<>();

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