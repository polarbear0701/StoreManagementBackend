package nguyen.storemanagementbackend.domain.store.service;

import java.time.LocalDateTime;
import java.util.UUID;
import nguyen.storemanagementbackend.common.dto.StockResponseBasedDto;
import nguyen.storemanagementbackend.domain.store.dto.stock.request.NewStockRequestDto;
import nguyen.storemanagementbackend.domain.store.mapper.StockMapper;
import nguyen.storemanagementbackend.domain.store.model.StockModel;
import nguyen.storemanagementbackend.domain.store.model.StoreModel;
import nguyen.storemanagementbackend.domain.store.repository.StockRepository;
import nguyen.storemanagementbackend.domain.store.repository.StoreRepository;
import nguyen.storemanagementbackend.domain.user.model.Users;
import nguyen.storemanagementbackend.domain.user.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private final StoreRepository storeRepository;
    private final UsersRepository userRepository;

    public StockService(
        StockRepository stockRepository,
        StockMapper stockMapper,
        StoreRepository storeRepository,
        UsersRepository userRepository
    ) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
    }

    public StockResponseBasedDto createNewStock(
        NewStockRequestDto stockRequest,
        UUID currentUserId
    ) {
        StoreModel store = storeRepository
            .findById(stockRequest.getStoreId())
            .orElseThrow(() ->
                new RuntimeException(
                    "Store not found with id: " + stockRequest.getStoreId()
                )
            );

        StockModel stockModel = stockMapper.toStockModel(stockRequest);

        stockModel.setStore(store);
        stockModel.setUpdatedAt(LocalDateTime.now());
        Users currentUser = userRepository
            .findById(currentUserId)
            .orElseThrow(() ->
                new RuntimeException("User not found with id: " + currentUserId)
            );
        stockModel.setUpdatedBy(currentUser);

        StockModel savedStock = stockRepository.save(stockModel);

        return stockMapper.toStockResponseBasedDto(savedStock);
    }
}
