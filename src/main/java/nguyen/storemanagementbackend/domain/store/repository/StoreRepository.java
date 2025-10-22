package nguyen.storemanagementbackend.domain.store.repository;

import java.util.List;
import java.util.UUID;

import nguyen.storemanagementbackend.domain.user.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nguyen.storemanagementbackend.domain.store.model.StoreModel;

@Repository
public interface StoreRepository extends JpaRepository<StoreModel, UUID> {

    List<StoreModel> findByStoreOwner(Users storeOwner);
}