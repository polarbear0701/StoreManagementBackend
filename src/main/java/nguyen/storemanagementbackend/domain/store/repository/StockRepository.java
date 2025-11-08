package nguyen.storemanagementbackend.domain.store.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import nguyen.storemanagementbackend.domain.store.model.StockModel;
import nguyen.storemanagementbackend.domain.store.model.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<StockModel, UUID> {
    List<StockModel> findByStore(StoreModel store);

    List<StockModel> findByStoreStoreId(UUID storeId);

    List<StockModel> findByStoreAndIsActive(StoreModel store, boolean isActive);

    List<StockModel> findByStoreStoreIdAndIsActive(
        UUID storeId,
        boolean isActive
    );

    Optional<StockModel> findByStockIdAndStoreStoreId(
        UUID stockId,
        UUID storeId
    );

    @Query(
        "SELECT s FROM StockModel s WHERE s.store.storeId = :storeId AND s.stockName LIKE %:stockName%"
    )
    List<StockModel> findByStoreIdAndStockNameContaining(
        @Param("storeId") UUID storeId,
        @Param("stockName") String stockName
    );

    @Query(
        "SELECT s FROM StockModel s WHERE s.store.storeId = :storeId AND s.stockQuantity < :threshold"
    )
    List<StockModel> findLowStockByStore(
        @Param("storeId") UUID storeId,
        @Param("threshold") int threshold
    );

    boolean existsByStockNameAndStoreStoreId(String stockName, UUID storeId);
}
