package nguyen.storemanagementbackend.domain.store.service;

import nguyen.storemanagementbackend.domain.store.dto.storemodel.request.NewStoreRequestDto;
import nguyen.storemanagementbackend.domain.store.repository.StoreInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StoreInfoService {
    private final StoreInfoRepository storeInfoRepository;
    private final Logger logger = LoggerFactory.getLogger(StoreInfoService.class);

    public StoreInfoService(StoreInfoRepository storeInfoRepository) {
        this.storeInfoRepository = storeInfoRepository;
    }

    public void createNewStoreInfoService(NewStoreRequestDto newStoreRequestDto) {

    }


}
