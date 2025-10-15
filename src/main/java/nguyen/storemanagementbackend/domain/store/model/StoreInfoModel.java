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
public class StoreInfoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_info_id")
    private UUID storeInfoId;

    @Column(name = "store_name", length = 50, nullable = false)
    private String storeName;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "store_address", referencedColumnName = "address_id")
    private Address storeAddress;

    @Column(name = "store_description")
    private String storeDescription;



}
