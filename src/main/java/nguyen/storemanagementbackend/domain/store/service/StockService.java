package nguyen.storemanagementbackend.domain.store.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import nguyen.storemanagementbackend.common.dto.StockResponseBasedDto;
import nguyen.storemanagementbackend.domain.store.dto.stock.request.NewStockRequestDto;
import nguyen.storemanagementbackend.domain.store.dto.stock.request.UpdateStockRequestDto;
import nguyen.storemanagementbackend.domain.store.dto.stock.response.DetailedStockResponseDto;
import nguyen.storemanagementbackend.domain.store.mapper.StockMapper;
import nguyen.storemanagementbackend.domain.store.model.StockModel;
import nguyen.storemanagementbackend.domain.store.model.StoreModel;
import nguyen.storemanagementbackend.domain.store.repository.StockRepository;
import nguyen.storemanagementbackend.domain.store.repository.StoreRepository;
import nguyen.storemanagementbackend.domain.user.model.Users;
import nguyen.storemanagementbackend.domain.user.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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

    // CREATE - Create new stock
    public StockResponseBasedDto createNewStock(
        NewStockRequestDto stockRequest,
        UUID currentUserId
    ) {
        // Validate that the store exists
        StoreModel store = storeRepository
            .findById(stockRequest.getStoreId())
            .orElseThrow(() ->
                new RuntimeException(
                    "Store not found with id: " + stockRequest.getStoreId()
                )
            );

        // Check if stock with same name already exists in the store
        if (
            stockRepository.existsByStockNameAndStoreStoreId(
                stockRequest.getStockName(),
                stockRequest.getStoreId()
            )
        ) {
            throw new RuntimeException(
                "Stock with name '" +
                    stockRequest.getStockName() +
                    "' already exists in this store"
            );
        }

        // Map DTO to entity
        StockModel stockModel = stockMapper.toStockModel(stockRequest);

        // Set the store reference
        stockModel.setStore(store);

        // Set audit fields
        stockModel.setUpdatedAt(LocalDateTime.now());

        // Set the user who created the stock
        Users currentUser = userRepository
            .findById(currentUserId)
            .orElseThrow(() ->
                new RuntimeException("User not found with id: " + currentUserId)
            );
        stockModel.setUpdatedBy(currentUser);

        // Save the stock
        StockModel savedStock = stockRepository.save(stockModel);

        // Convert to response DTO and return
        return stockMapper.toStockResponseBasedDto(savedStock);
    }

    // READ - Get stock by ID
    @Transactional(readOnly = true)
    public DetailedStockResponseDto getStockById(UUID stockId) {
        StockModel stock = stockRepository
            .findById(stockId)
            .orElseThrow(() ->
                new RuntimeException("Stock not found with id: " + stockId)
            );

        return stockMapper.toDetailedStockResponseDto(stock);
    }

    // READ - Get stock by ID and Store ID (for security)
    @Transactional(readOnly = true)
    public DetailedStockResponseDto getStockByIdAndStoreId(
        UUID stockId,
        UUID storeId
    ) {
        StockModel stock = stockRepository
            .findByStockIdAndStoreStoreId(stockId, storeId)
            .orElseThrow(() ->
                new RuntimeException(
                    "Stock not found with id: " +
                        stockId +
                        " in store: " +
                        storeId
                )
            );

        return stockMapper.toDetailedStockResponseDto(stock);
    }

    // READ - Get all stocks by store ID
    @Transactional(readOnly = true)
    public List<StockResponseBasedDto> getAllStocksByStoreId(UUID storeId) {
        // Validate store exists
        if (!storeRepository.existsById(storeId)) {
            throw new RuntimeException("Store not found with id: " + storeId);
        }

        List<StockModel> stocks = stockRepository.findByStoreStoreId(storeId);
        return stockMapper.toStockResponseBasedDtoList(stocks);
    }

    // READ - Get all active stocks by store ID
    @Transactional(readOnly = true)
    public List<StockResponseBasedDto> getActiveStocksByStoreId(UUID storeId) {
        // Validate store exists
        if (!storeRepository.existsById(storeId)) {
            throw new RuntimeException("Store not found with id: " + storeId);
        }

        List<StockModel> stocks = stockRepository.findByStoreStoreIdAndIsActive(
            storeId,
            true
        );
        return stockMapper.toStockResponseBasedDtoList(stocks);
    }

    // READ - Search stocks by name
    @Transactional(readOnly = true)
    public List<StockResponseBasedDto> searchStocksByName(
        UUID storeId,
        String stockName
    ) {
        // Validate store exists
        if (!storeRepository.existsById(storeId)) {
            throw new RuntimeException("Store not found with id: " + storeId);
        }

        List<StockModel> stocks =
            stockRepository.findByStoreIdAndStockNameContaining(
                storeId,
                stockName
            );
        return stockMapper.toStockResponseBasedDtoList(stocks);
    }

    // READ - Get low stock items
    @Transactional(readOnly = true)
    public List<StockResponseBasedDto> getLowStockItems(
        UUID storeId,
        int threshold
    ) {
        // Validate store exists
        if (!storeRepository.existsById(storeId)) {
            throw new RuntimeException("Store not found with id: " + storeId);
        }

        List<StockModel> stocks = stockRepository.findLowStockByStore(
            storeId,
            threshold
        );
        return stockMapper.toStockResponseBasedDtoList(stocks);
    }

    // UPDATE - Update stock
    public DetailedStockResponseDto updateStock(
        UUID stockId,
        UpdateStockRequestDto updateRequest,
        UUID currentUserId
    ) {
        // Find existing stock
        StockModel existingStock = stockRepository
            .findById(stockId)
            .orElseThrow(() ->
                new RuntimeException("Stock not found with id: " + stockId)
            );

        // Check if updating stock name and if new name already exists
        if (
            updateRequest.getStockName() != null &&
            !updateRequest.getStockName().equals(existingStock.getStockName())
        ) {
            if (
                stockRepository.existsByStockNameAndStoreStoreId(
                    updateRequest.getStockName(),
                    existingStock.getStore().getStoreId()
                )
            ) {
                throw new RuntimeException(
                    "Stock with name '" +
                        updateRequest.getStockName() +
                        "' already exists in this store"
                );
            }
        }

        // Update fields from DTO
        stockMapper.updateStockFromDto(updateRequest, existingStock);

        // Set audit fields
        existingStock.setUpdatedAt(LocalDateTime.now());

        // Set the user who updated the stock
        Users currentUser = userRepository
            .findById(currentUserId)
            .orElseThrow(() ->
                new RuntimeException("User not found with id: " + currentUserId)
            );
        existingStock.setUpdatedBy(currentUser);

        // Save updated stock
        StockModel updatedStock = stockRepository.save(existingStock);

        return stockMapper.toDetailedStockResponseDto(updatedStock);
    }

    // UPDATE - Update stock for specific store (for security)
    public DetailedStockResponseDto updateStockForStore(
        UUID stockId,
        UUID storeId,
        UpdateStockRequestDto updateRequest,
        UUID currentUserId
    ) {
        // Find existing stock in specific store
        StockModel existingStock = stockRepository
            .findByStockIdAndStoreStoreId(stockId, storeId)
            .orElseThrow(() ->
                new RuntimeException(
                    "Stock not found with id: " +
                        stockId +
                        " in store: " +
                        storeId
                )
            );

        // Check if updating stock name and if new name already exists
        if (
            updateRequest.getStockName() != null &&
            !updateRequest.getStockName().equals(existingStock.getStockName())
        ) {
            if (
                stockRepository.existsByStockNameAndStoreStoreId(
                    updateRequest.getStockName(),
                    storeId
                )
            ) {
                throw new RuntimeException(
                    "Stock with name '" +
                        updateRequest.getStockName() +
                        "' already exists in this store"
                );
            }
        }

        // Update fields from DTO
        stockMapper.updateStockFromDto(updateRequest, existingStock);

        // Set audit fields
        existingStock.setUpdatedAt(LocalDateTime.now());

        // Set the user who updated the stock
        Users currentUser = userRepository
            .findById(currentUserId)
            .orElseThrow(() ->
                new RuntimeException("User not found with id: " + currentUserId)
            );
        existingStock.setUpdatedBy(currentUser);

        // Save updated stock
        StockModel updatedStock = stockRepository.save(existingStock);

        return stockMapper.toDetailedStockResponseDto(updatedStock);
    }

    // DELETE - Soft delete (deactivate) stock
    public void deactivateStock(UUID stockId, UUID currentUserId) {
        StockModel stock = stockRepository
            .findById(stockId)
            .orElseThrow(() ->
                new RuntimeException("Stock not found with id: " + stockId)
            );

        // Set audit fields
        stock.setUpdatedAt(LocalDateTime.now());
        stock.setActive(false);

        // Set the user who deactivated the stock
        Users currentUser = userRepository
            .findById(currentUserId)
            .orElseThrow(() ->
                new RuntimeException("User not found with id: " + currentUserId)
            );
        stock.setUpdatedBy(currentUser);

        stockRepository.save(stock);
    }

    // DELETE - Soft delete for specific store (for security)
    public void deactivateStockForStore(
        UUID stockId,
        UUID storeId,
        UUID currentUserId
    ) {
        StockModel stock = stockRepository
            .findByStockIdAndStoreStoreId(stockId, storeId)
            .orElseThrow(() ->
                new RuntimeException(
                    "Stock not found with id: " +
                        stockId +
                        " in store: " +
                        storeId
                )
            );

        // Set audit fields
        stock.setUpdatedAt(LocalDateTime.now());
        stock.setActive(false);

        // Set the user who deactivated the stock
        Users currentUser = userRepository
            .findById(currentUserId)
            .orElseThrow(() ->
                new RuntimeException("User not found with id: " + currentUserId)
            );
        stock.setUpdatedBy(currentUser);

        stockRepository.save(stock);
    }

    // DELETE - Hard delete stock (permanent removal)
    public void deleteStock(UUID stockId) {
        if (!stockRepository.existsById(stockId)) {
            throw new RuntimeException("Stock not found with id: " + stockId);
        }
        stockRepository.deleteById(stockId);
    }

    // DELETE - Hard delete for specific store (for security)
    public void deleteStockForStore(UUID stockId, UUID storeId) {
        StockModel stock = stockRepository
            .findByStockIdAndStoreStoreId(stockId, storeId)
            .orElseThrow(() ->
                new RuntimeException(
                    "Stock not found with id: " +
                        stockId +
                        " in store: " +
                        storeId
                )
            );
        stockRepository.delete(stock);
    }

    // UTILITY - Reactivate stock
    public DetailedStockResponseDto reactivateStock(
        UUID stockId,
        UUID currentUserId
    ) {
        StockModel stock = stockRepository
            .findById(stockId)
            .orElseThrow(() ->
                new RuntimeException("Stock not found with id: " + stockId)
            );

        // Set audit fields
        stock.setUpdatedAt(LocalDateTime.now());
        stock.setActive(true);

        // Set the user who reactivated the stock
        Users currentUser = userRepository
            .findById(currentUserId)
            .orElseThrow(() ->
                new RuntimeException("User not found with id: " + currentUserId)
            );
        stock.setUpdatedBy(currentUser);

        StockModel updatedStock = stockRepository.save(stock);
        return stockMapper.toDetailedStockResponseDto(updatedStock);
    }
}
