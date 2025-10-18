package nguyen.storemanagementbackend.domain.store.controller;

import nguyen.storemanagementbackend.common.generic.GenericResponseDto;
import nguyen.storemanagementbackend.domain.store.dto.NewStoreDto;
import nguyen.storemanagementbackend.domain.store.model.StoreModel;
import nguyen.storemanagementbackend.domain.store.service.StoreService;
import nguyen.storemanagementbackend.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/create")
    public ResponseEntity<GenericResponseDto<StoreModel>> createNewStoreController(
            @RequestBody NewStoreDto newStoreDto,
            @AuthenticationPrincipal CustomUserDetails currentUser
            ) {

        if (currentUser.getId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        newStoreDto.setStoreOwnerId(currentUser.getId());

        StoreModel createdStore = storeService.createStore(newStoreDto);
        return ResponseEntity.ok().body(new GenericResponseDto<>(HttpStatus.CREATED.value(), "Success", createdStore));
    }

    @DeleteMapping("/delete/{storeId}")
    public String deleteStore(@PathVariable UUID storeId) {
        storeService.deleteStore(storeId);
        return "Store deleted";
    }
}
