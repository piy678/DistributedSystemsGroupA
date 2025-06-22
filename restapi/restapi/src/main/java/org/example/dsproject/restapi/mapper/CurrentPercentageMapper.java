package org.example.dsproject.restapi.mapper;

import org.example.dsproject.restapi.dto.CurrentPercentageDto;
import org.example.dsproject.restapi.model.CurrentPercentage;

public class CurrentPercentageMapper {

    public static CurrentPercentageDto toDto(CurrentPercentage entity) {
        return new CurrentPercentageDto(
                entity.getHour(),
                entity.getCommunityDepleted(),
                entity.getGridPortion()
        );
    }

    public static CurrentPercentage toEntity(CurrentPercentageDto dto) {
        return new CurrentPercentage(dto.getHour(), dto.getCommunityDepleted(), dto.getGridPortion());
    }
}
