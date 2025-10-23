package nguyen.storemanagementbackend.domain.user.repository;

import nguyen.storemanagementbackend.domain.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByEmail(String email);

    Void deleteByEmail(String email);

    List<Users> findAll();

    boolean existsByEmail(String email);
}
