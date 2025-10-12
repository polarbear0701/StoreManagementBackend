package nguyen.storemanagementbackend.domain.store.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nguyen.storemanagementbackend.domain.store.model.StoreModel;

@Repository
public interface StoreRepository extends JpaRepository<StoreModel, UUID> {

	
}