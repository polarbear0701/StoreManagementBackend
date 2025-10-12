package nguyen.storemanagementbackend.domain.store.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nguyen.storemanagementbackend.domain.store.model.ReservationModel;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationModel, UUID> {

	
}