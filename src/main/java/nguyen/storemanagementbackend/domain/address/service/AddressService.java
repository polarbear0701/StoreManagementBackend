package nguyen.storemanagementbackend.domain.address.service;

import nguyen.storemanagementbackend.domain.address.dto.NewAddressDto;
import nguyen.storemanagementbackend.domain.address.model.Address;
import nguyen.storemanagementbackend.domain.address.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address createAddressService(NewAddressDto newAddressDto) {
        Address newAddress = new Address();
        newAddress.setAddressNumber(newAddressDto.getAddressNumber());
        newAddress.setAddressWard(newAddressDto.getAddressWard());
        newAddress.setAddressDistrict(newAddressDto.getAddressDistrict());
        newAddress.setAddressCity(newAddressDto.getAddressCity());
        newAddress.setAddressCountry(newAddressDto.getAddressCountry());

        return addressRepository.save(newAddress);
    }

    public Address createAddressService(Address newAddress) {
        return addressRepository.save(newAddress);
    }
}
