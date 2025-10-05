package nguyen.storemanagementbackend.domain.user.model;

import jakarta.persistence.*;
import lombok.*;
import nguyen.storemanagementbackend.common.enumeration.AuthProvider;
import nguyen.storemanagementbackend.common.enumeration.Role;
import nguyen.storemanagementbackend.domain.address.model.Address;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * -- TABLE 'user' schema
 *     user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
 *     email varchar(100) UNIQUE NOT NULL,
 *     password varchar(32) NOT NULL,
 *     user_name varchar(50),
 *     user_age Int check (user_age >= 0),
 *     user_address UUID references address(address_id) ON DELETE SET NULL,
 *     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 *     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 *     role varchar(20) NOT NULL DEFAULT 'STAFF',
 *     auth_provider varchar(20) NOT NULL DEFAULT 'LOCAL'
 */

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 32, nullable = false)
    private String password;

    @Column(name = "user_name", length = 50, nullable = false)
    private String userName;

    @Column(name = "user_age")
    private int userAge;

    @OneToOne
    @JoinColumn(name = "user_address", referencedColumnName = "address_id")
    private Address address;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20, nullable = false)
    private Role role = Role.STAFF;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider", length = 20, nullable = false)
    private AuthProvider authProvider = AuthProvider.LOCAL;

    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
