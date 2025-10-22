package nguyen.storemanagementbackend.domain.store.controller;

import nguyen.storemanagementbackend.common.dto.StoreResponseBasedDto;
import nguyen.storemanagementbackend.common.generic.GenericResponseDto;
import nguyen.storemanagementbackend.domain.store.dto.storemodel.request.NewStoreRequestDto;
import nguyen.storemanagementbackend.domain.store.dto.storemodel.response.DetailedStoreResponseDto;
import nguyen.storemanagementbackend.domain.store.service.StoreService;
import nguyen.storemanagementbackend.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<GenericResponseDto<StoreResponseBasedDto>> createNewStoreController(
            @RequestBody NewStoreRequestDto newStoreRequestDto,
            @AuthenticationPrincipal CustomUserDetails currentUser
            ) {

        if (currentUser.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        newStoreRequestDto.setStoreOwnerId(currentUser.getId());

        StoreResponseBasedDto createdStore = storeService.createStore(newStoreRequestDto);

        return ResponseEntity.ok().body(new GenericResponseDto<>(HttpStatus.CREATED.value(), "Success", createdStore));
    }

    @GetMapping("/fetch/store/{storeId}")
    public ResponseEntity<GenericResponseDto<DetailedStoreResponseDto>> fetchStoreById(@PathVariable UUID storeId) {
        DetailedStoreResponseDto storeFetchedDto = storeService.fetchStoreById(storeId);
        return ResponseEntity.ok().body(new GenericResponseDto<>(HttpStatus.OK.value(), "Success", storeFetchedDto));
    }

    @GetMapping("/fetch/admin")
    public ResponseEntity<GenericResponseDto<List<StoreResponseBasedDto>>> fetchStoreByAdminId(
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        List<StoreResponseBasedDto> storeFetchedDto = storeService.fetchStoreBasedInfoByAdminId(currentUser.getId());
        return ResponseEntity.ok().body(new GenericResponseDto<>(HttpStatus.OK.value(), "Success", storeFetchedDto));
    }

    @DeleteMapping("/delete/{storeId}")
    public String deleteStore(@PathVariable UUID storeId) {
        storeService.deleteStore(storeId);
        return "Store deleted";
    }
}
