package nguyen.storemanagementbackend.domain.address.service;


import nguyen.storemanagementbackend.domain.address.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

}
