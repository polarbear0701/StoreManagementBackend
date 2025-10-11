package nguyen.storemanagementbackend.domain.store.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import nguyen.storemanagementbackend.domain.store.model.StoreModel;

public interface StoreRepository extends JpaRepository<StoreModel, UUID> {

	
}