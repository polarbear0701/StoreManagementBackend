package nguyen.storemanagementbackend.common.mapper;

import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UuidMapper {
    default String map(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }

    default UUID map (String uuid) {
        return uuid != null ? UUID.fromString(uuid) : null;
    }
}
