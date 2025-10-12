package nguyen.storemanagementbackend.domain.store.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nguyen.storemanagementbackend.domain.store.model.StoreSlotModel;

@Repository
public interface StoreSlotRepository extends JpaRepository<StoreSlotModel, UUID> {

	
}