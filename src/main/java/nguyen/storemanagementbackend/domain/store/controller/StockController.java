package nguyen.storemanagementbackend.domain.store.controller;

import nguyen.storemanagementbackend.common.dto.StockResponseBasedDto;
import nguyen.storemanagementbackend.common.generic.GenericResponseDto;
import nguyen.storemanagementbackend.domain.store.dto.stock.request.NewStockRequestDto;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<GenericResponseDto<StockResponseBasedDto>> createStock(
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
}
