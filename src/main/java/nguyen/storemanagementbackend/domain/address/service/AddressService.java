package nguyen.storemanagementbackend.domain.address.service;


import nguyen.storemanagementbackend.common.dto.AddressResponseBasedDto;
import nguyen.storemanagementbackend.domain.address.dto.NewAddressRequestDto;
import nguyen.storemanagementbackend.domain.address.mapper.AddressMapper;
import nguyen.storemanagementbackend.domain.address.model.Address;
import nguyen.storemanagementbackend.domain.address.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final Logger logger = LoggerFactory.getLogger(AddressService.class);

    public AddressService(
            AddressRepository addressRepository,
            AddressMapper addressMapper
    ) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public AddressResponseBasedDto updateAddress(
            UUID addressId,
            NewAddressRequestDto newAddressRequestDto
    ) {
        Address updatedAddress = addressRepository.findById(addressId).orElseThrow(
                () -> new RuntimeException("No address found with ID: " + addressId)
        );

        addressMapper.updateAddress(newAddressRequestDto, updatedAddress);
        logger.info("Updated Address: {}", updatedAddress.getAddressNumber());
        return addressMapper.toAddressResponseBasedDto(addressRepository.save(updatedAddress));
    }


}
