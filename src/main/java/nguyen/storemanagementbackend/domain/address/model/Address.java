package nguyen.storemanagementbackend.domain.address.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/** Address info
 * address_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
 * address_number int,
 * address_ward varchar(50),
 * address_district varchar(50),
 * address_city varchar(50),
 * address_country varchar(50)
 */

@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "address_id")
    private UUID addressId;

    @Column(name = "address_number", length = 30)
    private String addressNumber;

    @Column(name = "address_ward", length = 50)
    private String addressWard;

    @Column(name = "address_district", length = 50)
    private String addressDistrict;

    @Column(name="address_city", length = 50, nullable = false)
    private String addressCity;

    @Column(name="address_country", length = 50, nullable = false)
    private String addressCountry;
    
    @Column(name = "is_active")
    private boolean isActive;
}
