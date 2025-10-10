package nguyen.storemanagementbackend.domain.store.model;

import jakarta.persistence.*;
import lombok.*;
import nguyen.storemanagementbackend.domain.address.model.Address;

import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "store_info")
public class StoreInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_id")
    private UUID storeId;

    @Column(name = "store_name")
    private String storeName;

    @OneToOne
    @JoinColumn(name = "store_address", referencedColumnName = "address_id")
    private Address storeAddress;

    @Column(name = "store_description")
    private String storeDescription;



}
