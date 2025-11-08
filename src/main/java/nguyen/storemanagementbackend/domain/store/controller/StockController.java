package nguyen.storemanagementbackend.domain.store.controller;

import java.util.List;
import java.util.UUID;
import nguyen.storemanagementbackend.common.dto.StockResponseBasedDto;
import nguyen.storemanagementbackend.common.generic.GenericResponseDto;
import nguyen.storemanagementbackend.domain.store.dto.stock.request.NewStockRequestDto;
import nguyen.storemanagementbackend.domain.store.dto.stock.request.UpdateStockRequestDto;
import nguyen.storemanagementbackend.domain.store.dto.stock.response.DetailedStockResponseDto;
import nguyen.storemanagementbackend.domain.store.service.StockService;
import nguyen.storemanagementbackend.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // CREATE - Create new stock
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<
        GenericResponseDto<StockResponseBasedDto>
    > createStock(
        @RequestBody NewStockRequestDto newStockRequestDto,
        @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        if (currentUser.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        StockResponseBasedDto createdStock = stockService.createNewStock(
            newStockRequestDto,
            currentUser.getId()
        );

        return ResponseEntity.ok().body(
            new GenericResponseDto<>(
                HttpStatus.CREATED.value(),
                "Stock created successfully",
                createdStock
            )
        );
    }

    // READ - Get stock by ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{stockId}")
    public ResponseEntity<
        GenericResponseDto<DetailedStockResponseDto>
    > getStockById(@PathVariable UUID stockId) {
        DetailedStockResponseDto stock = stockService.getStockById(stockId);

        return ResponseEntity.ok().body(
            new GenericResponseDto<>(
                HttpStatus.OK.value(),
                "Stock retrieved successfully",
                stock
            )
        );
    }

    // READ - Get stock by ID for specific store (more secure)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/store/{storeId}/stock/{stockId}")
    public ResponseEntity<
        GenericResponseDto<DetailedStockResponseDto>
    > getStockByIdAndStoreId(
        @PathVariable UUID storeId,
        @PathVariable UUID stockId
    ) {
        DetailedStockResponseDto stock = stockService.getStockByIdAndStoreId(
            stockId,
            storeId
        );

        return ResponseEntity.ok().body(
            new GenericResponseDto<>(
                HttpStatus.OK.value(),
                "Stock retrieved successfully",
                stock
            )
        );
    }

    // READ - Get all stocks by store ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/store/{storeId}")
    public ResponseEntity<
        GenericResponseDto<List<StockResponseBasedDto>>
    > getAllStocksByStoreId(@PathVariable UUID storeId) {
        List<StockResponseBasedDto> stocks = stockService.getAllStocksByStoreId(
            storeId
        );

        return ResponseEntity.ok().body(
            new GenericResponseDto<>(
                HttpStatus.OK.value(),
                "Stocks retrieved successfully",
                stocks
            )
        );
    }

    // READ - Get active stocks by store ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/store/{storeId}/active")
    public ResponseEntity<
        GenericResponseDto<List<StockResponseBasedDto>>
    > getActiveStocksByStoreId(@PathVariable UUID storeId) {
        List<StockResponseBasedDto> stocks =
            stockService.getActiveStocksByStoreId(storeId);

        return ResponseEntity.ok().body(
            new GenericResponseDto<>(
                HttpStatus.OK.value(),
                "Active stocks retrieved successfully",
                stocks
            )
        );
    }

    // READ - Search stocks by name
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/store/{storeId}/search")
    public ResponseEntity<
        GenericResponseDto<List<StockResponseBasedDto>>
    > searchStocksByName(
        @PathVariable UUID storeId,
        @RequestParam String stockName
    ) {
        List<StockResponseBasedDto> stocks = stockService.searchStocksByName(
            storeId,
            stockName
        );

        return ResponseEntity.ok().body(
            new GenericResponseDto<>(
                HttpStatus.OK.value(),
                "Search results retrieved successfully",
                stocks
            )
        );
    }

    // READ - Get low stock items
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/store/{storeId}/low-stock")
    public ResponseEntity<
        GenericResponseDto<List<StockResponseBasedDto>>
    > getLowStockItems(
        @PathVariable UUID storeId,
        @RequestParam(defaultValue = "10") int threshold
    ) {
        List<StockResponseBasedDto> stocks = stockService.getLowStockItems(
            storeId,
            threshold
        );

        return ResponseEntity.ok().body(
            new GenericResponseDto<>(
                HttpStatus.OK.value(),
                "Low stock items retrieved successfully",
                stocks
            )
        );
    }

    // UPDATE - Update stock
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{stockId}")
    public ResponseEntity<
        GenericResponseDto<DetailedStockResponseDto>
    > updateStock(
        @PathVariable UUID stockId,
        @RequestBody UpdateStockRequestDto updateRequest,
        @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        if (currentUser.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        DetailedStockResponseDto updatedStock = stockService.updateStock(
            stockId,
            updateRequest,
            currentUser.getId()
        );

        return ResponseEntity.ok().body(
            new GenericResponseDto<>(
                HttpStatus.OK.value(),
                "Stock updated successfully",
                updatedStock
            )
        );
    }

    // UPDATE - Update stock for specific store (more secure)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/store/{storeId}/stock/{stockId}")
    public ResponseEntity<
        GenericResponseDto<DetailedStockResponseDto>
    > updateStockForStore(
        @PathVariable UUID storeId,
        @PathVariable UUID stockId,
        @RequestBody UpdateStockRequestDto updateRequest,
        @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        if (currentUser.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        DetailedStockResponseDto updatedStock =
            stockService.updateStockForStore(
                stockId,
                storeId,
                updateRequest,
                currentUser.getId()
            );

        return ResponseEntity.ok().body(
            new GenericResponseDto<>(
                HttpStatus.OK.value(),
                "Stock updated successfully",
                updatedStock
            )
        );
    }

    // DELETE - Soft delete (deactivate) stock
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{stockId}/deactivate")
    public ResponseEntity<GenericResponseDto<String>> deactivateStock(
        @PathVariable UUID stockId,
        @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        if (currentUser.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        stockService.deactivateStock(stockId, currentUser.getId());

        return ResponseEntity.ok().body(
            new GenericResponseDto<>(
                HttpStatus.OK.value(),
                "Stock deactivated successfully",
                "Stock has been deactivated"
            )
        );
    }

    // DELETE - Soft delete for specific store (more secure)
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/store/{storeId}/stock/{stockId}/deactivate")
    public ResponseEntity<GenericResponseDto<String>> deactivateStockForStore(
        @PathVariable UUID storeId,
        @PathVariable UUID stockId,
        @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        if (currentUser.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        stockService.deactivateStockForStore(
            stockId,
            storeId,
            currentUser.getId()
        );

        return ResponseEntity.ok().body(
            new GenericResponseDto<>(
                HttpStatus.OK.value(),
                "Stock deactivated successfully",
                "Stock has been deactivated"
            )
        );
    }

    // DELETE - Hard delete stock (permanent removal)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{stockId}")
    public ResponseEntity<GenericResponseDto<String>> deleteStock(
        @PathVariable UUID stockId
    ) {
        stockService.deleteStock(stockId);

        return ResponseEntity.ok().body(
            new GenericResponseDto<>(
                HttpStatus.OK.value(),
                "Stock deleted successfully",
                "Stock has been permanently removed"
            )
        );
    }

    // DELETE - Hard delete for specific store (more secure)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/store/{storeId}/stock/{stockId}")
    public ResponseEntity<GenericResponseDto<String>> deleteStockForStore(
        @PathVariable UUID storeId,
        @PathVariable UUID stockId
    ) {
        stockService.deleteStockForStore(stockId, storeId);

        return ResponseEntity.ok().body(
            new GenericResponseDto<>(
                HttpStatus.OK.value(),
                "Stock deleted successfully",
                "Stock has been permanently removed"
            )
        );
    }

    // UTILITY - Reactivate stock
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{stockId}/reactivate")
    public ResponseEntity<
        GenericResponseDto<DetailedStockResponseDto>
    > reactivateStock(
        @PathVariable UUID stockId,
        @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        if (currentUser.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        DetailedStockResponseDto reactivatedStock =
            stockService.reactivateStock(stockId, currentUser.getId());

        return ResponseEntity.ok().body(
            new GenericResponseDto<>(
                HttpStatus.OK.value(),
                "Stock reactivated successfully",
                reactivatedStock
            )
        );
    }
}
