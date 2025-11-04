package nguyen.storemanagementbackend.domain.address.controller;

import nguyen.storemanagementbackend.common.dto.AddressResponseBasedDto;
import nguyen.storemanagementbackend.common.generic.GenericResponseDto;
import nguyen.storemanagementbackend.domain.address.dto.NewAddressRequestDto;
import nguyen.storemanagementbackend.domain.address.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PatchMapping("/update/{addressId}")
    public ResponseEntity<GenericResponseDto<AddressResponseBasedDto>> updateAddressController(
            @RequestBody NewAddressRequestDto newAddressRequestDto,
            @PathVariable String addressId
    ) {
        AddressResponseBasedDto updatedAddress = addressService.updateAddress((UUID.fromString(addressId)), newAddressRequestDto);
        return ResponseEntity.ok().body(
                new GenericResponseDto<>(
                        HttpStatus.OK.value(),
                        "Address updated successfully",
                        updatedAddress
                )
        );
    }
}
