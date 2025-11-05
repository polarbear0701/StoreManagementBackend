package nguyen.storemanagementbackend.domain.store.service;

import jakarta.transaction.Transactional;
import nguyen.storemanagementbackend.common.dto.StoreResponseBasedDto;
import nguyen.storemanagementbackend.common.exception.InvalidNewStoreException;
import nguyen.storemanagementbackend.common.exception.NoStoreFoundException;
import nguyen.storemanagementbackend.domain.store.dto.storemodel.request.NewStoreRequestDto;
import nguyen.storemanagementbackend.domain.store.dto.storemodel.response.DetailedStoreResponseDto;
import nguyen.storemanagementbackend.domain.store.mapper.StoreMapper;
import nguyen.storemanagementbackend.domain.store.model.StoreModel;
import nguyen.storemanagementbackend.domain.store.repository.StoreRepository;
import nguyen.storemanagementbackend.domain.user.model.Users;

import nguyen.storemanagementbackend.domain.user.service.UsersService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final UsersService usersService;
    private final StoreMapper storeMapper;

    public StoreService(StoreRepository storeRepository, UsersService usersService, StoreMapper storeMapper) {
        this.storeRepository = storeRepository;
        this.usersService = usersService;
        this.storeMapper = storeMapper;
    }

    /**
     * Create a new store
     *
     * @param newStoreRequestDto Data Transfer Object containing information about the new store to be created.
     *                   Containing store owner ID and store info.
     * @return The newly created StoreModel object.
     * @throws InvalidNewStoreException: If the provided store owner ID does not correspond to
     *                                   a valid ADMIN user.
     */

    @Transactional
    public StoreResponseBasedDto createStore(NewStoreRequestDto newStoreRequestDto) {

        validateNewStoreRequestDto(newStoreRequestDto);

        Optional<Users> adminOptional = usersService.fetchUserById(newStoreRequestDto.getStoreOwnerId());

        Users owner = adminOptional.orElse(null);

        StoreModel newStore = storeMapper.toEntity(newStoreRequestDto);
        newStore.setStoreOwner(owner);

        initializeNewStoreAdmins(newStore, owner);

        return storeMapper.toStoreResponseBasedDto(storeRepository.save(newStore));
    }

    public List<StoreResponseBasedDto> fetchStoreBasedInfoByAdminId(UUID adminId) {
        Optional<Users> adminOptional = usersService.fetchUserById(adminId);
        if (adminOptional.isEmpty()) {
            throw new NoStoreFoundException("No admin found with the provided ID!");
        }
        List<StoreModel> stores = storeRepository.findByStoreOwner(adminOptional.get());

        return storeMapper.toStoreResponseBasedDtoList(stores);
    }

    @Transactional
    public void deleteStore(UUID storeId, UUID ownerId) {

        StoreModel storeToDelete = storeRepository.findById(storeId).orElseThrow(
                () -> new NoStoreFoundException("This store does not exist! Can't delete store")
        );

        validateStoreOwner(storeToDelete, ownerId);

        storeToDelete.setActive(false);
    }

    public DetailedStoreResponseDto fetchStoreById(UUID storeId) {
        Optional<StoreModel> fetchedStoreOptional = storeRepository.findById(storeId);
        if (fetchedStoreOptional.isEmpty()) {
            throw new NoStoreFoundException("No store found with the provided ID!");
        }

        StoreModel fetchedStore = fetchedStoreOptional.get();
        return storeMapper.toDetailedStoreResponseDto(fetchedStore);
    }



    // Helper functions
    private void validateNewStoreRequestDto(NewStoreRequestDto newStoreRequestDto) {
        UUID currentUserId = newStoreRequestDto.getStoreOwnerId();
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

    private void initializeNewStoreAdmins(StoreModel newStore, Users owner) {
        if (newStore.getAdmins() == null){
            newStore.setAdmins(new HashSet<>());
        }
        Set<Users> currentStoreAdmins = newStore.getAdmins();
        currentStoreAdmins.add(owner);
        newStore.setAdmins(currentStoreAdmins);
    }

    private void validateStoreOwner(StoreModel storeToDelete, UUID ownerId) {
        if (!storeToDelete.getStoreOwner().getUserId().equals(ownerId)) {
            throw new InvalidNewStoreException("You don't own this store! Operation failed!");
        }
    }
}
