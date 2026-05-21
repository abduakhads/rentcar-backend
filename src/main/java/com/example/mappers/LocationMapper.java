package com.example.mappers;

import com.example.dto.LocationDTO;
import com.example.models.Location;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationDTO toDTO(Location location);
    List<LocationDTO> toDTOList(List<Location> locations);
}
