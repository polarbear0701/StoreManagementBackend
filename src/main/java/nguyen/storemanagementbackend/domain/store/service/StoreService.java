package nguyen.storemanagementbackend.domain.store.service;

import nguyen.storemanagementbackend.common.exception.InvalidNewStoreException;
import nguyen.storemanagementbackend.domain.address.model.Address;
import nguyen.storemanagementbackend.domain.store.dto.NewStoreDto;
import nguyen.storemanagementbackend.domain.store.model.StoreInfoModel;
import nguyen.storemanagementbackend.domain.store.model.StoreModel;
import nguyen.storemanagementbackend.domain.store.repository.StoreRepository;
import nguyen.storemanagementbackend.domain.user.model.Users;

import nguyen.storemanagementbackend.domain.user.service.UsersService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final UsersService usersService;

    public StoreService(StoreRepository storeRepository, UsersService usersService) {
        this.storeRepository = storeRepository;
        this.usersService = usersService;
    }

    public StoreModel createStore(NewStoreDto newStoreDto) {

        validateNewStoreDto(newStoreDto);
        
        Optional<Users> adminOptional = usersService.fetchUserById(newStoreDto.getStoreOwnerId());

        Users owner = adminOptional.orElse(null);

        Address newStoreAddress = Address.builder()
                .addressNumber(newStoreDto.getStoreInfo().getStoreAddress().getAddressNumber())
                .addressWard(newStoreDto.getStoreInfo().getStoreAddress().getAddressWard())
                .addressDistrict(newStoreDto.getStoreInfo().getStoreAddress().getAddressDistrict())
                .addressCity(newStoreDto.getStoreInfo().getStoreAddress().getAddressCity())
                .addressCountry(newStoreDto.getStoreInfo().getStoreAddress().getAddressCountry())
                .build();

        StoreInfoModel newStoreInfo = StoreInfoModel.builder()
                .storeAddress(newStoreAddress)
                .storeName(newStoreDto.getStoreInfo().getStoreName())
                .storeDescription(newStoreDto.getStoreInfo().getStoreDescription())
                .build();

        StoreModel newStore = StoreModel.builder()
                .storeOwner(owner)
                .storeInfo(newStoreInfo)
                .build();

        Set<Users> currentStoreAdmins = new HashSet<>();
        currentStoreAdmins.add(owner);
        newStore.setAdmins(currentStoreAdmins);

        return storeRepository.save(newStore);
    }

    public void deleteStore(UUID storeId) {
        storeRepository.deleteById(storeId);
    }

    // Helper functions
    private void validateNewStoreDto(NewStoreDto newStoreDto) {
        UUID currentUserId = newStoreDto.getStoreOwnerId();
        Optional<Users> currentUserOptional = usersService.fetchUserById(currentUserId);
        
        if(currentUserOptional.isPresent()) {
            Users currentUser = currentUserOptional.get();
            if (!currentUser.getRole().toString().equals("ADMIN")) {
                throw new InvalidNewStoreException("This user is not an ADMIN. Please check again!");
            }
        } else {
            throw new InvalidNewStoreException("No user found! Please register again!");
        }
    }
}
